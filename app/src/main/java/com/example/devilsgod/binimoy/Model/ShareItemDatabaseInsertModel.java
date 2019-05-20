package com.example.devilsgod.binimoy.Model;

import android.content.Intent;
import android.net.Uri;

public class ShareItemDatabaseInsertModel {


    String itemName;
    Integer phoneNo;
    String itemDescription;
    private String mImageUrl;
    String userID;

    public ShareItemDatabaseInsertModel() {
    }

    public ShareItemDatabaseInsertModel(String itemName, Integer phoneNo, String itemDescription, String mImageUrl, String userID) {
        this.itemName = itemName;
        this.phoneNo = phoneNo;
        this.itemDescription = itemDescription;
        this.mImageUrl = mImageUrl;
        this.userID = userID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Integer phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
