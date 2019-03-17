package com.kor.foodmanager.data.model;

public class EventsInProgressRequestDto {
    private LocationDto location;
    private FiltersDto filters;

    public EventsInProgressRequestDto() {
        filters = new FiltersDto();
        location = new LocationDto();
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public FiltersDto getFilters() {
        return filters;
    }

    public void setFilters(FiltersDto filters) {
        this.filters = filters;
    }
}
