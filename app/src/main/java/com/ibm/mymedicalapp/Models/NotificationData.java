package com.ibm.mymedicalapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationData {
    @SerializedName("patient")
    @Expose
    private String patientName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("postID")
    @Expose
    private String postID;

    public NotificationData() {}

    public NotificationData(String patientName, String category, String postID) {
        this.patientName = patientName;
        this.category = category;
        this.postID = postID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
