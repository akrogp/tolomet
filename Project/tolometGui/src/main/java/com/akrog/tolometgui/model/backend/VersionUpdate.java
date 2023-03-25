package com.akrog.tolometgui.model.backend;

import java.util.ArrayList;
import java.util.List;

public class VersionUpdate {
    private int code;
    private int from;
    private final List<String> updates = new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeName() {
        int major = code / 100;
        int minor = (code % 100) / 10;
        int rev = code % 10;
        StringBuilder str = new StringBuilder("v");
        str.append(major);
        str.append('.');
        str.append(minor);
        if( rev > 0 ) {
            str.append('.');
            str.append(rev);
        }
        return str.toString();
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public List<String> getUpdates() {
        return updates;
    }
}
