package com.kor.foodmanager.data.model;

import java.util.Arrays;

public class PlaceDetailsResultDto {
    PlaceDetailsComponentDto[] address_components;

    public PlaceDetailsResultDto(PlaceDetailsComponentDto[] address_components) {
        this.address_components = address_components;
    }

    public PlaceDetailsResultDto() {
    }

    public PlaceDetailsComponentDto[] getAddress_components() {
        return address_components;
    }

    public void setAddress_components(PlaceDetailsComponentDto[] address_components) {
        this.address_components = address_components;
    }

    @Override
    public String toString() {
        return "PlaceDetailsResultDto{" +
                "address_components=" + Arrays.toString(address_components) +
                '}';
    }
}


