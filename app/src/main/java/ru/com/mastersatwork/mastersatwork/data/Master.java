package ru.com.mastersatwork.mastersatwork.data;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Master {

    @Exclude
    private String id;

    private String name;
    private String email;

    private String assignedOrders;

    public Master(String masterId, String masterName, String masterEmail) {
        this.id = masterId;
        this.name = masterName;
        this.email = masterEmail;
    }

    private void setAssignedOrders(String order) {
        assignedOrders = order;
    }

    @Exclude
    public String getMasterId() {
        return id;
    }

    public void setMasterId(String masterId) {
        this.id = masterId;
    }

    public String getMasterName() {
        return name;
    }

    public void setMasterName(String masterName) {
        this.name = masterName;
    }

    public String getMasterEmail() {
        return email;
    }

    public void setMasterEmail(String masterEmail) {
        this.email = masterEmail;
    }
}
