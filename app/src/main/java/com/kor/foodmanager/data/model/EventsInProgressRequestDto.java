package com.kor.foodmanager.data.model;

public class EventsInProgressRequestDto {
    private LocationDto location;
    private FiltersDto filters;
    private SpinnerPositionDto spinnerPositions;

    public EventsInProgressRequestDto() {
        filters = new FiltersDto();
        location = new LocationDto();
        spinnerPositions = new SpinnerPositionDto();
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

    public SpinnerPositionDto getSpinnerPositions() {
        return spinnerPositions;
    }

    public void setSpinnerPositions(SpinnerPositionDto spinnerPositions) {
        this.spinnerPositions = spinnerPositions;
    }

    @Override
    public String toString() {
        return "EventsInProgressRequestDto{" +
                "location=" + location +
                ", filters=" + filters +
                ", spinnerPositions=" + spinnerPositions +
                '}';
    }
}
