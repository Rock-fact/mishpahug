package com.kor.foodmanager.data.model;

public class LocationDto {
    private Double lat, lng;

    public LocationDto(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LocationDto() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
