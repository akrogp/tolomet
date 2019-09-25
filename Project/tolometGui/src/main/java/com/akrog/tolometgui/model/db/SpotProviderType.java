package com.akrog.tolometgui.model.db;

import com.akrog.tolometgui.model.providers.ElliottProvider;
import com.akrog.tolometgui.model.providers.SpotProvider;

public enum SpotProviderType {
    ELLIOTT(new ElliottProvider());

    SpotProviderType(SpotProvider provider) {
        this.provider = provider;
    }

    public SpotProvider getProvider() {
        return provider;
    }

    private final SpotProvider provider;
}
