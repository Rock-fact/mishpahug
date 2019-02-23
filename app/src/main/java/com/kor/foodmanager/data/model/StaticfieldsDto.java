package com.kor.foodmanager.data.model;

import java.util.ArrayList;
import java.util.List;

public class StaticfieldsDto {
    private List<String> confession;
    private List<String> gender;
    private List<String> maritalStatus;
    private List<String> foodPreferences;
    private List<String> languages;
    private List<String> holiday;



    public List<String> getConfession() {
        return confession;
    }

    public void setConfession(List<String> confession) {
        this.confession = confession;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public List<String> getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(List<String> maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public List<String> getFoodPreferences() {
        return foodPreferences;
    }

    public void setFoodPreferences(List<String> foodPreferences) {
        this.foodPreferences = foodPreferences;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getHoliday() {
        return holiday;
    }

    public void setHoliday(List<String> holiday) {
        this.holiday = holiday;
    }

    public StaticfieldsDto() {
        this.confession = new ArrayList<>();
        this.gender = new ArrayList<>();
        this.maritalStatus = new ArrayList<>();
        this.foodPreferences = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.holiday = new ArrayList<>();
    }

    public StaticfieldsDto(List<String> confession, List<String> gender, List<String> maritalStatus, List<String> foodPreferences, List<String> languages, List<String> holiday) {
        this.confession = confession;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.foodPreferences = foodPreferences;
        this.languages = languages;
        this.holiday = holiday;
    }
}
