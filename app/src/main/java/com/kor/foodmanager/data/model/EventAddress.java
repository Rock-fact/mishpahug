package com.kor.foodmanager.data.model;

import android.location.Location;

class EventAddress {
    private String city, place_id;
    private LocationDto location;

    public EventAddress() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }
}

// \"city\": \"Tel Aviv-Yafo\",
//         \"place_id\":\"ChIJH3w7GaZMHRURkD-WwKJy-8E\",
//         \"location\":   {
//         \"lat\": 32.109333,
//         \"lng\": 34.855499

