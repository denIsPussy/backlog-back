package com.onlineshop.onlineshop.Models.vk;

public class VkAuthDto {
    private int vkId = 0;
    private boolean isRegistered = false;
    private String firstName = null;
    private String lastName = null;

    public VkAuthDto(int vkId, String firstName, String lastName, boolean isRegistered) {
        this.vkId = vkId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isRegistered = isRegistered;
    }
//
//    public static VkAuthDto getRegisterData(vkProfileInfo vkProfileInfo, int vkId){
//        return new VkAuthDto(vkId, vkProfileInfo.getResponse().get(0).getFirstName(), vkProfileInfo.getResponse().get(0).getLastName());
//    }
//
//    public static VkAuthDto getAuthData(int vkId, boolean isRegistered, String firstName, String lastName){
//        return new VkAuthDto(0, firstName, lastName);
//    }

    public int getVkId() {
        return vkId;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
