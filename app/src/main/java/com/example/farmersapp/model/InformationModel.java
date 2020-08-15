package com.example.farmersapp.model;

class InformationModel {

    String infoTitle;
    String infoBrief;
    String infoPhoto;

    public InformationModel(String infoTitle, String infoBrief, String infoPhoto) {
        this.infoTitle = infoTitle;
        this.infoBrief = infoBrief;
        this.infoPhoto = infoPhoto;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public String getInfoBrief() {
        return infoBrief;
    }

    public String getInfoPhoto() {
        return infoPhoto;
    }
}
