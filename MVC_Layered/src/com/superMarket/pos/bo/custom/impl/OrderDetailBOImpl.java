package com.superMarket.pos.bo.custom.impl;

import com.superMarket.pos.bo.custom.OrderDetailBO;
import com.superMarket.pos.dao.DAOFactory;
import com.superMarket.pos.dao.custom.OrderDetailsDAO;
import com.superMarket.pos.dto.OrderDetailDTO;
import com.superMarket.pos.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);


    @Override
    public ArrayList<OrderDetailDTO> getAllOrderDetail() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> all = orderDetailsDAO.getAll();
        ArrayList<OrderDetailDTO> orderDetail = new ArrayList<>();
        for (OrderDetails orderDetails : all) {
            orderDetail.add(new OrderDetailDTO(orderDetails.getOrderId(), orderDetails.getItemCode(),orderDetails.getDescription(),orderDetails.getUnitPrice(), orderDetails.getOrderQty(), orderDetails.getDiscount(), orderDetails.getTotal()));
        }
        return orderDetail;
    }

    @Override
    public boolean deleteOrderDetail(String code) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.delete(code);
    }

    @Override
    public boolean saveOrderDetail(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.save(new OrderDetails(dto.getOrderId(), dto.getItemCode(),dto.getDescription(),dto.getUnitPrice(), dto.getOrderQty(), dto.getDiscount(), dto.getTotal()));
    }

    @Override
    public boolean updateOrderDetail(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.update(new OrderDetails(dto.getOrderQty(), dto.getDiscount(),dto.getTotal(),dto.getItemCode()));
    }

    @Override
    public boolean orderDetailExit(String code) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.exist(code);
    }

    @Override
    public String generateNewOrderDetail() throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.generateNewID();
    }

    @Override
    public OrderDetailDTO searchOrderDetail(String id) throws SQLException, ClassNotFoundException {
        OrderDetails entity = orderDetailsDAO.search(id);
        return new OrderDetailDTO(entity.getOrderId(), entity.getItemCode(),entity.getDescription(),entity.getUnitPrice(), entity.getOrderQty(), entity.getDiscount(), entity.getTotal());
    }

}
