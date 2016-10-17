package com.akrog.tolomet;

import android.app.Application;
import android.content.Context;

/**
 * Created by gorka on 6/10/16.
 */

public class Tolomet extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

    private static Context context;
}
