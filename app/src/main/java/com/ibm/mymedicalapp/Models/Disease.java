package com.ibm.mymedicalapp.Models;

public class Disease {
    private String diseaseName;
    private String diseaseDescription;
    private String diseasePrecautions;

    public Disease() {}

    public Disease(String diseaseName, String diseaseDescription, String diseasePrecautions) {
        this.diseaseName = diseaseName;
        this.diseaseDescription = diseaseDescription;
        this.diseasePrecautions = diseasePrecautions;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public String getDiseasePrecautions() {
        return diseasePrecautions;
    }

    public void setDiseasePrecautions(String diseasePrecautions) {
        this.diseasePrecautions = diseasePrecautions;
    }
}
