package ru.com.mastersatwork.mastersatwork.data;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @PropertyName("Customer")
    @Expose
    private String customer;
    @SerializedName("CustomerComment")
    @Expose
    private String customerComment;
    @SerializedName("District")
    @Expose
    private String district;

    @PropertyName("InitialAmount")
    @Expose
    private int initialAmount;

    @SerializedName("Master")
    @Expose
    private String master;
    @SerializedName("MasterComment")
    @Expose
    private String masterComment;
    @SerializedName("TotalAmount")
    @Expose
    private int totalAmount;

    @PropertyName("Work")
    @Expose
    private String work;

    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("formationDate")
    @Expose
    private String formationDate;
    @SerializedName("idOrders")
    @Expose
    private String idOrders;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("takenDate")
    @Expose
    private String takenDate;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(int initialAmount) {
        this.initialAmount = initialAmount;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getMasterComment() {
        return masterComment;
    }

    public void setMasterComment(String masterComment) {
        this.masterComment = masterComment;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFormationDate() {
        return formationDate;
    }

    public void setFormationDate(String formationDate) {
        this.formationDate = formationDate;
    }

    public String getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(String idOrders) {
        this.idOrders = idOrders;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(String takenDate) {
        this.takenDate = takenDate;
    }

}