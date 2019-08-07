package com.akrog.tolomet.gae;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.akrog.tolomet.BuildConfig;
import com.akrog.tolomet.R;
import com.akrog.tolomet.io.TimeoutTask;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolomet.viewmodel.AppSettings;
import com.akrog.tolomet.viewmodel.DbTolomet;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by gorka on 8/11/16.
 */

public class Updater {
    private static final int TIMEOUT = 30;
    private static final String LF= System.getProperty("line.separator");
    private Context context;
    private final AppSettings settings = AppSettings.getInstance();
    private static final String TAG = Updater.class.getSimpleName();

    public void initialize( Context context ) {
        this.context = context;
    }

    public void start() {
        final Long stamp = checkStamp();
        if( stamp == null )
            return;
        AsyncTask<Void,Void,Notification> task = new AsyncTask<Void,Void,Notification>() {
            @Override
            protected Notification doInBackground(Void... params) {
                Log.d(TAG,"downloading");
                try {
                    return new TimeoutTask<Notification>(TIMEOUT) {
                        @Override
                        public Notification call() throws Exception {
                            return download(stamp);
                        }
                    }.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    cancel(true);
                }
                return null;
            }
            @Override
            protected void onPostExecute(Notification notification) {
                super.onPostExecute(notification);
                Log.d(TAG,"downloaded");
                if( notification != null )
                    show(notification);
            }
            @Override
            protected void onCancelled() {
                super.onCancelled();
                Log.d(TAG,"cancelled");
            }
        };
        task.execute();
    }

