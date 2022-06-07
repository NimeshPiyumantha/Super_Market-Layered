package com.superMarket.pos.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class CustomEntity {
    private int sumItemOrder;
    @Id
    private String cusId;
    private String cusTitle;
    private String cusName;
    private String cusAddress;
    private String city;
    private String province;
    private String postalCode;
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

    public CustomEntity() {
    }

    public CustomEntity(String orderDate, int itemSoldQty, int orderCount, BigDecimal sumOfTotal) {
        this.orderDate = orderDate;
        this.itemSoldQty = itemSoldQty;
        this.orderCount = orderCount;
        this.sumOfTotal = sumOfTotal;
    }

    public CustomEntity(String orderDate, BigDecimal sumOfTotal) {
        this.orderDate = orderDate;
        this.sumOfTotal = sumOfTotal;
    }

    public CustomEntity(String itemCode, String description, String packSize, int qtyOnHand, BigDecimal unitPrice, int orderQty, BigDecimal sumOfTotal) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.orderQty = orderQty;
        this.sumOfTotal = sumOfTotal;

    }

    public CustomEntity(String orderDate, String orderId, String cusId, String itemCode, String description, BigDecimal unitPrice, int orderQty, BigDecimal discount, BigDecimal sumOfTotal) {
        this.cusId = cusId;
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderQty = orderQty;
        this.discount = discount;
        this.sumOfTotal = sumOfTotal;
    }

    public int getSumItemOrder() {
        return sumItemOrder;
    }

    public void setSumItemOrder(int sumItemOrder) {
        this.sumItemOrder = sumItemOrder;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusTitle() {
        return cusTitle;
    }

    public void setCusTitle(String cusTitle) {
        this.cusTitle = cusTitle;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
        return "CustomEntity{" +
                "sumItemOrder=" + sumItemOrder +
                ", cusId='" + cusId + '\'' +
                ", cusTitle='" + cusTitle + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
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
