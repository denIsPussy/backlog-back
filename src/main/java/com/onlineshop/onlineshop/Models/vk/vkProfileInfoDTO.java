package com.onlineshop.onlineshop.Models.vk;

import java.util.List;

public class vkProfileInfoDTO {
    private int vkId;
    private String firstName;
    private String lastName;

    public vkProfileInfoDTO(int vkId, String firstName, String lastName) {
        this.vkId = vkId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static vkProfileInfoDTO get(vkProfileInfo vkProfileInfo, int vkId){
        return new vkProfileInfoDTO(vkId, vkProfileInfo.getFirstName(), vkProfileInfo.getLastName());
    }

    public int getVkId() {
        return vkId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
