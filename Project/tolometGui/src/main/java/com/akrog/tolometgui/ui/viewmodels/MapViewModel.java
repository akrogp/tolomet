package com.akrog.tolometgui.ui.viewmodels;

import com.akrog.tolometgui.model.db.SpotEntity;

import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private SpotEntity spot;

    public SpotEntity getSpot() {
        return spot;
    }

    public void setSpot(SpotEntity spot) {
        this.spot = spot;
    }
}
