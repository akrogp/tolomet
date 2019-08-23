package com.akrog.tolometgui2.ui.services;

import android.content.Context;

import com.akrog.tolometgui2.Tolomet;

import java.io.File;

public class StorageService {
    public static final String FILE_PROVIDER_AUTHORITY = "com.akrog.tolomet.ui2.FileProvider";

    public static File getAvailableCacheDir() {
        Context context = Tolomet.getAppContext();
        if( context == null )
            return null;
        File dir = context.getExternalCacheDir();
        if( dir != null )
            return dir;
        return context.getCacheDir();
    }
}
