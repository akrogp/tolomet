package com.akrog.tolomet.utils;

import com.akrog.tolomet.Station;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gorka on 28/11/16.
 */

public class DbManager {
    private static final String PATH="/home/gorka/MyProjects/Android/Tolomet/Project/tolometGui/src/main/assets/databases/Tolomet.db";

    private static Connection connect() throws ClassNotFoundException, SQLException {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:"+PATH);
        c.setAutoCommit(false);
        return c;
    }

    public static void addStations(List<Station> stations) throws SQLException, ClassNotFoundException {
        Connection c = connect();
        PreparedStatement insert = c.prepareStatement("INSERT INTO Station (id,code,name,provider,region,latitude,longitude) VALUES (?,?,?,?,?,?,?)");
        for(Station station : stations) {
            //System.out.println(station.getId());
            insert.setString(1,station.getId());
            insert.setString(2,station.getCode());
            insert.setString(3,station.getName());
            insert.setString(4,station.getProviderType().name());
            insert.setInt(5,station.getRegion());
            insert.setDouble(6,station.getLatitude());
            insert.setDouble(7,station.getLongitude());
            insert.executeUpdate();
        }
        c.commit();
        insert.close();
        c.close();
    }
}
