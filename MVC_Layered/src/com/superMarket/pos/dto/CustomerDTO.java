package com.superMarket.pos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerDTO implements Serializable {
    private String customerId;
    private String customerTitle;
    private String customerName;
    private String customerAddress;
    private String customerCity;
    private String customerProvince;
    private String customerPostalCode;

    public CustomerDTO() {
    }

    public CustomerDTO(String customerId, String customerTitle, String customerName, String customerAddress, String customerCity, String customerProvince, String customerPostalCode) {
        this.customerId = customerId;
        this.customerTitle = customerTitle;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerCity = customerCity;
        this.customerProvince = customerProvince;
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerProvince() {
        return customerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        this.customerProvince = customerProvince;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerId='" + customerId + '\'' +
                ", customerTitle='" + customerTitle + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerCity='" + customerCity + '\'' +
                ", customerProvince='" + customerProvince + '\'' +
                ", customerPostalCode='" + customerPostalCode + '\'' +
                '}';
    }

}
