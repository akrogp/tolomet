package com.akrog.tolomet.io;

import org.jamel.dbf.DbfReader;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ShpDownloader extends Downloader {
    @Override
    protected String parseInput(InputStream is, String stop) throws Exception {
        ZipInputStream zip = new ZipInputStream(is);
        ZipEntry entry;
        while( (entry=zip.getNextEntry()) != null ) {
            if( entry.getName().endsWith(".dbf") )
                break;
        }
        if( entry == null )
            return null;

        StringBuilder sb = new StringBuilder();
        try(DbfReader dbf = new DbfReader(zip)) {
            Object[] row;
            while((row=dbf.nextRecord()) != null) {
                int i = 0;
                for (; i < row.length-1; i++) {
                    sb.append(row[i].toString());
                    sb.append('\t');
                }
                sb.append(row[i].toString());
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
