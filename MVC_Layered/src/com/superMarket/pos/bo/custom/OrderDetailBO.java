package com.superMarket.pos.bo.custom;

import com.superMarket.pos.bo.SuperBO;
import com.superMarket.pos.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
    OrderDetailDTO searchOrderDetail(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailDTO> getAllOrderDetail() throws SQLException, ClassNotFoundException;

    boolean deleteOrderDetail(String code) throws SQLException, ClassNotFoundException;

    boolean saveOrderDetail(OrderDetailDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateOrderDetail(OrderDetailDTO dto) throws SQLException, ClassNotFoundException;

    boolean orderDetailExit(String code) throws SQLException, ClassNotFoundException;

    String generateNewOrderDetail() throws SQLException, ClassNotFoundException;

}
