package com.superMarket.pos.dao.custom.impl;

import com.superMarket.pos.dao.custom.OrderDAO;
import com.superMarket.pos.entity.Orders;
import com.superMarket.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Orders`");
        ArrayList<Orders> allOrders = new ArrayList<>();
        while (rst.next()) {
            allOrders.add(new Orders(rst.getString(1), LocalDate.parse(rst.getString(2)), rst.getString(3)));
        }
        return allOrders;
    }

    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Orders` (orderId, orderDate, cusId) VALUES (?,?,?)", entity.getOrderId(), entity.getOrderDate(), entity.getCusId());
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `Orders` SET orderDate=?, cusId=? WHERE orderId=?", entity.getOrderDate(), entity.getCusId(), entity.getOrderId());
    }

    @Override
    public Orders search(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Orders` WHERE orderId=?", oid);
        if (rst.next()) {
            return new Orders(rst.getString(1), LocalDate.parse(rst.getString(2)), rst.getString(3));
        }
        return null;
    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT orderId FROM `Orders` WHERE orderId=?", oid).next();
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Orders` WHERE orderId=?", oid);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Orders` ORDER BY orderId DESC LIMIT 1;");
        return rst.next() ? String.format("O-%03d", (Integer.parseInt(rst.getString("orderId").replace("O-", "")) + 1)) : "O-001";
    }

}
