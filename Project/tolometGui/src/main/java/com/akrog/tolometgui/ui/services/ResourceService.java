package com.akrog.tolometgui.ui.services;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderQuality;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.Tolomet;
import com.akrog.tolomet.Spot;
import com.akrog.tolomet.providers.SpotProviderType;
import com.akrog.tolomet.SpotType;

import java.util.HashMap;
import java.util.Map;

public class ResourceService {
    private static Map<String, Integer> mapProviders;
    private static Map<String, Bitmap> mapBitmap;

    synchronized public static Integer getProviderIcon(String provider) {
        if( mapProviders == null )
            createMapProviders();
        return mapProviders.get(provider);
    }

    public static Integer getProviderIcon(WindProviderType provider) {
        return getProviderIcon(provider.name());
    }

    public static Integer getProviderIcon(SpotProviderType provider) {
        return getProviderIcon(provider.name());
    }

    synchronized public static Bitmap getMarkerBitmap(String name) {
        if( mapBitmap == null )
            createMapBitmap();
        return mapBitmap.get(name);
    }

    public static Bitmap getMarketBitmap(Station station) {
        return getMarkerBitmap(station.getProviderType().getQuality().name());
    }

    public static Bitmap getMarkerBitmap(Spot spot) {
        return getMarkerBitmap(spot.getType().name());
    }

    public static int dp2px(int dp) {
        return Math.round(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static Bitmap res2bmp(int resId, int width, int height) {
        Canvas canvas = new Canvas();
        Drawable drawable = Tolomet.getAppContext().getResources().getDrawable(resId);
        width = width <= 0 ? drawable.getIntrinsicWidth() : dp2px(width);
        height = height <= 0 ? drawable.getIntrinsicHeight() : dp2px(height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    private static void createMapProviders() {
        mapProviders = new HashMap<>(WindProviderType.values().length);

        mapProviders.put(WindProviderType.Aemet.name(), R.drawable.aemet);
        mapProviders.put(WindProviderType.Euskalmet.name(), R.drawable.euskalmet);
        mapProviders.put(WindProviderType.Ffvl.name(), R.drawable.ffvl);
        mapProviders.put(WindProviderType.MeteoGalicia.name(), R.drawable.galicia);
        mapProviders.put(WindProviderType.Holfuy.name(), R.drawable.holfuy);
        mapProviders.put(WindProviderType.LaRioja.name(), R.drawable.larioja);
        mapProviders.put(WindProviderType.Meteocat.name(), R.drawable.meteocat);
        mapProviders.put(WindProviderType.MeteoClimatic.name(), R.drawable.meteoclimatic);
        mapProviders.put(WindProviderType.MeteoFrance.name(), R.drawable.meteofrance);
        mapProviders.put(WindProviderType.MeteoNavarra.name(), R.drawable.navarra);
        mapProviders.put(WindProviderType.WeatherUnderground.name(), R.drawable.wunder);
        mapProviders.put(WindProviderType.Metar.name(), R.drawable.ic_metar);
        mapProviders.put(WindProviderType.PiouPiou.name(), R.drawable.ic_piou);
        mapProviders.put(WindProviderType.Noromet.name(), R.drawable.noromet);

        mapProviders.put(SpotProviderType.ElliottParagliding.name(), R.drawable.ic_landing);
        mapProviders.put(SpotProviderType.DhvDatabase.name(), R.drawable.dhv);
    }

    private static void createMapBitmap() {
        //int med = 32;
        int big = 48;
        mapBitmap = new HashMap<>();

        mapBitmap.put(WindProviderQuality.Good.name(), res2bmp(R.drawable.ic_station_good, big, big));
        mapBitmap.put(WindProviderQuality.Medium.name(), res2bmp(R.drawable.ic_station_medium, big, big));
        mapBitmap.put(WindProviderQuality.Poor.name(), res2bmp(R.drawable.ic_station_poor, big, big));

        mapBitmap.put(SpotType.LANDING.name(), res2bmp(R.drawable.ic_landing, big, big));
        mapBitmap.put(SpotType.TAKEOFF.name(), res2bmp(R.drawable.ic_takeoff, big, big));
        mapBitmap.put(SpotType.TREKKING.name(), res2bmp(R.drawable.ic_hiker, big, big));
    }
}
