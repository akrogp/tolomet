package com.akrog.tolomet.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorka on 18/05/16.
 */
public class WindSpot {
    private String name;
    private final List<WindConstraint> constraints = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WindConstraint> getConstraints() {
        return constraints;
    }

    public boolean isValid() {
        if( name == null || name.isEmpty() )
            return false;
        for( WindConstraint constraint : constraints )
            if( !constraint.isValid() )
                return false;
        return true;
    }
}
