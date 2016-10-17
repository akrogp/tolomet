package com.akrog.tolomet.presenters;

import android.content.Context;

import com.akrog.tolomet.Tolomet;

/**
 * Created by gorka on 6/10/16.
 */

public class Communicator {
    private static Communicator instance;
    private final Context context;

    private Communicator(Context context) {
        this.context = context;
    }

    public static Communicator getInstance() {
        if( instance == null )
            instance = new Communicator(Tolomet.getAppContext());
        return instance;
    }
}
