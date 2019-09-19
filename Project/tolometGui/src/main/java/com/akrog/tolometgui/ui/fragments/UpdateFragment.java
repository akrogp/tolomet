package com.akrog.tolometgui.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.DbTolomet;
import com.akrog.tolometgui.ui.adapters.ProviderAdapter;
import com.akrog.tolometgui.ui.services.ResourceService;
import com.akrog.tolometgui.ui.services.WeakTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

public class UpdateFragment extends ToolbarFragment implements AdapterView.OnItemClickListener {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
        updateList(null);
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

    private void updateList(Map<String, DbTolomet.ProviderInfo> map) {
        providers = buildWrappers(map);
        sortProviders(providers);

        ListView list = getActivity().findViewById(R.id.list_providers);
        ProviderAdapter adapter = new ProviderAdapter(getActivity(), providers.toArray(new ProviderAdapter.ProviderWrapper[0]), this);
        list.setAdapter(adapter);
    }

    private List<ProviderAdapter.ProviderWrapper> buildWrappers(Map<String,DbTolomet.ProviderInfo> map) {
        List<ProviderAdapter.ProviderWrapper> providers = new ArrayList<>(WindProviderType.values().length);
        for( WindProviderType type : WindProviderType.values() ) {
            ProviderAdapter.ProviderWrapper wrapper = new ProviderAdapter.ProviderWrapper(type);
            Integer iconId = ResourceService.getProviderIcon(type);
            wrapper.setIconId(iconId == null ? 0 : iconId);
            if( map != null ) {
                DbTolomet.ProviderInfo info = map.get(type.name());
                if (info != null) {
                    wrapper.setStations(info.getStationCount());
                    if( info.getDate() != null )
                        wrapper.setDate(DATE_FORMAT.format(info.getDate()));
                }
            }
            providers.add(wrapper);
        }
        return providers;
    }

    private void sortProviders(List<ProviderAdapter.ProviderWrapper> providers) {
        Collections.sort(providers, (p1, p2) -> {
            WindProviderType t1 = p1.getType();
            WindProviderType t2 = p2.getType();
            if( t1.isDynamic() && !t2.isDynamic() )
                return -1;
            if( t2.isDynamic() && !t1.isDynamic() )
                return 1;
            if( p1.getStations() != p2.getStations() )
                return p2.getStations() - p1.getStations();
            /*if( p1.getIconId() > 0 && p2.getIconId() <= 0 )
                return -1;
            if( p2.getIconId() > 0 && p1.getIconId() <= 0 )
                return 1;*/
            if( t1.getQuality() != t2.getQuality() )
                return t1.getQuality().ordinal() - t2.getQuality().ordinal();
            return t1.toString().compareTo(t2.toString());
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        updateFab();
    }


    private static class CountTask extends WeakTask<UpdateFragment, Void, Void, Map<String, DbTolomet.ProviderInfo>> {
        CountTask(UpdateFragment fragment) {
            super(fragment);
        }

        @Override
        protected void onPreExecute() {
            UpdateFragment fragment = getContext();
            if( fragment != null )
                fragment.beginProgress();
        }

        @Override
        protected Map<String, DbTolomet.ProviderInfo> doInBackground(Void... voids) {
            return DbTolomet.getInstance().getProviderCounts();
        }

        @Override
        protected void onPostExecute(Map<String, DbTolomet.ProviderInfo> map) {
            UpdateFragment fragment = getContext();
            if( fragment == null )
                return;
            fragment.countTask = null;
            fragment.updateList(map);
            fragment.updateFab();
            fragment.endProgress();
        }
    }

    private static class UpdateTask extends WeakTask<UpdateFragment, Void, Void, Void> {
        UpdateTask(UpdateFragment fragment) {
            super(fragment);
        }

        @Override
        protected void onPreExecute() {
            UpdateFragment fragment = getContext();
            if( fragment != null )
                fragment.beginProgress();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UpdateFragment fragment = getContext();
            if( fragment == null )
                return null;
            for( ProviderAdapter.ProviderWrapper provider : fragment.providers ) {
                if( !provider.isChecked() )
                    continue;
                List<Station> stations = provider.getType().getProvider().downloadStations();
                if( stations != null )
                    DbTolomet.getInstance().updateStations(provider.getType(), stations);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            UpdateFragment fragment = getContext();
            if( fragment == null )
                return;
            fragment.updateTask = null;
            fragment.count();
            fragment.endProgress();
        }
    }
}
