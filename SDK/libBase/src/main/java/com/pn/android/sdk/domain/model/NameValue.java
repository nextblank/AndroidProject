package com.pn.android.sdk.domain.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Iterator;

public class NameValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    public NameValue() {
        super();
    }

    public NameValue(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public NameValue(JSONObject jo) {
        super();
        Iterator iterator = jo.keys();
        while (iterator.hasNext()) {
            this.name = (String) iterator.next();
            if (this.name == null || this.name.length() == 0) {
                this.value = null;
            } else {
                this.value = jo.optString(this.name);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

}
