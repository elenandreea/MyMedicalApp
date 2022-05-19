package com.ibm.mymedicalapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationData {
    @SerializedName("patient")
    @Expose
    private String patient;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("postID")
    @Expose
    private String postID;
    @SerializedName("token")
    @Expose
    private String token;


    public NotificationData() {}

    public NotificationData(String userID, String patient, String category, String postID, String token) {
        this.patient = patient;
        this.category = category;
        this.postID = postID;
        this.token = token;
        this.userID = userID;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
