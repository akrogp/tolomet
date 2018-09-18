package com.akrog.tolomet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorka on 18/05/16.
 */
public class FlySpot {
    private String name;
    private String country;
    private final List<FlyConstraint> constraints = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<FlyConstraint> getConstraints() {
        return constraints;
    }

    public boolean isValid() {
        if( name == null || name.isEmpty() || country == null || country.isEmpty() )
            return false;
        for( FlyConstraint constraint : constraints )
            if( !constraint.isValid() )
                return false;
        return true;
    }
}