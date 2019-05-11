package com.kor.foodmanager.data.model;

public class LocationDto {
    private Double lat, lng, radius;

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

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public LocationDto(Double lat, Double lng, Double radius) {
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "LocationDto{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", radius=" + radius +
                '}';
    }
}
