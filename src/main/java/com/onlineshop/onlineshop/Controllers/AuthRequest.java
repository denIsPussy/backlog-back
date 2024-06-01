package com.onlineshop.onlineshop.Controllers;

public class AuthRequest {
    private String username;
    private String password;
    private int vkId;

    public AuthRequest() {
    }

    public AuthRequest(String username, String password, int vkId) {
        this.username = username;
        this.password = password;
        this.vkId = vkId;
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthRequest(int vkId) {
        this.vkId = vkId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVkId() {
        return vkId;
    }

    public void setVkId(int vkId) {
        this.vkId = vkId;
    }
}

