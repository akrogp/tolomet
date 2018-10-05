package com.akrog.tolomet.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * Created by gorka on 18/05/16.
 */
public class WidgetSettings {

    public WidgetSettings(Context context, int widgetId) {
        this.context = context;
        fileName = String.format("%s-%d",PREFS_NAME,widgetId);
        settings = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
    }

    public FlySpot getSpot() {
        return getSpot(settings, 1.0F);
    }

    public void setSpot( FlySpot spot ) {
        setSpot(settings, spot);
    }

    public void delete() {
        settings.edit().clear().commit();
        String path = String.format("%s/shared_prefs/%s.xml", context.getFilesDir().getParent(), fileName);
        new File(path).delete();
    }

    public static FlySpot getSpot(SharedPreferences settings, float factor) {
        FlySpot spot = new FlySpot();
        spot.setName(settings.getString("wspot",null));
        spot.setCountry(settings.getString("wcountry",null));
        spot.setSpeedUnits(getInt(settings, "wunit", 0));
        int len = Integer.parseInt(settings.getString("wconstraints","0"));
        for( int i = 0; i < len; i++ ) {
            FlyConstraint constraint = new FlyConstraint();
            constraint.setStation(settings.getString("wstation"+i, null));
            constraint.setMinDir(getInt(settings,"wminDir"+i,0));
            constraint.setMaxDir(getInt(settings,"wmaxDir"+i,360));
            constraint.setMinWind(getFloat(settings,"wminWind"+i,0)/factor);
            constraint.setMaxWind(getFloat(settings,"wmaxWind"+i,0)/factor);
            constraint.setMaxHum(getInt(settings,"wmaxHum"+i,100));
            spot.getConstraints().add(constraint);
        }
        return spot;
    }

    private static int getInt(SharedPreferences settings, String key, int def) {
        String value = settings.getString(key,String.valueOf(def));
        if( value.isEmpty() )
            return def;
        return Integer.parseInt(value);
    }

    private static float getFloat(SharedPreferences settings, String key, float def) {
        String value = settings.getString(key,String.valueOf(def));
        if( value.isEmpty() )
            return def;
        return Float.parseFloat(value);
    }

    public static void setSpot( SharedPreferences settings, FlySpot spot ) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("wspot", spot.getName());
        editor.putString("wcountry", spot.getCountry());
        editor.putString("wunit", spot.getSpeedUnits()+"");
        int len = spot.getConstraints().size();
        editor.putString("wconstraints", ""+len);
        for( int i = 0; i < len; i++ ) {
            FlyConstraint constraint = spot.getConstraints().get(i);
            editor.putString("wstation"+i,""+constraint.getStation());
            editor.putString("wminDir"+i,""+constraint.getMinDir());
            editor.putString("wmaxDir"+i,""+constraint.getMaxDir());
            editor.putString("wminWind"+i,""+constraint.getMinWind());
            editor.putString("wmaxWind"+i,""+constraint.getMaxWind());
            editor.putString("wmaxHum"+i,""+constraint.getMaxHum());
        }
        editor.commit();
    }

    private final String fileName;
    private final SharedPreferences settings;
    private final Context context;

    private static final String PREFS_NAME = "WidgetPrefs";
}
