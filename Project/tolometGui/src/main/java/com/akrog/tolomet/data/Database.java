package com.akrog.tolomet.data;

import com.akrog.tolomet.Tolomet;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by gorka on 14/10/16.
 */

public class Database extends SQLiteAssetHelper {

    private Database() {
        super(Tolomet.getAppContext(), DB_NAME, null, DB_VERSION);
    }

    public static synchronized Database getInstance() {
        if( instance == null )
            instance = new Database();
        return instance;
    }

    private static final String DB_NAME = "Tolomet.db";
    private static final int DB_VERSION = 1;
    private static Database instance;
}
