package com.akrog.tolomet.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class Downloader {
	public enum FakeBrowser { ANDROID, MOZILLA, WGET };
	
	private String url;
	private String query;
	private String method;
	private final List<Entry<String,Object>> params = new ArrayList<Entry<String,Object>>();
	protected boolean usingLinebreak = true;
	private boolean cancelled = false;
	private FakeBrowser fakeBrowser = FakeBrowser.ANDROID;
	
	public String download() {
		return download(null);
	}
	
	public String download( String stop ) {
		/*CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);*/
		String result = "";
    	try {    		
    		HttpURLConnection con;
    		if( this.method != null && this.method.equalsIgnoreCase("POST") ) {
    			URL url = new URL(this.url);
    			con = (HttpURLConnection)url.openConnection();
    			con.setDoOutput(true);
    			con.setDoInput(true);
    			OutputStream os = con.getOutputStream();
    			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
    			wr.write(getQuery());
    			wr.close();
    			os.close();
    		} else {
    			URL url = new URL(this.url+"?"+getQuery());
    			con = (HttpURLConnection)url.openConnection();
    		}
    		applyBrowserProperties(con);
    		result = parseInput(con.getInputStream(),stop);
    	} catch( Exception e ) {
    		e.printStackTrace();
    		//onCancelled();
		}
    	return result;
	}
	
	public void setBrowser( FakeBrowser fakeBrowser ) {
		this.fakeBrowser = fakeBrowser;
	}
	
	private void applyBrowserProperties( HttpURLConnection con ) {
		switch( fakeBrowser ) {
			case WGET:
				con.setRequestProperty("User-Agent","Wget/1.13.4 (linux-gnu)");
				break;
			case MOZILLA:
				con.setRequestProperty("User-Agent","Mozilla/5.0 (Linux)");
				break;
			default: break;
		}
		//con.setRequestProperty("Accept", "*/*");
		//con.setRequestProperty("Accept-Encoding", "");
	}
	
	public void useLineBreak(boolean lb) {
		this.usingLinebreak = lb;
	}
	
	public void cancel() {
		cancelled = true;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	protected String parseInput( InputStream is, String stop ) throws Exception {		
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder builder = new StringBuilder();
		String line;		
		while( (line=rd.readLine()) != null ) {
			if( cancelled )
				return null;
			if( stop != null && line.contains(stop) )
				break;
			builder.append(line);
			if( this.usingLinebreak )
				builder.append('\n');
		}
		rd.close();
		return builder.toString();
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	private String getQuery() throws UnsupportedEncodingException {
		if( query != null )
			return query;
		
		StringBuilder result = new StringBuilder();
		boolean first = true;
		
		for( Entry<String,Object> entry : params ) {
			if( first )
				first = false;
			else
				result.append("&");
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
		}
    
	    return result.toString();
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addParam( final String name, final Object value ) {
		params.add(new Entry<String, Object>() {			
			@Override
			public String setValue(Object value) {
				return null;
			}			
			@Override
			public Object getValue() {
				return value;
			}			
			@Override
			public String getKey() {
				return name;
			}
		});
	}
	
	public void addParam( String name, String format, Object... values ) {
		addParam(name, String.format(format, values));
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}