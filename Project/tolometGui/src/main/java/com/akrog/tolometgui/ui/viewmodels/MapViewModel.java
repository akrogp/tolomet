package com.akrog.tolometgui.ui.viewmodels;

import com.akrog.tolometgui.model.db.SpotEntity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private MutableLiveData<SpotEntity> spot = new MutableLiveData<>();

    public SpotEntity getSpot() {
        return spot.getValue();
    }

    public void setSpot(SpotEntity spot) {
        this.spot.postValue(spot);
    }

    public LiveData<SpotEntity> liveSpot() {
        return spot;
    }
}
