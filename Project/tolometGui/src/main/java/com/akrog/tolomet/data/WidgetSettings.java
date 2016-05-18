package com.akrog.tolomet.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gorka on 18/05/16.
 */
public class WidgetSettings {

    public WidgetSettings(Context context, int widgetId) {
        this.context = context;
        settings = context.getSharedPreferences(String.format("%s-%d",PREFS_NAME,widgetId),Context.MODE_PRIVATE);
    }

    public WindSpot getSpot() {
        return getSpot(settings);
    }

    public void setSpot( WindSpot spot ) {
        setSpot(settings, spot);
    }

    public static WindSpot getSpot(SharedPreferences settings) {
        WindSpot spot = new WindSpot();
        spot.setName(settings.getString("wspot",null));
        int len = settings.getInt("wconstraints",0);
        for( int i = 0; i < len; i++ ) {
            WindConstraint constraint = new WindConstraint();
            constraint.setStation(settings.getString("wstation"+i, null));
            constraint.setMinDir(settings.getInt("wminDir"+i, 0));
            constraint.setMaxDir(settings.getInt("wmaxDir"+i, 360));
            constraint.setMinWind(settings.getInt("wminWind"+i, 0));
            constraint.setMinWind(settings.getInt("wmaxWind"+i, 0));
            spot.getConstraints().add(constraint);
        }
        return spot;
    }

    public static void setSpot( SharedPreferences settings, WindSpot spot ) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("wspot", spot.getName());
        int len = spot.getConstraints().size();
        editor.putInt("wconstraints", len);
        for( int i = 0; i < len; i++ ) {
            WindConstraint constraint = spot.getConstraints().get(i);
            editor.putString("wstation"+i,constraint.getStation());
            editor.putInt("wminDir"+i,constraint.getMinDir());
            editor.putInt("wmaxDir"+i,constraint.getMaxDir());
            editor.putInt("wminWind"+i,constraint.getMinWind());
            editor.putInt("wmaxWind"+i,constraint.getMaxWind());
        }
        editor.commit();
    }

    private final SharedPreferences settings;
    private final Context context;

    private static final String PREFS_NAME = "WidgetPrefs";
}
