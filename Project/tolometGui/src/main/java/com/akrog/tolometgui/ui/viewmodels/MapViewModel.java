package com.akrog.tolometgui.ui.viewmodels;

import com.akrog.tolomet.Spot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private MutableLiveData<Spot> spot = new MutableLiveData<>();

    public Spot getSpot() {
        return spot.getValue();
    }

    public void setSpot(Spot spot) {
        this.spot.postValue(spot);
    }

    public LiveData<Spot> liveSpot() {
        return spot;
    }
}
