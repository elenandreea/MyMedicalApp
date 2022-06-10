package com.ibm.mymedicalapp.Models;

public class Alarm {
    private int alarmNumericalID;
    private String alarmID;
    private String title;
    private String quantity;
    private String date;
    private String time;
    private String mRepeat;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;

    public Alarm() {}

    public Alarm(String title, String quantity, String date, String time, String mRepeat, String mRepeatNo, String mRepeatType, String mActive) {
        this.title = title;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.mRepeat = mRepeat;
        this.mRepeatNo = mRepeatNo;
        this.mRepeatType = mRepeatType;
        this.mActive = mActive;
    }

    public int getAlarmNumericalID() {
        return alarmNumericalID;
    }

    public void setAlarmNumericalID(int alarmNumericalID) {
        this.alarmNumericalID = alarmNumericalID;
    }

    public String getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getmRepeat() {
        return mRepeat;
    }

    public void setmRepeat(String mRepeat) {
        this.mRepeat = mRepeat;
    }

    public String getmRepeatNo() {
        return mRepeatNo;
    }

    public void setmRepeatNo(String mRepeatNo) {
        this.mRepeatNo = mRepeatNo;
    }

    public String getmRepeatType() {
        return mRepeatType;
    }

    public void setmRepeatType(String mRepeatType) {
        this.mRepeatType = mRepeatType;
    }

    public String getmActive() {
        return mActive;
    }

    public void setmActive(String mActive) {
        this.mActive = mActive;
    }
}
