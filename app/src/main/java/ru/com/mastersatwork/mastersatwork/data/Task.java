package ru.com.mastersatwork.mastersatwork.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("OrderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("CustomersName")
    @Expose
    private String customersName;
    @SerializedName("CustomersAddress")
    @Expose
    private String customersAddress;
    @SerializedName("CustomersPhone")
    @Expose
    private String customersPhone;
    @SerializedName("Job")
    @Expose
    private String job;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Comment")
    @Expose
    private String comment;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomersName() {
        return customersName;
    }

    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    public String getCustomersAddress() {
        return customersAddress;
    }

    public void setCustomersAddress(String customersAddress) {
        this.customersAddress = customersAddress;
    }

    public String getCustomersPhone() {
        return customersPhone;
    }

    public void setCustomersPhone(String customersPhone) {
        this.customersPhone = customersPhone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
