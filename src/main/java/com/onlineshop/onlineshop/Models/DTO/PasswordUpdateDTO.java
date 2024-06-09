package com.onlineshop.onlineshop.Models.DTO;

public class PasswordUpdateDTO {
    private String oldPassword;
    private String newPassword;

    public PasswordUpdateDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
