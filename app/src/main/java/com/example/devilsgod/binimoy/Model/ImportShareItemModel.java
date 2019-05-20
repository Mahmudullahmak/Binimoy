package com.example.devilsgod.binimoy.Model;

import java.io.Serializable;

public class ImportShareItemModel implements Serializable{

    private String itemName;
        private String itemDescription;
        private String mImageUrl;
        private long phoneNo;

        public ImportShareItemModel() {
        }

    public ImportShareItemModel(String itemName, String itemDescription, String mImageUrl, long phoneNo) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.mImageUrl = mImageUrl;
        this.phoneNo = phoneNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }
}


