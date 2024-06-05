package com.onlineshop.onlineshop.Models.DTO.User;

import com.onlineshop.onlineshop.Models.EverythingElse.User;

public class SignInDTO {
    private String username;
    private String password;

    public SignInDTO(){

    }

    public SignInDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
