package com.example.farmersapp.model;

public class TipsModel {

    String tipsBrief;
    String tipsId;
    String tipsPhoto;

    public TipsModel(String tipsBrief, String tipsId, String tipsPhoto) {
        this.tipsBrief = tipsBrief;
        this.tipsId = tipsId;
        this.tipsPhoto = tipsPhoto;
    }

    public TipsModel() {
    }

    public String getTipsBrief() {
        return tipsBrief;
    }

    public String getTipsId() {
        return tipsId;
    }

    public String getTipsPhoto() {
        return tipsPhoto;
    }
}
