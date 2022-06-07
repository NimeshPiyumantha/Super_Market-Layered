package com.superMarket.pos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetailDTO implements Serializable {
    private String orderId;
    private String itemCode;
    private String description;
    private BigDecimal unitPrice;
    private int orderQty;
    private BigDecimal discount;
    private BigDecimal total;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderId, String itemCode, String description, BigDecimal unitPrice, int orderQty, BigDecimal discount, BigDecimal total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.orderQty = orderQty;
        this.discount = discount;
        this.total = total;
    }

    public OrderDetailDTO(int orderQty, BigDecimal discount,BigDecimal total, String itemCode) {
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
        this.total=total;
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

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
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
