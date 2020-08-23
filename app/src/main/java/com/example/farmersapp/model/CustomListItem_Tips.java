package com.example.farmersapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomListItem_Tips implements Parcelable {
        String tipsPhoto;
        String tipsBrief;
        String tipsId;


    public CustomListItem_Tips(String tipsPhoto, String tipsBrief, String tipsId) {
        this.tipsPhoto = tipsPhoto;
        this.tipsBrief = tipsBrief;
        this.tipsId = tipsId;
    }

    public String getTipsPhoto() {
        return tipsPhoto;
    }

    public String getTipsBrief() {
        return tipsBrief;
    }

    public String getTipsId() {
        return tipsId;
    }

    protected CustomListItem_Tips(Parcel in) {
        tipsPhoto = in.readString();
        tipsBrief = in.readString();
        tipsId = in.readString();
    }

    public static final Creator<CustomListItem_Tips> CREATOR = new Creator<CustomListItem_Tips>() {
        @Override
        public CustomListItem_Tips createFromParcel(Parcel in) {
            return new CustomListItem_Tips(in);
        }

        @Override
        public CustomListItem_Tips[] newArray(int size) {
            return new CustomListItem_Tips[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tipsPhoto);
        dest.writeString(tipsBrief);
        dest.writeString(tipsId);
    }
}
