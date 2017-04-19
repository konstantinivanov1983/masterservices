package ru.com.mastersatwork.mastersatwork.data;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Master {

    @Exclude
    private String masterId;

    private String masterName;
    private String masterEmail;

    private String assignedOrders;

    public Master(String masterId, String masterName, String masterEmail) {
        this.masterId = masterId;
        this.masterName = masterName;
        this.masterEmail = masterEmail;
    }

    private void setAssignedOrders(String order) {
        assignedOrders = order;
    }

    @Exclude
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
