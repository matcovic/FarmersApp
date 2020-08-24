package com.example.farmersapp.model;

public class InformationItem {

String infoBrief;
String infoPhoto;
String infoTitle;

    public InformationItem(String infoBrief, String infoPhoto, String infoTitle) {
        this.infoBrief = infoBrief;
        this.infoPhoto = infoPhoto;
        this.infoTitle = infoTitle;
    }

    public InformationItem() {
    }

    public String getInfoBrief() {
        return infoBrief;
    }

    public String getInfoPhoto() {
        return infoPhoto;
    }

    public String getInfoTitle() {
        return infoTitle;
    }
}
