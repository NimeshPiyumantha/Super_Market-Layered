package com.superMarket.pos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomDTO {

    private String customerId;
    private String customerTitle;
    private String customerName;
    private String customerAddress;
    private String customerCity;
    private String customerProvince;
    private String customerPostalCode;

    private String itemCode;
    private String description;
    private String packSize;
    private BigDecimal unitPrice;
    private int qtyOnHand;

    private String orderId;
    private String orderDate;

    private int orderQty;
    private BigDecimal discount;

    private int itemSoldQty;
    private int orderCount;
    private BigDecimal sumOfTotal;


    public CustomDTO() {
    }
    public CustomDTO(String orderDate, int itemSoldQty, int orderCount, BigDecimal sumOfTotal) {
        this.orderDate = orderDate;
        this.itemSoldQty = itemSoldQty;
        this.orderCount = orderCount;
        this.sumOfTotal = sumOfTotal;
    }

    public CustomDTO(String orderDate, BigDecimal sumOfTotal) {
        this.orderDate = orderDate;
        this.sumOfTotal = sumOfTotal;
    }

    public CustomDTO(String itemCode, String description, String packSize, int qtyOnHand, BigDecimal unitPrice, int orderQty, BigDecimal sumOfTotal) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.orderQty = orderQty;
        this.sumOfTotal = sumOfTotal;

    }

    public CustomDTO(String orderDate, String orderId, String cusId, String itemCode, String description, BigDecimal unitPrice, int orderQty, BigDecimal discount, BigDecimal sumOfTotal) {
        this.customerId = cusId;
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderQty = orderQty;
        this.discount = discount;
        this.sumOfTotal = sumOfTotal;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getItemSoldQty() {
        return itemSoldQty;
    }

    public void setItemSoldQty(int itemSoldQty) {
        this.itemSoldQty = itemSoldQty;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getSumOfTotal() {
        return sumOfTotal;
    }

    public void setSumOfTotal(BigDecimal sumOfTotal) {
        this.sumOfTotal = sumOfTotal;
    }

    @Override
    public String toString() {
        return "CustomDTO{" +
                "customerId='" + customerId + '\'' +
                ", customerTitle='" + customerTitle + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerCity='" + customerCity + '\'' +
                ", customerProvince='" + customerProvince + '\'' +
                ", customerPostalCode='" + customerPostalCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", itemSoldQty=" + itemSoldQty +
                ", orderCount=" + orderCount +
                ", sumOfTotal=" + sumOfTotal +
                '}';
    }
}
