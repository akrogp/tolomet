package com.akrog.tolomet.providers;

public enum SpotProviderType {
    ElliottParagliding(new ElliottProvider()),
    DhvDatabase(new DhvSpotProvider());

    SpotProviderType(SpotProvider provider) {
        this.provider = provider;
    }

    public SpotProvider getProvider() {
        return provider;
    }

    private final SpotProvider provider;
}
