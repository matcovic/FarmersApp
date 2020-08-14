package com.example.farmersapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CustomListItem_DiseaseCategories implements Parcelable {


    public List<String> charaRoponDhap;
    public  List<String> folonDhap;
    public  List<String> fosolKatarDhap;
    public  List<String> pushpoDhap;
    public  List<String> udhvidhBordhonDhap;
    public String id;

    public CustomListItem_DiseaseCategories(List<String> charaRoponDhap, List<String> folonDhap, List<String> fosolKatarDhap, List<String> pushpoDhap, List<String> udhvidhBordhonDhap, String id) {
        this.charaRoponDhap = charaRoponDhap;
        this.folonDhap = folonDhap;
        this.fosolKatarDhap = fosolKatarDhap;
        this.pushpoDhap = pushpoDhap;
        this.udhvidhBordhonDhap = udhvidhBordhonDhap;
        this.id = id;
    }

    protected CustomListItem_DiseaseCategories(Parcel in) {
        charaRoponDhap = in.createStringArrayList();
        folonDhap = in.createStringArrayList();
        fosolKatarDhap = in.createStringArrayList();
        pushpoDhap = in.createStringArrayList();
        udhvidhBordhonDhap = in.createStringArrayList();
        id = in.readString();
    }

    public static final Creator<CustomListItem_DiseaseCategories> CREATOR = new Creator<CustomListItem_DiseaseCategories>() {
        @Override
        public CustomListItem_DiseaseCategories createFromParcel(Parcel in) {
            return new CustomListItem_DiseaseCategories(in);
        }

        @Override
        public CustomListItem_DiseaseCategories[] newArray(int size) {
            return new CustomListItem_DiseaseCategories[size];
        }
    };

    public List<String> getCharaRoponDhap() {
        return charaRoponDhap;
    }

    public List<String> getFolonDhap() {
        return folonDhap;
    }

    public List<String> getFosolKatarDhap() {
        return fosolKatarDhap;
    }

    public List<String> getPushpoDhap() {
        return pushpoDhap;
    }

    public List<String> getUdhvidhBordhonDhap() {
        return udhvidhBordhonDhap;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(charaRoponDhap);
        dest.writeStringList(folonDhap);
        dest.writeStringList(fosolKatarDhap);
        dest.writeStringList(pushpoDhap);
        dest.writeStringList(udhvidhBordhonDhap);
        dest.writeString(id);
    }
}
