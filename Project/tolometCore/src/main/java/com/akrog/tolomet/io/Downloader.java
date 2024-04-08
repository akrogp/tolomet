package com.akrog.tolomet.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

public class Downloader {
	public enum FakeBrowser {DEFAULT, MOZILLA, WGET, TOLOMET };
	
	private String url;
	private String query;
	private String method;
	private final List<Entry<String,Object>> params = new ArrayList<Entry<String,Object>>();
	private final Map<String,String> headers = new HashMap<>();
	protected boolean usingLinebreak = true;
	private boolean cancelled = false;
    private boolean gzipped = false;
	//private FakeBrowser fakeBrowser = FakeBrowser.DEFAULT;
    private FakeBrowser fakeBrowser = FakeBrowser.TOLOMET;
	private final int retries;
    private final long timeout;
    private TimeoutTask<String> task;
	private String error;

    public Downloader(long timeout, int retries) {
        this.timeout = timeout;
        this.retries = retries;
    }

    public Downloader() {
        this(15,2);
    }
	
	public String download() {
		return download(null, null);
	}

	public String download( final String stop) {
    	return download(stop, null);
	}

    public String download( final String stop, final String charset ) {
		task = new TimeoutTask<String>(timeout, retries) {
			@Override
			public String call() throws Exception {
				return rawDownload(stop, charset);
			}
		};
        String result = "";
		error = null;
        try {
            result = task.execute();
        } catch (Exception e) {
			error = e.getMessage();
            e.printStackTrace();
        }
        task = null;
        return result;
    }

    public String getError() {
        return error;
    }

	private String rawDownload( String stop, String charset ) {
		/*CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);*/
		String result = "";
        HttpURLConnection con = null;
    	try {
    		if( this.method != null && this.method.equalsIgnoreCase("POST") ) {
    			URL url = new URL(this.url);
    			con = openConnection(url);
    			con.setDoOutput(true);
    			con.setDoInput(true);
    			OutputStream os = getOutputStream(con);
    			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
    			wr.write(getQuery());
    			wr.close();
    			os.close();
    		} else {
    			URL url = new URL(params.isEmpty()?this.url:this.url+"?"+getQuery());
    			con = openConnection(url);
    		}
    		applyBrowserProperties(con);
    		applyHeaders(con);
            InputStream is = getInputStream(con);
            if( isGzipped() )
                is = new GZIPInputStream(is);
    		result = parseInput(is, stop, charset);
    	} catch( Exception e ) {
    		e.printStackTrace();
    		//onCancelled();
		} finally {
            if( con != null )
                con.disconnect();
        }
        return result;
	}

	private HttpURLConnection openConnection(URL url) throws Exception {
		// Now trusted invalid certificates will be configured externally:
		// https://developer.android.com/privacy-and-security/security-config
		return (HttpURLConnection)url.openConnection();
	}

	protected OutputStream getOutputStream(HttpURLConnection con) throws IOException {
    	return con.getOutputStream();
	}

	protected InputStream getInputStream(HttpURLConnection con) throws IOException {
		return con.getInputStream();
	}

    private void applyHeaders(HttpURLConnection con) {
        for( Entry<String, String> header : headers.entrySet() ) {
            con.setRequestProperty(header.getKey(), header.getValue());
        }
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
			case TOLOMET:
				con.setRequestProperty("User-Agent","Mozilla/5.0 (Linux) Tolomet/5.0");
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
        if( task != null ) {
            task.cancel();
            task = null;
        }
		cancelled = true;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	protected String parseInput( InputStream is, String stop, String charset ) throws Exception {
		BufferedReader rd;
		if( charset == null )
			rd = new BufferedReader(new InputStreamReader(is));
		else
			rd = new BufferedReader(new InputStreamReader(is, charset));
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

	public void setHeader(String name, String value) {
    	headers.put(name, value);
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

    public boolean isGzipped() {
        return gzipped;
    }

    public void setGzipped(boolean gzipped) {
        this.gzipped = gzipped;
    }
}
