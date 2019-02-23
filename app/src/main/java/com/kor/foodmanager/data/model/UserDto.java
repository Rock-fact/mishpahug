package com.kor.foodmanager.data.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserDto {
    private String firstName, lastName, fullName, gender, maritalStatus, confession, phoneNumber, description;
    private Date dateOfBirth;
    private List<String> pictureLink, foodPreferences, languages;
    private double rate;
    private int numberOfVoters;

    public Date parseDate(String dateStr){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date date;
        try {
            date = ft.parse(dateStr);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public UserDto() {
    }

    public UserDto(String firstName, String lastName, String fullName, String gender, String maritalStatus, String confession, String phoneNumber, String description, String dateOfBirth, List<String> pictureLink, List<String> foodPreferences, List<String> languages, double rate, int numberOfVoters) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.confession = confession;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.pictureLink = pictureLink;
        this.foodPreferences = foodPreferences;
        this.languages = languages;
        this.rate = rate;
        this.numberOfVoters = numberOfVoters;
        this.dateOfBirth = parseDate(dateOfBirth);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = parseDate(dateOfBirth);
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
