package com.example.farmersapp.model;

public class CustomListItem_Cultivation {


     public String nameBangla;
     public String nameEnglish;
     public String userPhoto;

    public CustomListItem_Cultivation(String nameBangla, String nameEnglish, String userPhoto) {
        this.nameBangla = nameBangla;
        this.nameEnglish = nameEnglish;
        this.userPhoto = userPhoto;
    }


    public String getNameBangla() {
        return nameBangla;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public String getUserPhoto() {
        return userPhoto;
    }
}
