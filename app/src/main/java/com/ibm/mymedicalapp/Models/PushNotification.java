package com.ibm.mymedicalapp.Models;

public class PushNotification {
    private NotificationData notificationData;
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
