package com.onlineshop.onlineshop.Models.vk;

import java.util.List;

public class vkProfileInfo {
    private String firstName;
    private String lastName;
    private String maidenName;
    private String screenName;
    private Integer sex;
    private Integer relation;
    private User relationPartner;
    private Integer relationPending;
    private List<User> relationRequests;
    private String bdate;
    private Integer bdateVisibility;
    private String homeTown;
    private Country country;
    private City city;
    private NameRequest nameRequest;
    private String status;
    private String phone;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public User getRelationPartner() {
        return relationPartner;
    }

    public void setRelationPartner(User relationPartner) {
        this.relationPartner = relationPartner;
    }

    public Integer getRelationPending() {
        return relationPending;
    }

    public void setRelationPending(Integer relationPending) {
        this.relationPending = relationPending;
    }

    public List<User> getRelationRequests() {
        return relationRequests;
    }

    public void setRelationRequests(List<User> relationRequests) {
        this.relationRequests = relationRequests;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public Integer getBdateVisibility() {
        return bdateVisibility;
    }

    public void setBdateVisibility(Integer bdateVisibility) {
        this.bdateVisibility = bdateVisibility;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public NameRequest getNameRequest() {
        return nameRequest;
    }

    public void setNameRequest(NameRequest nameRequest) {
        this.nameRequest = nameRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

