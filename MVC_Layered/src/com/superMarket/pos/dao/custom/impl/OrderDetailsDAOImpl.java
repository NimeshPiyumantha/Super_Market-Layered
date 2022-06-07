package com.superMarket.pos.dao.custom.impl;

import com.superMarket.pos.dao.custom.OrderDetailsDAO;
import com.superMarket.pos.entity.OrderDetails;
import com.superMarket.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `OrderDetail`");
        ArrayList<OrderDetails> allOrderDetails = new ArrayList<>();
        while (rst.next()) {
            allOrderDetails.add(new OrderDetails(rst.getString(1),rst.getString(2), rst.getString(3),rst.getBigDecimal(4), rst.getInt(5), rst.getBigDecimal(6), rst.getBigDecimal(7)));
        }
        return allOrderDetails;
    }

    @Override
    public boolean save(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `OrderDetail` (orderId,itemCode,description,unitPrice,orderQty,discount,total) VALUES (?,?,?,?,?,?,?)", entity.getOrderId(),entity.getItemCode(),entity.getDescription(),entity.getUnitPrice(),entity.getOrderQty(), entity.getDiscount(), entity.getTotal());
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `OrderDetail` SET  orderQty=?, discount=?,total=? WHERE itemCode=?",entity.getOrderQty(), entity.getDiscount(),entity.getTotal(),entity.getItemCode());
    }

    @Override
    public OrderDetails search(String oid) throws SQLException, ClassNotFoundException {

        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `OrderDetail` WHERE orderId LIKE ?", oid);
        if (rst.next()) {
            return new OrderDetails(rst.getString(1),rst.getString(2), rst.getString(3),rst.getBigDecimal(4), rst.getInt(5), rst.getBigDecimal(6), rst.getBigDecimal(7));
        }
        return null;
    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT orderId FROM `OrderDetail` WHERE orderId=?", oid).next();
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `OrderDetail` WHERE orderId=?", oid);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `OrderDetail` ORDER BY orderId DESC LIMIT 1;");
        return rst.next() ? String.format("O-%03d", (Integer.parseInt(rst.getString("orderId").replace("O-", "")) + 1)) : "O-001";
    }
}
