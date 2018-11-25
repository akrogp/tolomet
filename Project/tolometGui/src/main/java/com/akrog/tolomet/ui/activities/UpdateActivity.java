package com.akrog.tolomet.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolomet.ui.adapters.ProviderAdapter;
import com.akrog.tolomet.viewmodel.DbTolomet;
import com.akrog.tolomet.viewmodel.ProviderWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UpdateActivity extends ProgressActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> download());
    }

    private void count() {
        countTask = new AsyncTask<Void,Void,Map<String,DbTolomet.ProviderInfo>>() {
            @Override
            protected void onPreExecute() {
                beginProgress();
            }

            @Override
            protected Map<String, DbTolomet.ProviderInfo> doInBackground(Void... voids) {
                return DbTolomet.getInstance().getProviderCounts();
            }

            @Override
            protected void onPostExecute(Map<String, DbTolomet.ProviderInfo> map) {
                countTask = null;
                updateList(map);
                endProgress();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void download() {
        updateTask = new AsyncTask<Void,Void,List<Station>>() {
            @Override
            protected void onPreExecute() {
                beginProgress();
            }

            @Override
            protected List<Station> doInBackground(Void... voids) {
                List<Station> stations = WindProviderType.Euskalmet.getProvider().downloadStations();
                DbTolomet.getInstance().updateStations(stations);
                return stations;
            }

            @Override
            protected void onPostExecute(List<Station> stations) {
                updateTask = null;
                count();
                endProgress();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void updateList(Map<String, DbTolomet.ProviderInfo> map) {
        providers = buildWrappers(map);
        sortProviders(providers);

        ListView list = findViewById(R.id.list_providers);
        ProviderAdapter adapter = new ProviderAdapter(this, providers.toArray(new ProviderWrapper[0]), this);
        list.setAdapter(adapter);
    }

    private List<ProviderWrapper> buildWrappers(Map<String,DbTolomet.ProviderInfo> map) {
        List<ProviderWrapper> providers = new ArrayList<>(WindProviderType.values().length);
        for( WindProviderType type : WindProviderType.values() ) {
            ProviderWrapper wrapper = new ProviderWrapper(type);
            setIcon(wrapper);
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

    private void setIcon(ProviderWrapper wrapper) {
        int iconId;
        WindProviderType type = wrapper.getType();
        if( type == WindProviderType.Euskalmet )
            iconId = R.drawable.euskalmet;
        else if( type == WindProviderType.Aemet )
            iconId = R.drawable.aemet;
        else if( type == WindProviderType.Holfuy )
            iconId = R.drawable.holfuy;
        else if( type == WindProviderType.PiouPiou )
            iconId = R.drawable.ic_piou;
        else if( type == WindProviderType.Ffvl )
            iconId = R.drawable.ffvl;
        else if( type == WindProviderType.LaRioja )
            iconId = R.drawable.larioja;
        else if( type == WindProviderType.MeteoGalicia )
            iconId = R.drawable.galicia;
        else if( type == WindProviderType.Meteocat )
            iconId = R.drawable.meteocat;
        else if( type == WindProviderType.MeteoNavarra )
            iconId = R.drawable.navarra;
        else if( type == WindProviderType.Metar )
            iconId = R.drawable.ic_metar;
        else if( type == WindProviderType.MeteoClimatic )
            iconId = R.drawable.meteoclimatic;
        else if( type == WindProviderType.MeteoFrance )
            iconId = R.drawable.meteofrance;
        else if( type == WindProviderType.WeatherUnderground )
            iconId = R.drawable.wunder;
        else
            iconId = 0;
        wrapper.setIconId(iconId);
    }

    private void sortProviders(List<ProviderWrapper> providers) {
        Collections.sort(providers, (p1, p2) -> {
            WindProviderType t1 = p1.getType();
            WindProviderType t2 = p2.getType();
            if( t1.isDynamic() && !t2.isDynamic() )
                return -1;
            if( t2.isDynamic() && !t1.isDynamic() )
                return 1;
            if( p1.getIconId() > 0 && p2.getIconId() <= 0 )
                return -1;
            if( p2.getIconId() > 0 && p1.getIconId() <= 0 )
                return 1;
            if( t1.getQuality() != t2.getQuality() )
                return t1.getQuality().ordinal() - t2.getQuality().ordinal();
            return t1.toString().compareTo(t2.toString());
        });
    }

    @Override
    public void onSettingsChanged() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                endProgress();
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        updateFab();
    }

    private void updateFab() {
        boolean enabled = false;
        if( providers != null )
            for( ProviderWrapper provider : providers )
                if( provider.isChecked() ) {
                    enabled = true;
                    break;
                }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private AsyncTask<Void,Void,Map<String,DbTolomet.ProviderInfo>> countTask;
    private AsyncTask<Void,Void,List<Station>> updateTask;
    private List<ProviderWrapper> providers;
}
