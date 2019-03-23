package com.kor.foodmanager.data.model;

public class SpinnerPositionDto {
    private int holiday, city, confession, food;

    public SpinnerPositionDto() {
    }

    public SpinnerPositionDto(int holiday, int city, int confession, int food) {
        this.holiday = holiday;
        this.city = city;
        this.confession = confession;
        this.food = food;
    }

    public int getHoliday() {
        return holiday;
    }

    public void setHoliday(int holiday) {
        this.holiday = holiday;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getConfession() {
        return confession;
    }

    public void setConfession(int confession) {
        this.confession = confession;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "SpinnerPositionDto{" +
                "holiday=" + holiday +
                ", city=" + city +
                ", confession=" + confession +
                ", food=" + food +
                '}';
    }
}
