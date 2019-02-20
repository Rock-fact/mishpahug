package com.kor.foodmanager.data.model;

public class UserDto {
    // TODO: 19.02.2019 Update this!
    private String firstName;
    private String lastName;

    public UserDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto() {
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
}
