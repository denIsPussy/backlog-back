package com.onlineshop.onlineshop.Models.DTO;

public class JwtAuthenticationDTO {
    private String token;

    public String getToken() {
        return token;
    }

    public JwtAuthenticationDTO(String token){
        this.token = token;
    }
}
