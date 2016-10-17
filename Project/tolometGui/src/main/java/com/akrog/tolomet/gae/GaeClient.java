package com.akrog.tolomet.gae;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GaeClient extends AsyncTask<String, Void, String> {
	private GaeManager manager;

	public GaeClient( GaeManager manager ) {
		this.manager = manager;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();	        
    }
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
	}
	
	@Override
	protected String doInBackground(String... urls) {
		StringBuilder builder = new StringBuilder();
    	try {
    		URL url = new URL(urls[0]);
    		URLConnection con = url.openConnection();
    		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String line;
    		while( (line=rd.readLine()) != null && !isCancelled() )
    			builder.append(line);
    		rd.close();
    	} catch( Exception e ) {
    		System.out.println(e.getMessage());
    		cancel(true);
		}
    	return builder.toString();
	}		
	
	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        manager.onMotd(Motd.getInstance(result));
    }
}
