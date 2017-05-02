package ru.com.mastersatwork.mastersatwork.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("idcustomers")
    @Expose
    private String idcustomers;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("privateAddress")
    @Expose
    private String privateAddress;
    @SerializedName("publicAddress")
    @Expose
    private String publicAddress;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getIdcustomers() {
        return idcustomers;
    }

    public void setIdcustomers(String idcustomers) {
        this.idcustomers = idcustomers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrivateAddress() {
        return privateAddress;
    }

    public void setPrivateAddress(String privateAddress) {
        this.privateAddress = privateAddress;
    }

    public String getPublicAddress() {
        return publicAddress;
    }

    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }
}