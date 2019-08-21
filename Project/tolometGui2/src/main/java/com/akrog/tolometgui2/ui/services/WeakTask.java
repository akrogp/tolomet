package com.akrog.tolometgui2.ui.services;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import androidx.fragment.app.Fragment;

public abstract class WeakTask<Context extends Fragment,Progress,Params,Result> extends AsyncTask<Progress,Params,Result> {
    private final WeakReference<Context> reference;

    public WeakTask(Context context) {
        reference = new WeakReference<>(context);
    }

    public Context getContext() {
        Context context = reference.get();
        if( context == null || context.getActivity().isFinishing() )
            return null;
        return context;
    }
}
