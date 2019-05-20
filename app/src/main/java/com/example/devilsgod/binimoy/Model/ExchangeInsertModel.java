package com.example.devilsgod.binimoy.Model;

public class ExchangeInsertModel {


    String userID;
    String imageUrl;
    String productName;
    String productDesc;
    String exchangeProductName;
    Integer phoneNo;

    public ExchangeInsertModel() {
    }

    public ExchangeInsertModel(String userID, String imageUrl, String productName, String productDesc, String exchangeProductName, Integer phoneNo) {
        this.userID = userID;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.productDesc = productDesc;
        this.exchangeProductName = exchangeProductName;
        this.phoneNo = phoneNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getExchangeProductName() {
        return exchangeProductName;
    }

    public void setExchangeProductName(String exchangeProductName) {
        this.exchangeProductName = exchangeProductName;
    }

    public Integer getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Integer phoneNo) {
        this.phoneNo = phoneNo;
    }
}