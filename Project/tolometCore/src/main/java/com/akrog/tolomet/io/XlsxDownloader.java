package com.akrog.tolomet.io;

import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XlsxDownloader extends Downloader {
    public XlsxDownloader(long timeout, int retries) {
        super(timeout, retries);
    }

    public XlsxDownloader() {
        super();
    }

    @Override
    protected String parseInput(InputStream is, String stop, String charset) throws Exception {
        try(ReadableWorkbook wb = new ReadableWorkbook(is)) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {
                return rows.map(row ->
                    row.stream().map(Cell::getRawValue).collect(Collectors.joining("|"))
                ).collect(Collectors.joining("\n"));
            }
        }
    }
}
