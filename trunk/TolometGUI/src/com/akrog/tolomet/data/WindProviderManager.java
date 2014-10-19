package com.akrog.tolomet.data;

import java.util.Calendar;
import java.util.TimeZone;

import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.providers.AemetProvider;
import com.akrog.tolomet.data.providers.EuskalmetProvider;
import com.akrog.tolomet.data.providers.LaRiojaProvider;
import com.akrog.tolomet.data.providers.MeteoGaliciaProvider;
import com.akrog.tolomet.data.providers.MeteoNavarraProvider;
import com.akrog.tolomet.data.providers.MeteocatProvider;
import com.akrog.tolomet.data.providers.RedVigiaProvider;

public class WindProviderManager {
	public WindProviderManager( Tolomet tolomet ) {
		this.providers = new WindProvider[WindProviderType.values().length];
		this.providers[0] = new EuskalmetProvider( tolomet );
		this.providers[1] = new MeteoNavarraProvider( tolomet );
		this.providers[2] = new AemetProvider( tolomet );
		this.providers[3] = new LaRiojaProvider( tolomet );
		this.providers[4] = new MeteoGaliciaProvider( tolomet );
		this.providers[5] = new RedVigiaProvider( tolomet );
		this.providers[6] = new MeteocatProvider( tolomet );
	}
	
	/*public String getUrl( Station station ) {	
		return this.providers[station.provider.getValue()].getUrl(station, this.past, this.now);
	}*/
	
	public void download( Station station ) {		
		this.providers[station.provider.getValue()].download(station, this.past, this.now);
	}
	
	public void cancelDownload( Station station ) {
		this.providers[station.provider.getValue()].cancelDownload();
	}
	
	public String getInfoUrl( Station station ) {
		return this.providers[station.provider.getValue()].getInfoUrl(station.code);
	}
	
	/*public void updateStation( Station station, String data ) {
		this.providers[station.provider.getValue()].updateStation(station, data);
	}*/
	
	public int getRefresh( Station station ) {
		return this.providers[station.provider.getValue()].getRefresh();
	}

	public boolean updateTimes( Station station ) {
		this.now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		this.past = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		
		// First execution
		if( station.isEmpty() ) {
			this.past.set(Calendar.HOUR_OF_DAY,0);
			this.past.set(Calendar.MINUTE,0);
			return true;
		}
		
		// Check update interval
		this.past.setTimeInMillis((Long)station.listDirection.get(station.listDirection.size()-2));
		if( (this.now.getTimeInMillis()-this.past.getTimeInMillis()) <= this.providers[station.provider.getValue()].getRefresh()*60*1000 )
			return false;
		
		// Clear cache
		if( this.past.get(Calendar.YEAR) == this.now.get(Calendar.YEAR) && this.past.get(Calendar.DAY_OF_YEAR) == this.now.get(Calendar.DAY_OF_YEAR) )
			return true;
		this.past.setTimeInMillis(this.now.getTimeInMillis());
		this.past.set(Calendar.HOUR_OF_DAY,0);
		this.past.set(Calendar.MINUTE, 0);
		this.past.set(Calendar.SECOND, 0);
		station.clear();			
		return true;
	}		
		
	private Calendar now, past;
	private WindProvider[] providers;
}
