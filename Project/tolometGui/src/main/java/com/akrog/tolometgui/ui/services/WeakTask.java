package com.akrog.tolometgui.ui.services;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import androidx.fragment.app.Fragment;

public abstract class WeakTask<Context,Params,Progress,Result> extends AsyncTask<Params,Progress,Result> {
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

    @Override
    final protected void onPreExecute() {
        Context context = getContext();
        if( context == null )
            return;
        onPreExecute(context);
    }

    @Override
    final protected Result doInBackground(Params... params) {
        Context context = getContext();
        if( context == null )
            return null;
        return doInBackground(context, params);
    }

    @Override
    final protected void onPostExecute(Result result) {
        Context context = getContext();
        if( context == null )
            return;
        onPostExecute(context, result);
    }

    @Override
    final protected void onCancelled() {
        Context context = getContext();
        if( context == null )
            return;
        onCancelled(context, null);
    }

    @Override
    final protected void onCancelled(Result result) {
        Context context = getContext();
        if( context == null )
            return;
        onCancelled(context, result);
    }

    @Override
    final protected void onProgressUpdate(Progress... progresses) {
        Context context = getContext();
        if( context == null )
            return;
        onProgressUpdate(context, progresses);
    }

    protected void onPreExecute(Context context) {
    }

    protected abstract Result doInBackground(Context context, Params... params);

    protected void onPostExecute(Context context, Result result) {
    }

    protected void onCancelled(Context context, Result result) {
    }

    protected void onProgressUpdate(Context context, Progress... progresses) {
    }
}
