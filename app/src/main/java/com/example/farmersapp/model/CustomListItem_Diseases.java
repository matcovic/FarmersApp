package com.example.farmersapp.model;

public class CustomListItem_Diseases  {

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
}
