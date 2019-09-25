package com.akrog.tolometgui.model.providers;

import com.akrog.tolometgui.model.db.SpotEntity;

import java.util.List;

public interface SpotProvider {
    List<SpotEntity> downloadSpots();
    void cancel();
}
