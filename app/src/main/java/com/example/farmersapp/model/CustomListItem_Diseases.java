package com.example.farmersapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomListItem_Diseases implements Parcelable {

    String diseaseBiologicalControl;
    String diseaseBrief;
    String diseaseCause;
    String diseaseChemicalControl;
    String diseaseScientificName;
    String diseaseTitle;
    String diseaseType;
    String diseaseId;
    String diseasePhoto;

    public CustomListItem_Diseases(String diseaseBiologicalControl, String diseaseBrief, String diseaseCause, String diseaseChemicalControl, String diseaseScientificName, String diseaseTitle, String diseaseType, String diseaseId, String diseasePhoto) {
        this.diseaseBiologicalControl = diseaseBiologicalControl;
        this.diseaseBrief = diseaseBrief;
        this.diseaseCause = diseaseCause;
        this.diseaseChemicalControl = diseaseChemicalControl;
        this.diseaseScientificName = diseaseScientificName;
        this.diseaseTitle = diseaseTitle;
        this.diseaseType = diseaseType;
        this.diseaseId = diseaseId;
        this.diseasePhoto = diseasePhoto;
    }

    protected CustomListItem_Diseases(Parcel in) {
        diseaseBiologicalControl = in.readString();
        diseaseBrief = in.readString();
        diseaseCause = in.readString();
        diseaseChemicalControl = in.readString();
        diseaseScientificName = in.readString();
        diseaseTitle = in.readString();
        diseaseType = in.readString();
        diseaseId = in.readString();
        diseasePhoto = in.readString();
    }

    public static final Creator<CustomListItem_Diseases> CREATOR = new Creator<CustomListItem_Diseases>() {
        @Override
        public CustomListItem_Diseases createFromParcel(Parcel in) {
            return new CustomListItem_Diseases(in);
        }

        @Override
        public CustomListItem_Diseases[] newArray(int size) {
            return new CustomListItem_Diseases[size];
        }
    };

    public String getDiseaseBiologicalControl() {
        return diseaseBiologicalControl;
    }

    public String getDiseaseBrief() {
        return diseaseBrief;
    }

    public String getDiseaseCause() {
        return diseaseCause;
    }

    public String getDiseaseChemicalControl() {
        return diseaseChemicalControl;
    }

    public String getDiseaseScientificName() {
        return diseaseScientificName;
    }

    public String getDiseaseTitle() {
        return diseaseTitle;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public String getDiseasePhoto() {
        return diseasePhoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diseaseBiologicalControl);
        dest.writeString(diseaseBrief);
        dest.writeString(diseaseCause);
        dest.writeString(diseaseChemicalControl);
        dest.writeString(diseaseScientificName);
        dest.writeString(diseaseTitle);
        dest.writeString(diseaseType);
        dest.writeString(diseaseId);
        dest.writeString(diseasePhoto);
    }
}
