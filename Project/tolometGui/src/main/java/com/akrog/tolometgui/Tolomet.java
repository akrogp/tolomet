package com.akrog.tolometgui;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

import com.akrog.tolometgui.model.backend.Backend;
import com.akrog.tolometgui.model.backend.LocalBackend;
import com.akrog.tolometgui.model.backend.FirebaseBackend;

public class Tolomet extends Application {
    private static Context context;
    private static Backend backend;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

    public static synchronized Backend getBackend() {
        if( backend == null ) {
            if( context != null && !FirebaseApp.getApps(context).isEmpty() )
                backend = new FirebaseBackend();
            else
                backend = new LocalBackend();
        }
        return backend;
    }
}
