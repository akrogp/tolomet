package com.akrog.tolomet.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

public class GzDownloader extends Downloader {
    @Override
    protected InputStream getInputStream(HttpURLConnection con) throws IOException {
        return new GZIPInputStream(con.getInputStream());
    }
}
