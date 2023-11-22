package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;

import java.util.ArrayList;
import java.util.List;

public class JcylSaltProvider extends JcylRoadProvider {
    @Override
    public List<Station> downloadStations() {
        List<Station> result = new ArrayList<>();
        for( Station station : super.downloadStations() )
            if( station.getProviderType() == WindProviderType.JcylSaltProvider )
                result.add(station);
        return result;
    }
}
