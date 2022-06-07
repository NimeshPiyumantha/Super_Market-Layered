package com.superMarket.pos.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "`OrderDetails`")
public class OrderDetails {
    @Id
    private String orderId;
    private String itemCode;
    private String description;
    private  BigDecimal unitPrice;
    private int orderQty;
    private BigDecimal discount;
    private BigDecimal total;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String itemCode, String description, BigDecimal unitPrice, int orderQty, BigDecimal discount, BigDecimal total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.orderQty = orderQty;
        this.discount = discount;
        this.total = total;
    }

    public OrderDetails(int orderQty, BigDecimal discount, BigDecimal total, String itemCode) {
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
        this.total=total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
