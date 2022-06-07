package com.superMarket.pos.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO extends OrderDetailDTO {

    List<OrderDetailDTO> orderDetails;
    private String orderId;
    private LocalDate orderDate;
    private String cusId;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, LocalDate orderDate, String cusId, List<OrderDetailDTO> orderDetails) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.cusId = cusId;
        this.orderDetails = orderDetails;
    }

    public OrderDTO(String orderId, LocalDate orderDate, String cusId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.cusId = cusId;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", cusId='" + cusId + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
