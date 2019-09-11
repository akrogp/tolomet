package com.akrog.tolometgui2.ui.services;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import androidx.fragment.app.Fragment;

public abstract class WeakTask<Context,Progress,Params,Result> extends AsyncTask<Progress,Params,Result> {
    private final WeakReference<Context> reference;

    public WeakTask(Context context) {
        reference = new WeakReference<>(context);
    }

    public Context getContext() {
        Context context = reference.get();
        if( context == null )
            return null;
        Activity activity = null;
        if( context instanceof Activity )
            activity = (Activity)context;
        else if( context instanceof Fragment ) {
            activity = ((Fragment) context).getActivity();
            if( activity == null )
                return null;
        }
        if( activity != null && activity.isFinishing() )
            return null;
        return context;
    }
}
