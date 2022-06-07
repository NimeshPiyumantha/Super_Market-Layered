package com.superMarket.pos.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "`Orders`")
public class Orders {
    @Id
    private String orderId;
    private LocalDate orderDate;
    private String cusId;

    public Orders() {
    }

    public Orders(String orderId, LocalDate orderDate, String cusId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.cusId = cusId;
    }

    public Orders(String string, LocalDate parse, int anInt) {
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getCusId() {
        return cusId;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", cusId='" + cusId + '\'' +
                '}';
    }
}
