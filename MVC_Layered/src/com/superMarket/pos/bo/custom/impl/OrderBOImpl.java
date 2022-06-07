package com.superMarket.pos.bo.custom.impl;

import com.superMarket.pos.bo.custom.OrderBO;
import com.superMarket.pos.dao.DAOFactory;
import com.superMarket.pos.dao.custom.OrderDAO;
import com.superMarket.pos.dto.OrderDTO;
import com.superMarket.pos.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> all = orderDAO.getAll();
        ArrayList<OrderDTO> allOrder = new ArrayList<>();
        for (Orders orders : all) {
            allOrder.add(new OrderDTO(orders.getOrderId(), orders.getOrderDate(), orders.getCusId()));
        }
        return allOrder;
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(id);
    }

    @Override
    public boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new Orders(dto.getOrderId(), dto.getOrderDate(), dto.getCusId()));
    }

    @Override
    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new Orders(dto.getOrderId(), dto.getOrderDate(), dto.getCusId()));
    }

    @Override
    public boolean orderExit(String code) throws SQLException, ClassNotFoundException {
        return orderDAO.exist(code);
    }

    @Override
    public String generateNewOrder() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public OrderDTO searchOrder(String id) throws SQLException, ClassNotFoundException {
        Orders entity = orderDAO.search(id);
        return new OrderDTO(entity.getOrderId(), entity.getOrderDate(), entity.getCusId());
    }
}