    private Long checkStamp() {
        Calendar checked = Calendar.getInstance();
        checked.setTimeInMillis(settings.getCheckStamp());
        Calendar now = Calendar.getInstance();
        // Once a day
        if( checked.get(Calendar.YEAR) == now.get(Calendar.YEAR) && checked.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) )
            return null;
        settings.saveCheckStamp(now.getTimeInMillis());
        return settings.getUpdateStamp();
    }

    private Notification download(long stamp) throws Exception {
        GaeClient client = new GaeClient();
        return client.checkNotifications(
                android.os.Build.VERSION.SDK_INT,
                BuildConfig.VERSION_CODE, DbTolomet.getInstance().getVersion(),
                stamp, Locale.getDefault().getLanguage()
        );
    }

    private void show(Notification info) {
        if( info.getAppVersion() != null )
            askAppUpgrade(info);
        else if( info.getDbVersion() != null )
            askDbUpgrade(info);
        else if( info.getMotd() != null )
            showMotd(info);
    }

    private void askAppUpgrade(Notification info) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.newversion)
                .setMessage(parseImprovements(info))
                .setPositiveButton(R.string.update,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent marketIntent = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("http://market.android.com/details?id=com.akrog.tolomet"));
                                context.startActivity(marketIntent);
                            }
                        })
                .setNegativeButton(R.string.tomorrow,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
    }

    private String parseImprovements(Notification info) {
        StringBuilder msg = new StringBuilder();
        msg.append(context.getString(R.string.improvements));
        msg.append(" v");
        msg.append(info.getAppVersion());
        msg.append(':');
        msg.append(LF);
        for( String change : info.getImprovements() ) {
            msg.append("* ");
            msg.append(change);
            msg.append(LF);
        }
        return msg.toString();
    }

    private void askDbUpgrade(final Notification info) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.newdb)
                .setMessage(parseStations(info))
                .setPositiveButton(R.string.update,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                updateStations(info);
                            }
                        }).create().show();
    }

    private void updateStations(Notification info) {
        SQLiteDatabase lite = DbTolomet.getInstance().getWritableDatabase();
        lite.beginTransaction();
        try {
            for (Station station : info.getStations()) {
                WindProviderType type = WindProviderType.valueOf(station.getProvider());
                String id = com.akrog.tolomet.Station.buildId(type, station.getCode());
                if( station.getRegion() != null )
                    station.setRegion(getRegionCode(lite,station.getRegion()));
                if( station.getAction().equals(Station.Action.ADD) )
                    addStation(lite, station, id, type);
                else if( station.getAction().equals(Station.Action.UPDATE) )
                    updateStation(lite, station, id, type);
                else if( station.getAction().equals(Station.Action.REMOVE) )
                    delStation(lite, station, id, type);
            }
            lite.setVersion(info.getDbVersion());
            lite.setTransactionSuccessful();
        } catch (Exception e ) {
            e.printStackTrace();
        } finally {
            lite.endTransaction();
        }
    }

    private String getRegionCode(SQLiteDatabase lite, String region) {
        Cursor cursor = lite.query(DbTolomet.TAB_REGION,
                new String[]{DbTolomet.COL_REG_ID},DbTolomet.COL_REG_NAME+"=?",new String[]{region},
                null,null,null);
        cursor.moveToFirst();
        String id = cursor.getString(0);
        cursor.close();
        return id;
    }

    private void addStation(SQLiteDatabase lite, Station station, String id, WindProviderType type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbTolomet.COL_STA_ID, id);
        contentValues.put(DbTolomet.COL_STA_CODE, station.getCode());
        contentValues.put(DbTolomet.COL_STA_NAME, station.getName());
        contentValues.put(DbTolomet.COL_STA_PROV, station.getProvider());
        contentValues.put(DbTolomet.COL_STA_REG, station.getRegion());
        contentValues.put(DbTolomet.COL_STA_LAT, station.getLatitude());
        contentValues.put(DbTolomet.COL_STA_LON, station.getLongitude());
        lite.insert(DbTolomet.TAB_STATION, null, contentValues);
    }

    private void updateStation(SQLiteDatabase lite, Station station, String id, WindProviderType type) {
        ContentValues contentValues = new ContentValues();
        if( station.getName() != null )
            contentValues.put(DbTolomet.COL_STA_NAME, station.getName());
        if( station.getRegion() != null )
            contentValues.put(DbTolomet.COL_STA_REG, station.getRegion());
        if( station.getLatitude() != null )
            contentValues.put(DbTolomet.COL_STA_LAT, station.getLatitude());
        if( station.getLongitude() != null )
            contentValues.put(DbTolomet.COL_STA_LON, station.getLongitude());
        lite.update(DbTolomet.TAB_STATION, contentValues, "id=?",new String[]{id});
    }

    private void delStation(SQLiteDatabase lite, Station station, String id, WindProviderType type) {
        lite.delete(DbTolomet.TAB_STATION,"id=?",new String[]{id});
    }

    private String parseStations(Notification info) {
        StringBuilder msg = new StringBuilder();
        int cAdd = 0, cDel = 0, cMod = 0;
        Station sAdd = null, sDel = null, sMod = null;
        for(Station station : info.getStations()) {
            if( station.getAction().equals(Station.Action.ADD) ) {
                cAdd++;
                sAdd = station;
            } else if( station.getAction().equals(Station.Action.UPDATE) ) {
                cMod++;
                sMod = station;
            }
            else if( station.getAction().equals(Station.Action.REMOVE) ) {
                cDel++;
                sDel = station;
            }
        }
        if( cAdd > 0 )
            msg.append(formatCount(R.string.newst,cAdd,sAdd));
        if( cMod > 0 )
            msg.append(formatCount(R.string.modst,cMod,sMod));
        if( cDel > 0 )
            msg.append(formatCount(R.string.delst,cDel,sDel));
        return msg.toString();
    }

    private String formatCount(int id, int count, Station station) {
        if( count == 1 )
            return String.format("* %s: %s%s",context.getString(id),station.getName(),LF);
        return String.format("* %s: %d%s",context.getString(id),count,LF);
    }

    private void showMotd(Notification info) {
        if( info.getStamp() != null )
            settings.saveUpdateStamp(info.getStamp());
        new AlertDialog.Builder(context)
                .setTitle(R.string.motd)
                .setMessage(info.getMotd())
                .create().show();
    }
}
