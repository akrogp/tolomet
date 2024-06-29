package com.akrog.tolometgui.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.SparseArray;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.EuskalmetProvider;
import com.akrog.tolomet.providers.MeteoGaliciaProvider;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.Tolomet;
import com.akrog.tolometgui.model.backend.ConfigUpdate;
import com.akrog.tolometgui.model.db.DbTolomet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AppSettings {
    private enum Screen {
        CHARTS(R.id.nav_charts), MAP(R.id.nav_maps), INFO(R.id.nav_info), ORIGIN(R.id.nav_origin),
        SETTINGS(R.id.nav_settings), DISCOVER(R.id.nav_discover), HELP(R.id.nav_help),
        REPORT(R.id.nav_report), ABOUT(R.id.nav_about);

        Screen(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        private int id;
    }

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
        if( instance == null ) {
            instance = new AppSettings();
            refreshCore();
        }
        return instance;
    }

    public static void refreshCore() {
        ((EuskalmetProvider)WindProviderType.Euskalmet.getProvider()).setOriginal(instance.isEuskalmetOriginal());
        ((MeteoGaliciaProvider)WindProviderType.MeteoGalicia.getProvider()).setAltProvider(instance.isAltMeteoGalicia());
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
        Station station = DbTolomet.getInstance().stationDao().findStation(id);
        if( station != null )
            station.setFavorite(getFavorites().contains(station.getId()));
        return station;
    }

    public String getSelectedLanguage() {
        return settings.getString(SELECTED_LANGUAGE, Locale.getDefault().getLanguage());
    }

    public void setSelectedLanguage(String lang) {
        settings.edit().putString(SELECTED_LANGUAGE, lang).commit();
    }

    public long getCheckStamp() {
        return settings.getLong("stamp-check", 0);
    }

    public void saveCheckStamp( long stamp ) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("stamp-check", stamp);
        editor.commit();
    }

    public long getConfigStamp() {
        return settings.getLong("stamp-config", 0);
    }

    public void saveConfigStamp( long stamp ) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("stamp-config", stamp);
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

    public long getWidgetStamp() {
        return settings.getLong("widget-stamp", 0);
    }

    public void saveWidgetStamp(long stamp) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("widget-stamp", stamp);
        editor.commit();
    }

    public int getWidgetLoop() {
        return settings.getInt("widget-loop", 0);
    }

    public void saveWidgetLoop(int loop) {
        settings.edit().putInt("widget-loop", loop).commit();
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
        if( liveFlying != null )
            liveFlying.postValue(flying);
    }

    public boolean isFlying() {
        return settings.getBoolean("pref_flying", false);
    }

    public LiveData<Boolean> getLiveFlying() {
        if( liveFlying == null )
            liveFlying = new MutableLiveData<>(isFlying());
        return liveFlying;
    }

    public void setSatellite(boolean sat) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("pref_sat", sat);
        editor.commit();
    }

    public boolean isSatellite() {
        return settings.getBoolean("pref_sat", true);
    }

    public void setFlySpots(boolean sat) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("pref_flyspot", sat);
        editor.commit();
    }

    public boolean isFlySpots() {
        return settings.getBoolean("pref_flyspot", true);
    }

    public void setHikeSpots(boolean sat) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("pref_hikespot", sat);
        editor.commit();
    }

    public boolean isHikeSpots() {
        return settings.getBoolean("pref_hikespot", true);
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

    public boolean isSendXctrack() {
        return settings.getBoolean(PREF_SEND_XCTRACK, false);
    }

    public int getPortXctrack() {
        return Integer.parseInt(settings.getString(PREF_PORT_XCTRACK, "10110"));
    }

    public boolean isLocalServer() {
        return settings.getBoolean(PREF_ENABLED_SERVER, true);
    }

    public int getPortLocalServer() {
        return Integer.parseInt(settings.getString(PREF_PORT_SERVER, "4363"));
    }

    public void saveScreen(int id) {
        if( id >= Screen.values().length )
            for( Screen screen : Screen.values() )
                if( id == screen.getId() ) {
                    id = screen.ordinal();
                    break;
                }
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("pref_fragment",id);
        editor.commit();
    }

    public int getScreen() {
        int id = settings.getInt("pref_fragment", R.id.nav_charts);
        if( id < Screen.values().length )
            return Screen.values()[id].getId();
        return id;
    }

    public void setEuskalmetOriginal(boolean useOriginal) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREF_ORIG_EUSKALMET, useOriginal);
        editor.commit();
    }

    public boolean isEuskalmetOriginal() {
        return settings.getBoolean(PREF_ORIG_EUSKALMET, false);
    }

    public void setAltMeteoGalicia(boolean alt) {
        settings.edit().putBoolean(PREF_ALT_METEOGALICIA, alt).commit();
    }

    public boolean isAltMeteoGalicia() {
        return settings.getBoolean(PREF_ALT_METEOGALICIA, false);
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

    public void applyChanges(List<ConfigUpdate> cfgs) {
        SharedPreferences.Editor editor = settings.edit();
        for(ConfigUpdate cfg : cfgs) {
            if( cfg.getType().equals("string") )
                editor.putString(cfg.getKey(), cfg.getValue());
            else if( cfg.getType().equals("boolean") )
                editor.putBoolean(cfg.getKey(), Boolean.parseBoolean(cfg.getValue()));
            else if( cfg.getType().equals("int") )
                editor.putInt(cfg.getKey(), Integer.parseInt(cfg.getValue()));
            else if( cfg.getType().equals("long") )
                editor.putLong(cfg.getKey(), Long.parseLong(cfg.getValue()));
            else if( cfg.getType().equals("float") )
                editor.putFloat(cfg.getKey(), Float.parseFloat(cfg.getValue()));
        }
        editor.commit();
    }

    private static AppSettings instance;
    private final SharedPreferences settings;
    private final Context context;
    private final static int VERSION = 3;
    private final static int INVALID = -1000;
    private final SparseArray<List<Integer>> mapArrays = new SparseArray<List<Integer>>();
    private MutableLiveData<Boolean> liveFlying;

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
    public static final String SELECTED_LANGUAGE = "pref_modeLang";
    public static final String PREF_ORIG_EUSKALMET = "pref_origEuskalmet";
    public static final String PREF_SEND_XCTRACK = "pref_sendXctrack";
    public static final String PREF_PORT_XCTRACK = "pref_portXctrack";
    public static final String PREF_ENABLED_SERVER = "pref_enabledLocalServer";
    public static final String PREF_PORT_SERVER = "pref_portLocalServer";
    public static final String PREF_ALT_METEOGALICIA = "pref_altMeteoGalicia";
}
