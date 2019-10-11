package com.akrog.tolometgui.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.ui.adapters.ProviderAdapter;
import com.akrog.tolometgui.ui.services.WeakTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;

public class UpdateFragment extends ToolbarFragment implements AdapterView.OnItemClickListener {
    private List<ProviderAdapter.ProviderWrapper> providers;
    private CountTask countTask;
    private UpdateTask updateTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addCancelListenner(() -> {
            if( countTask != null ) {
                countTask.cancel(true);
                countTask = null;
            }
            if( updateTask != null ) {
                updateTask.cancel(true);
                updateTask = null;
            }
        });
        count();
        //updateList(null);
        updateFab();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(view -> download());
    }

    @Override
    protected int getMenuResource() {
        return R.menu.empty;
    }

    @Override
    protected int[] getLiveMenuItems() {
        return new int[0];
    }

    @Override
    public boolean needsScreenshotStation() {
        return false;
    }

    @Override
    public String getScreenshotSubject() {
        return getString(R.string.UpdateSubject);
    }

    @Override
    public String getScreenshotText() {
        return getString(R.string.UpdateText);
    }

    @Override
    public boolean useStation() {
        return false;
    }

    private void count() {
        countTask = new CountTask(this);
        countTask.execute();
    }

    private void download() {
        updateTask = new UpdateTask(this);
        updateTask.execute();
    }

    private void updateFab() {
        boolean enabled = false;
        if( providers != null )
            for( ProviderAdapter.ProviderWrapper provider : providers )
                if( provider.isChecked() ) {
                    enabled = true;
                    break;
                }
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        if( enabled )
            fab.show();
        else
            fab.hide();
    }

    private void updateList(List<DbTolomet.ProviderInfo> result) {
        providers = buildWrappers(result);
        Collections.sort(providers);

        ListView list = getActivity().findViewById(R.id.list_providers);
        ProviderAdapter adapter = new ProviderAdapter(getActivity(), providers.toArray(new ProviderAdapter.ProviderWrapper[0]), this);
        list.setAdapter(adapter);
    }

    private List<ProviderAdapter.ProviderWrapper> buildWrappers(List<DbTolomet.ProviderInfo> list) {
        /*DbTolometOld.ProviderInfo elliot = new DbTolometOld.ProviderInfo();
        elliot.setSpotProviderType(SpotProviderType.ElliottParagliding);
        elliot.setProvider(elliot.getSpotProviderType().name());
        list.add(elliot);*/
        List<ProviderAdapter.ProviderWrapper> providers = new ArrayList<>(WindProviderType.values().length);
        for( DbTolomet.ProviderInfo info : list ) {
            ProviderAdapter.ProviderWrapper wrapper = new ProviderAdapter.ProviderWrapper(info);
            providers.add(wrapper);
        }
        return providers;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        updateFab();
    }


    private static class CountTask extends WeakTask<UpdateFragment, Void, Void, List<DbTolomet.ProviderInfo>> {
        CountTask(UpdateFragment fragment) {
            super(fragment);
        }

        @Override
        protected void onPreExecute(UpdateFragment fragment) {
            fragment.beginProgress();
        }

        @Override
        protected List<DbTolomet.ProviderInfo> doInBackground(UpdateFragment fragment, Void... voids) {
            return DbTolomet.getInstance().statsDao().getProviderCounts();
        }

        @Override
        protected void onPostExecute(UpdateFragment fragment, List<DbTolomet.ProviderInfo> result) {
            fragment.countTask = null;
            fragment.updateList(result);
            fragment.updateFab();
            fragment.endProgress();
        }
    }

    private static class UpdateTask extends WeakTask<UpdateFragment, Void, Void, Void> {
        UpdateTask(UpdateFragment fragment) {
            super(fragment);
        }

        @Override
        protected void onPreExecute(UpdateFragment fragment) {
            fragment.beginProgress();
        }

        @Override
        protected Void doInBackground(UpdateFragment fragment, Void... voids) {
            for( ProviderAdapter.ProviderWrapper provider : fragment.providers ) {
                if( !provider.isChecked() )
                    continue;
                provider.download();
            }
            return null;
        }

        @Override
        protected void onPostExecute(UpdateFragment fragment, Void aVoid) {
            fragment.updateTask = null;
            fragment.count();
            fragment.endProgress();
        }
    }
}
