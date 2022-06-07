package com.superMarket.pos.bo.custom;

import com.superMarket.pos.bo.SuperBO;
import com.superMarket.pos.dto.CustomerDTO;
import com.superMarket.pos.dto.ItemDTO;
import com.superMarket.pos.dto.OrderDTO;
import com.superMarket.pos.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    OrderDTO searchOrder(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String code) throws SQLException, ClassNotFoundException;

    boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    boolean orderExit(String code) throws SQLException, ClassNotFoundException;

    String generateNewOrder() throws SQLException, ClassNotFoundException;
}
