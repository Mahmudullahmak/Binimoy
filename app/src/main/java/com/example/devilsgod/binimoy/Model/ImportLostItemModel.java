package com.example.devilsgod.binimoy.Model;

import java.io.Serializable;

public class ImportLostItemModel implements Serializable {
    private String lostProductImage;
    private String lostItemName;
    private String whereFound;
    private long phoneNo;

    public ImportLostItemModel() {
    }

    public ImportLostItemModel(String lostProductImage, String lostItemName, String whereFound, long phoneNo) {
        this.lostProductImage = lostProductImage;
        this.lostItemName = lostItemName;
        this.whereFound = whereFound;
        this.phoneNo = phoneNo;
    }

    public String getLostProductImage() {
        return lostProductImage;
    }

    public void setLostProductImage(String lostProductImage) {
        this.lostProductImage = lostProductImage;
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

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }
}
