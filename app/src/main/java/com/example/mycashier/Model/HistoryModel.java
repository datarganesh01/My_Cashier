package com.example.mycashier.Model;

public class HistoryModel {

    String historyName,historyTime,historyType,historyAmount;
    int historyId;
    public HistoryModel(int historyId,String historyName, String historyTime, String historyType, String historyAmount) {
        this.historyId = historyId;
        this.historyName = historyName;
        this.historyTime = historyTime;
        this.historyType = historyType;
        this.historyAmount = historyAmount;
    }

    public String getHistoryName() {
        return historyName;
    }

    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }

    public String getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(String historyTime) {
        this.historyTime = historyTime;
    }

    public String getHistoryType() {
        return historyType;
    }

    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }

    public String getHistoryAmount() {
        return historyAmount;
    }

    public void setHistoryAmount(String historyAmount) {
        this.historyAmount = historyAmount;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }
}
