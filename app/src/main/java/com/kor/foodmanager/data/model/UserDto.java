package com.kor.foodmanager.data.model;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private String firstName, lastName, fullName, gender, maritalStatus, confession, phoneNumber, description;
    private String dateOfBirth;
    private List<String> pictureLink, foodPreferences, languages;
    private double rate;
    private int numberOfVoters;
    private long userId;
    private Boolean isInvited;


    public UserDto() {
        pictureLink = new ArrayList<>();
        foodPreferences = new ArrayList<>();
        languages = new ArrayList<>();
    }

    public UserDto(String firstName, String lastName, String fullName, String gender, String maritalStatus, String confession, String phoneNumber, String description, String dateOfBirth, List<String> pictureLink, List<String> foodPreferences, List<String> languages, double rate, int numberOfVoters, long userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.confession = confession;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.pictureLink = pictureLink;
        this.foodPreferences = foodPreferences;
        this.languages = languages;
        this.rate = rate;
        this.numberOfVoters = numberOfVoters;
        this.userId = userId;
    }

    public Boolean getInvited() {
        return isInvited;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getConfession() {
        return confession;
    }

    public void setConfession(String confession) {
        this.confession = confession;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(List<String> pictureLink) {
        this.pictureLink = pictureLink;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getNumberOfVoters() {
        return numberOfVoters;
    }

    public void setNumberOfVoters(int numberOfVoters) {
        this.numberOfVoters = numberOfVoters;
    }
}
