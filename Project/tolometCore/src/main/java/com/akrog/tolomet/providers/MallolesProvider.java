package com.akrog.tolomet.providers;

public class MallolesProvider extends TolometProvider {
    public MallolesProvider() {
        super(5);
    }

    @Override
    protected String getApiUrl() {
        return "https://www.malloles.cat/tolomet.php";
    }

    @Override
    public String getInfoUrl(String code) {
        return getUserUrl(code);
    }

    @Override
    public String getUserUrl(String code) {
        return "https://malloles.cat/estacio/";
    }
}
