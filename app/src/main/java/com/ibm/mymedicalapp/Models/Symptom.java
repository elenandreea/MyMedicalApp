package com.ibm.mymedicalapp.Models;

public class Symptom {
    private String symptomName;
    private String symptomCategory;
    private int symptomNo;
    private boolean isChecked;

    public Symptom(){}

    public Symptom(String symptomName, String symptomCategory, int symptomNo) {
        this.symptomName = symptomName;
        this.symptomCategory = symptomCategory;
        this.symptomNo = symptomNo;
        this.isChecked = false;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getSymptomCategory() {
        return symptomCategory;
    }

    public void setSymptomCategory(String symptomCategory) {
        this.symptomCategory = symptomCategory;
    }

    public int getSymptomNo() {
        return symptomNo;
    }

    public void setSymptomNo(int symptomNo) {
        this.symptomNo = symptomNo;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
