package com.akrog.tolomet.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipDownloader extends Downloader {
    @Override
    protected InputStream getInputStream(HttpURLConnection con) throws IOException {
        ZipInputStream zip = new ZipInputStream(con.getInputStream());
        ZipEntry nextEntry = zip.getNextEntry();
        return zip;
    }
}
