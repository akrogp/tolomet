package com.akrog.tolomet.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private List<String> stations = new ArrayList<>();

    public MainViewModel() {
        stations.add("Punta Galea (EU)");
        stations.add("Orduña (EU)");
        stations.add("Menorca Aeropuerto (AE)");
    }

    public List<String> getStations() {
        return stations;
    }
}
