package com.example.devilsgod.binimoy.Model;

import java.io.Serializable;

public class ImportExchangeItemModel implements Serializable{
    private String imageUrl;
    private String productName;
    private String productDesc;
    private String exchangeProductName;
    private long phoneNo;

    public ImportExchangeItemModel() {
    }

    public ImportExchangeItemModel(String imageUrl, String productName, String productDesc, String exchangeProductName, long phoneNo) {
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.productDesc = productDesc;
        this.exchangeProductName = exchangeProductName;
        this.phoneNo = phoneNo;
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

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }
}
