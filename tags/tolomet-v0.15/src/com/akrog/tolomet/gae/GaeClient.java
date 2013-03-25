package com.akrog.tolomet.gae;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

import com.akrog.tolomet.Tolomet;

public class GaeClient extends AsyncTask<String, Void, String> {
	private Tolomet mTolomet;

	public GaeClient( Tolomet tolomet ) {
		mTolomet = tolomet;		
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
    		onCancelled();
		}
    	return builder.toString();
	}		
	
	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mTolomet.onMotd(Motd.getInstance(result));
    }
}
