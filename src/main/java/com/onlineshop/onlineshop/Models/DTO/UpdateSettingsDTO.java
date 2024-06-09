package com.onlineshop.onlineshop.Models.DTO;

public class UpdateSettingsDTO {
    private boolean isEnabled;
    private String password;

    public UpdateSettingsDTO() {
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getPassword() {
        return password;
    }
}
