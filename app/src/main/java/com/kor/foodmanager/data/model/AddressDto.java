package com.kor.foodmanager.data.model;

public class AddressDto {
    private String city, place_id;
    private LocationDto location;

    public AddressDto(String city, String place_id, LocationDto location) {
        this.city = city;
        this.place_id = place_id;
        this.location = location;
    }

    public AddressDto() {
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

    @Override
    public String toString() {
        return city + ", " + location;
//        return "AddressDto{" +
//                "city='" + city + '\'' +
//                ", place_id='" + place_id + '\'' +
//                ", location=" + location +
//                '}';
    }
}

