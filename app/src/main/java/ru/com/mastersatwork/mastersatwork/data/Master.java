package ru.com.mastersatwork.mastersatwork.data;

public class Master {
    private String masterId;
    private String masterName;
    private String masterEmail;

    public Master(String masterId, String masterName, String masterEmail) {
        this.masterId = masterId;
        this.masterName = masterName;
        this.masterEmail = masterEmail;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getMasterEmail() {
        return masterEmail;
    }

    public void setMasterEmail(String masterEmail) {
        this.masterEmail = masterEmail;
    }
}
