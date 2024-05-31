package com.onlineshop.onlineshop.Models.vk;

public class UserTokenDto extends  ApiResponse {
    private String token;
    private String username;

    public UserTokenDto(String token, String username, boolean success, String message) {
        super(success, message);
        this.token = token;
        this.username = username;
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
}
