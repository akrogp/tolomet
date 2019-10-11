package com.akrog.tolomet.providers;

public enum SpotProviderType {
    ElliottParagliding(new ElliottProvider());

    SpotProviderType(SpotProvider provider) {
        this.provider = provider;
    }

    public SpotProvider getProvider() {
        return provider;
    }

    private final SpotProvider provider;
}
