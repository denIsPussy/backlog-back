package com.onlineshop.onlineshop.Models.vk;

public class vkProfileInfoDTO {
    private int vkId = 0;
    private String firstName = null;
    private String lastName = null;

    public vkProfileInfoDTO(int vkId, String firstName, String lastName) {
        this.vkId = vkId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static vkProfileInfoDTO getRegisterData(vkProfileInfo vkProfileInfo, int vkId){
        return new vkProfileInfoDTO(vkId, vkProfileInfo.getResponse().get(0).getFirstName(), vkProfileInfo.getResponse().get(0).getLastName());
    }

    public static vkProfileInfoDTO getAuthData(int vkId, String firstName, String lastName){
        return new vkProfileInfoDTO(vkId, firstName, lastName);
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
