package com.kor.foodmanager.data.model;

import java.util.Arrays;
import java.util.List;

public class PlaceDetailsComponentDto {
    String long_name;
    String short_name;
    List<String> types;

    public PlaceDetailsComponentDto() {
    }

    public PlaceDetailsComponentDto(String long_name, String short_name, List<String> types) {
        this.long_name = long_name;
        this.short_name = short_name;
        this.types = types;
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "PlaceDetailsComponentDto{" +
                "long_name='" + long_name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", types=" + types +
                '}';
    }
}
