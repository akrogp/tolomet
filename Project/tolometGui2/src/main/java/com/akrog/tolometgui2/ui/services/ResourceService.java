package com.akrog.tolometgui2.ui.services;

import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui2.R;

import java.util.HashMap;
import java.util.Map;

public class ResourceService {
    private static Map<WindProviderType, Integer> mapProviders;

    synchronized public static Integer getProviderIcon(WindProviderType provider) {
        if( mapProviders == null )
            createMapProviders();
        return mapProviders.get(provider);
    }

    private static void createMapProviders() {
        mapProviders = new HashMap<>(WindProviderType.values().length);
        mapProviders.put(WindProviderType.Aemet, R.drawable.aemet);
        mapProviders.put(WindProviderType.Euskalmet, R.drawable.euskalmet);
        mapProviders.put(WindProviderType.Ffvl, R.drawable.ffvl);
        mapProviders.put(WindProviderType.MeteoGalicia, R.drawable.galicia);
        mapProviders.put(WindProviderType.Holfuy, R.drawable.holfuy);
        mapProviders.put(WindProviderType.LaRioja, R.drawable.larioja);
        mapProviders.put(WindProviderType.Meteocat, R.drawable.meteocat);
        mapProviders.put(WindProviderType.MeteoClimatic, R.drawable.meteoclimatic);
        mapProviders.put(WindProviderType.MeteoFrance, R.drawable.meteofrance);
        mapProviders.put(WindProviderType.MeteoNavarra, R.drawable.navarra);
        mapProviders.put(WindProviderType.WeatherUnderground, R.drawable.wunder);
        mapProviders.put(WindProviderType.Metar, R.drawable.ic_metar);
    }
}
