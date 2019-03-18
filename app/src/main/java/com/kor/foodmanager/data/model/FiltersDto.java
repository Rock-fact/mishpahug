package com.kor.foodmanager.data.model;

public class FiltersDto {
    private String dateFrom, dateTo;
    private String holidays, confession, food;

    public FiltersDto() {
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getHolidays() {
        return holidays;
    }

    public void setHolidays(String holidays) {
        this.holidays = holidays;
    }

    public String getConfession() {
        return confession;
    }

    public void setConfession(String confession) {
        this.confession = confession;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}


