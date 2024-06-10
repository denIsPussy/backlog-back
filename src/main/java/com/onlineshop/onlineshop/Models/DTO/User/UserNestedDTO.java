package com.onlineshop.onlineshop.Models.DTO.User;

import com.onlineshop.onlineshop.Models.Database.User.User;

public class UserNestedDTO {
    private String username;
    private String firstName;
    private String lastName;

    public UserNestedDTO(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public UserNestedDTO(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
