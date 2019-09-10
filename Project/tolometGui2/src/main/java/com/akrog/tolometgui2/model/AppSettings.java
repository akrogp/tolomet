package com.akrog.tolometgui2.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.SparseArray;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Station;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.Tolomet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppSettings {
    private static AppSettings instance;
    private final SharedPreferences settings;
    private final Context context;

    private AppSettings() {
        context = Tolomet.getAppContext();
        settings = PreferenceManager.getDefaultSharedPreferences(context);

        fixValues();
        setDefaultsAuto();

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("cfg", VERSION);
        editor.commit();
    }

    public static AppSettings getInstance() {
        if( instance == null )
            instance = new AppSettings();
        return instance;
    }

    private void setDefaultsAuto() {
        String autoKeys[] = {"pref_speedRange","pref_minTemp","pref_maxTemp","pref_minPres","pref_maxPres"};
        for( String key : autoKeys ) {
            if( settings.getString(key, null) == null ) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(key, INVALID+"");
                editor.commit();
            }
        }
    }

    public int getConfigVersion() {
        return settings.getInt("cfg", 1);
    }

    public Set<String> getFavorites() {
        Set<String> result;
        try {
            result = fromCsv(settings.getString("fav", ""));
        } catch( Exception e ) {
            e.printStackTrace();
            result = new HashSet<String>();
        }
        return result;
    }

    public void addFavorite(Station station) {
        Set<String> favs = getFavorites();
        favs.add(station.getId());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("fav", toCsv(favs));
        editor.commit();
    }

    public void removeFavorite(Station station) {
        removeFavorite(station.getId());
    }

    public void removeFavorite(String station) {
        Set<String> favs = getFavorites();
        favs.remove(station);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("fav", toCsv(favs));
        editor.commit();
    }

    public void setFavorite(Station station, boolean fav) {
        station.setFavorite(fav);
        if( fav )
            addFavorite(station);
        else
            removeFavorite(station);
    }

    public void saveStation(Station station) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("station", station == null ? null : station.getId());
        editor.commit();
    }

    public Station loadStation() {
        String id = settings.getString("station", null);
        if( id == null )
            return null;
        Station station = DbTolomet.getInstance().findStation(id);
        station.setFavorite(getFavorites().contains(station.getId()));
        return station;
    }

    public long getCheckStamp() {
        return settings.getLong("stamp-check", 0);
    }

    public void saveCheckStamp( long stamp ) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("stamp-check", stamp);
        editor.commit();
    }

    public long getUpdateStamp() {
        return settings.getLong("stamp-update", 0);
    }

    public void saveUpdateStamp(long stamp ) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("stamp-update", stamp);
        editor.commit();
    }

    public boolean isIntroAccepted() {
        return settings.getBoolean("intro-ok", false);
    }

    public void setIntroAccepted(boolean ok) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("intro-ok", ok);
        editor.commit();
    }

    private int getPrefValue( String key, int idDefault, int idArray, boolean max, Measurement meas ) {
        return getPrefValue(key, idDefault, idArray, max, meas, 1.0F);
    }

    private int getPrefValue( String key, int idDefault, int idArray, boolean max, Measurement meas, float factor ) {
        int res = Integer.parseInt(settings.getString(key, context.getString(idDefault)));
        if( res != INVALID )
            return res;
        if( meas.isEmpty() )
            return Integer.parseInt(context.getString(idDefault));

        List<Integer> values = mapArrays.get(idArray);
        if( values == null ) {
            values = new ArrayList<Integer>();
            String[] strings = context.getResources().getStringArray(idArray);
            for( int i = 0; i < strings.length; i++ ) {
                int value = Integer.parseInt(strings[i]);
                if( value != INVALID )
                    values.add(value);
            }
            mapArrays.put(idArray, values);
        }

        int auto = max ?
                (int)Math.ceil(meas.getMaximum().intValue()*factor) :
                (int)Math.floor(meas.getMinimum().intValue()*factor);
        for( int i = 0; i < values.size(); i++ ) {
            int value = values.get(i);
            if( value <= auto )
                continue;
            if( !max )
                i = i > 1 ? i - 1 : 0;
            res = values.get(i);
            break;
        }

        if( res == INVALID )
            res = values.get(values.size()-1);

        return res;
    }

    public int getSpeedRange(Measurement meas) {
        return getPrefValue("pref_speedRange", R.string.pref_speedRangeDefault, R.array.pref_rangeValues, true, meas, getSpeedFactor());
    }

    public int getSpeedUnit() {
        return Integer.parseInt(settings.getString(PREF_UNIT, "0"));
    }

    public String getSpeedLabel() {
        String[] labels = context.getResources().getStringArray(R.array.pref_speedUnitEntries);
        return labels[getSpeedUnit()];
    }

    public static float getSpeedFactor(int speedUnit) {
        switch( speedUnit ) {
            case UNIT_MS:
                return 1000.0F/3600.0F;
            case UNIT_KNOT:
                return 1.0F/1.852F;
            case UNIT_KMH:
            default:
                return 1.0F;
        }
    }

    public float getSpeedFactor() {
        return getSpeedFactor(getSpeedUnit());
    }

    public int getMinMarker() {
        return Integer.parseInt(settings.getString("pref_minMarker", context.getString(R.string.pref_minMarkerDefault)));
    }

    public int getMaxMarker() {
        return Integer.parseInt(settings.getString("pref_maxMarker", context.getString(R.string.pref_maxMarkerDefault)));
    }

    public int getMinTemp(Measurement meas) {
        return getPrefValue("pref_minTemp", R.string.pref_minTempDefault, R.array.pref_minTempValues, false, meas);
    }

    public int getMaxTemp(Measurement meas) {
        return getPrefValue("pref_maxTemp", R.string.pref_maxTempDefault, R.array.pref_maxTempValues, true, meas);
    }

    public int getMinPres(Measurement meas) {
        return getPrefValue("pref_minPres", R.string.pref_minPresDefault, R.array.pref_minPresValues, false, meas);
    }

    public int getMaxPres(Measurement meas) {
        return getPrefValue("pref_maxPres", R.string.pref_maxPresDefault, R.array.pref_maxPresValues, true, meas);
    }

    public boolean isSimpleMode() {
        return settings.getString("pref_modeGraphs", context.getString(R.string.pref_modeGraphsDefault)).equals("0");
    }

    public int getUpdateMode() {
        return Integer.parseInt(settings.getString("pref_modeUpdate", context.getString(R.string.pref_modeUpdateDefault)));
    }

    public void setFlying(boolean flying) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("pref_flying", flying);
        editor.commit();
    }

    public boolean isFlying() {
        return settings.getBoolean("pref_flying", false);
    }

    public void setSatellite(boolean sat) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("pref_sat", sat);
        editor.commit();
    }

    public boolean isSatellite() {
        return settings.getBoolean("pref_sat", true);
    }

    public FlySpot getSpot() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("wconstraints","1");
        editor.putString("wunit", getSpeedUnit()+"");
        editor.commit();
        return WidgetSettings.getSpot(settings, getSpeedFactor());
    }

    public void setUpdateMode(int mode) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("pref_modeUpdate",""+mode);
        editor.commit();
    }

    public boolean isDaltonic() {
        return !settings.getString("pref_modeColors","0").equals("0");
    }

    public void saveScreen(int id) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("pref_fragment",id);
        editor.commit();
    }

    public int getScreen() {
        return settings.getInt("pref_fragment", R.id.nav_charts);
    }

    private void fixValues() {
        fixValues("pref_modeGraphs", R.array.pref_modeGraphsValues);
        fixValues("pref_modeUpdate", R.array.pref_modeUpdateValues );
        fixValues("pref_minTemp", R.array.pref_minTempValues );
        fixValues("pref_maxTemp", R.array.pref_maxTempValues );
        fixValues("pref_minPres", R.array.pref_minPresValues );
        fixValues("pref_maxPres", R.array.pref_maxPresValues );
        fixValues("pref_speedRange", R.array.pref_rangeValues );
        fixValues("pref_minMarker", R.array.pref_minSpeedValues );
        fixValues("pref_maxMarker", R.array.pref_maxSpeedValues );
    }

    private void fixValues( String key, int idArray ) {
        String pref = settings.getString(key, null);
        if( pref == null )
            return;
        String[] values = context.getResources().getStringArray(idArray);
        for( String value : values )
            if( pref.equals(value) )
                return;
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    private Set<String> fromCsv( String str ) {
        Set<String> set = new HashSet<String>();
        set.addAll(Arrays.asList(str.split(",")));
        if( set.size() == 1 && set.iterator().next().isEmpty() )
            set.clear();
        return set;
    }

    private String toCsv( Set<String> set ) {
        StringBuilder str = new StringBuilder();
        for( String code : set ) {
            str.append(code);
            str.append(',');
        }
        return str.toString().replaceAll(",$", "");
    }

    private final static int VERSION = 3;
    private final static int INVALID = -1000;
    private final SparseArray<List<Integer>> mapArrays = new SparseArray<List<Integer>>();
    public final static int MANUAL_UPDATES=0;
    public final static int SMART_UPDATES=1;
    public final static int AUTO_UPDATES=2;
    public final static int COLOR_NORMAL = 0;
    public final static int COLOR_DALTONIC = 1;
    public final static int UNIT_KMH = 0;
    public final static int UNIT_MS = 1;
    public final static int UNIT_KNOT = 2;
    public final static String PREF_UNIT = "pref_speedUnit";
    public final static String PREF_SPEED_RANGE = "pref_speedRange";
    public final static String PREF_MARKER_MIN = "pref_minMarker";
    public final static String PREF_MARKER_MAX = "pref_maxMarker";
}
