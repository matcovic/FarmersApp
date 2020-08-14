package com.example.farmersapp.model;

public class CropsModel {


    public String nameBangla;
    public String nameEnglish;


    public CropsModel() {
    }

    public CropsModel(String nameBangla, String nameEnglish) {
        this.nameBangla = nameBangla;
        this.nameEnglish = nameEnglish;

    }

    public String getNameBangla() {
        return nameBangla;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }



    public void setNameBangla(String nameBangla) {
        this.nameBangla = nameBangla;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }
}
