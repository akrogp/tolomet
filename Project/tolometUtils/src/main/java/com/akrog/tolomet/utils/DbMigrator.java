package com.akrog.tolomet.utils;

import com.akrog.tolomet.Region;
import com.akrog.tolomet.Station;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gorka on 17/10/16.
 */

public class DbMigrator {
    private static final String PATH="/home/gorka/MyProjects/Android/Tolomet/Project/tolometGui/src/main/assets/databases/Tolomet.db";

    public static void main(String[] args) throws Exception {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:"+PATH);
        c.setAutoCommit(false);
        addRegions(c);
        addStations(c);
        c.close();
    }

    private static void addStations(Connection c) throws Exception {
        PreparedStatement query = c.prepareStatement("SELECT * FROM Region WHERE name=?");
        PreparedStatement insert = c.prepareStatement("INSERT INTO Station (id,code,name,provider,region,latitude,longitude) VALUES (?,?,?,?,?,?,?)");
        for(Station station : ResourceManager.loadAllStations()) {
            List<Region> regions = null;
            try {
                regions = ResourceManager.loadRegions(station.getCountry());
            } catch (Exception e) {}
            String regionName = regions == null ? station.getCountry() : regions.get(station.getRegion()-1).getName();
            query.setString(1,regionName);
            ResultSet rs = query.executeQuery();
            rs.next();
            if( station.getName().equals("Enewetak/Marshal") )
                station.setCode("ENIP");
            if( station.getName().equals("Ujae Atoll/Marsh") )
                station.setCode("UJAP");
            insert.setString(1,station.getId());
            insert.setString(2,station.getCode());
            insert.setString(3,station.getName());
            insert.setString(4,station.getProviderType().name());
            insert.setInt(5,rs.getInt("id"));
            insert.setDouble(6,station.getLatitude());
            insert.setDouble(7,station.getLongitude());
            insert.executeUpdate();
        }
        c.commit();
        query.close();
        insert.close();
    }

    private static void addRegions(Connection c) throws Exception {
        Set<String> countries = new HashSet<>();
        for( Station station : ResourceManager.loadAllStations() )
            countries.add(station.getCountry());
        PreparedStatement insert = c.prepareStatement("INSERT INTO Region (name,country) VALUES (?,?)");
        for( String country : countries ) {
            List<Region> regions = null;
            try {
                regions = ResourceManager.loadRegions(country);
            } catch (Exception e) {}
            if( regions == null ) {
                insert.setString(1,country);
                insert.setString(2,country);
                insert.executeUpdate();
            } else {
                for( Region region : regions ) {
                    insert.setString(1,region.getName());
                    insert.setString(2,country);
                    insert.executeUpdate();
                }
            }
        }
        c.commit();
        insert.close();
    }
}
