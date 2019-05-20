package com.example.devilsgod.binimoy.Model;

public class InsertLostItemsModel {
    private String lostItemName;
    private String whereFound;
    private String userId;
    private String lostProductImage;
    private Integer phoneNo;

    public InsertLostItemsModel() {
    }

    public InsertLostItemsModel(String lostItemName, String whereFound, String userId, String lostProductImage, Integer phoneNo) {
        this.lostItemName = lostItemName;
        this.whereFound = whereFound;
        this.userId = userId;
        this.lostProductImage = lostProductImage;
        this.phoneNo = phoneNo;
    }

    public String getLostItemName() {
        return lostItemName;
    }

    public void setLostItemName(String lostItemName) {
        this.lostItemName = lostItemName;
    }

    public String getWhereFound() {
        return whereFound;
    }

    public void setWhereFound(String whereFound) {
        this.whereFound = whereFound;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLostProductImage() {
        return lostProductImage;
    }

    public void setLostProductImage(String lostProductImage) {
        this.lostProductImage = lostProductImage;
    }

    public Integer getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Integer phoneNo) {
        this.phoneNo = phoneNo;
    }
}
