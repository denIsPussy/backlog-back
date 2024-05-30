package com.onlineshop.onlineshop.Models.vk;

import com.fasterxml.jackson.annotation.JsonProperty;

public class vkData {
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
}
