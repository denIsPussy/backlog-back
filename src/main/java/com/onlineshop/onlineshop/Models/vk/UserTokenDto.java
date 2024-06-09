package com.onlineshop.onlineshop.Models.vk;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTokenDto extends ApiResponse {
    private String token;
    private String username;
    @JsonProperty("isChildModeEnabled")
    private boolean isChildModeEnabled;

    public UserTokenDto(String token, String username, boolean success, String message, boolean isChildModeEnabled) {
        super(success, message);
        this.token = token;
        this.username = username;
        this.isChildModeEnabled = isChildModeEnabled;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isChildModeEnabled() {
        return isChildModeEnabled;
    }

    public void setChildModeEnabled(boolean childModeEnabled) {
        isChildModeEnabled = childModeEnabled;
    }
}
