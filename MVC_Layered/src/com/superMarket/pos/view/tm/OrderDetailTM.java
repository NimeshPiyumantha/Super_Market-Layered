package com.superMarket.pos.view.tm;

import com.superMarket.pos.dto.OrderDetailDTO;

import java.math.BigDecimal;

public class OrderDetailTM  {
    private String orderId;
    private String cusId;
    private String itemCode;
    private String description;
    private String packSize;
    private BigDecimal unitPrice;
    private int orderQty;
    private BigDecimal discount;
    private BigDecimal total;


    public OrderDetailTM() {
    }

    public OrderDetailTM(String orderId, String cusId, String itemCode, String description, String packSize, BigDecimal unitPrice, int orderQty, BigDecimal discount, BigDecimal total) {
        this.orderId = orderId;
        this.cusId = cusId;
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.orderQty = orderQty;
        this.discount = discount;
        this.total = total;
    }

    public OrderDetailTM(String orderId, String itemCode, int orderQty, BigDecimal discount, BigDecimal total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
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
        return "OrderDetailTM{" +
                "orderId='" + orderId + '\'' +
                ", cusId='" + cusId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", unitPrice=" + unitPrice +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
