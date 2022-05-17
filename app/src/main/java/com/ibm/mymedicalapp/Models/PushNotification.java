package com.ibm.mymedicalapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushNotification {
    @SerializedName("data")
    @Expose
    private NotificationData notificationData;
    @SerializedName("to")
    @Expose
    private String to;

    public PushNotification(NotificationData notificationData, String to) {
        this.notificationData = notificationData;
        this.to = to;
    }
    public PushNotification(NotificationData data) {
        this.notificationData = data;
    }
    public NotificationData getNotificationData() {
        return notificationData;
    }
    public void setNotificationData(NotificationData data) {
        this.notificationData = data;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String recipient) {
        this.to = recipient;
    }
}
