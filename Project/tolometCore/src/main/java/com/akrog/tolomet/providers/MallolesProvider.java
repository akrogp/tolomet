package com.akrog.tolomet.providers;
import com.akrog.tolomet.Station;

public class MallolesProvider extends TolometProvider {
    public MallolesProvider() {
        super(5);
    }

    @Override
    protected String getApiUrl() {
        return "https://www.malloles.cat/tolomet.php";
    }

    @Override
    public String getInfoUrl(Station sta) {
        return getUserUrl(sta);
    }

    @Override
    public String getUserUrl(Station sta) {
        return "https://malloles.cat/estacio/";
    }
}
