package com.akrog.tolometgui.ui.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.akrog.tolometgui.Tolomet;

public class NetworkService {
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Tolomet.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
