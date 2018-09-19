package com.akrog.tolomet.viewmodel;

import android.database.Cursor;

import java.io.Closeable;

/**
 * Created by gorka on 18/10/16.
 */

public class CursorEx implements Closeable {
    public CursorEx(Cursor cursor) {
        this.cursor = cursor;
    }

    public boolean moveToFirst() {
        return cursor.moveToFirst();
    }

    public String getString( String col ) {
        return cursor.getString(cursor.getColumnIndex(col));
    }

    public int getInt( String col ) {
        return cursor.getInt(cursor.getColumnIndex(col));
    }

    public double getDouble( String col ) {
        return cursor.getDouble(cursor.getColumnIndex(col));
    }

    @Override
    public void close() {
        cursor.close();
    }

    private final Cursor cursor;
}
