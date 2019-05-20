package com.example.devilsgod.binimoy.Model;

public class ImportSlidesModel {
    private String slideName;
    private String slideUrl;

    public ImportSlidesModel() {
    }

    public ImportSlidesModel(String slideName, String slideUrl) {
        this.slideName = slideName;
        this.slideUrl = slideUrl;
    }

    public String getSlideName() {
        return slideName;
    }

    public void setSlideName(String slideName) {
        this.slideName = slideName;
    }

    public String getSlideUrl() {
        return slideUrl;
    }

    public void setSlideUrl(String slideUrl) {
        this.slideUrl = slideUrl;
    }
}
