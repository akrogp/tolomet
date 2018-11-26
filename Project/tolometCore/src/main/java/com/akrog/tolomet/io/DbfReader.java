package com.akrog.tolomet.io;

import java.io.IOException;
import java.io.InputStream;

public class DbfReader {
    private final InputStream is;
    private DbfHeader header;

    public static class DbfHeader {
        int records;
        int offset;
    }

    public DbfReader(InputStream is) {
        this.is = is;
    }

    public DbfHeader readHeader() throws IOException {
        if( header != null )
            return header;
        header = new DbfHeader();
        is.skip(4);
    }
}
