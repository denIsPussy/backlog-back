package com.onlineshop.onlineshop.Models.vk;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VkApiResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token_id")
    private String accessTokenId;

    @JsonProperty("user_id")
    private int userId;

    private String phone;

    @JsonProperty("phone_validated")
    private long phoneValidated;

    @JsonProperty("is_service")
    private boolean isService;

    private String email;

    private int source;

    @JsonProperty("source_description")
    private String sourceDescription;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenId() {
        return accessTokenId;
    }

    public void setAccessTokenId(String accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getPhoneValidated() {
        return phoneValidated;
    }

    public void setPhoneValidated(long phoneValidated) {
        this.phoneValidated = phoneValidated;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }
}
