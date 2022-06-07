package com.superMarket.pos.dao.custom.impl;

import com.superMarket.pos.dao.custom.ItemDAO;
import com.superMarket.pos.entity.Item;
import com.superMarket.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> allItems = new ArrayList<>();
        while (rst.next()) {
            allItems.add(new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getBigDecimal(4), rst.getInt(5)));
        }
        return allItems;
    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item (itemCode, description, packSize, unitPrice,qtyOnHand) VALUES (?,?,?,?,?)", entity.getItemCode(), entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(), entity.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, unitPrice=? ,qtyOnHand=? WHERE itemCode=?", entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getItemCode());

    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", code);
        if (rst.next()) {
            return new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getBigDecimal(4), rst.getInt(5));
        }
        return null;
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT itemCode FROM Item WHERE itemCode=?", code).next();
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?", code);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("itemCode");
            int newItemId = Integer.parseInt(id.replace("I-", "")) + 1;
            return String.format("I-%03d", newItemId);
        } else {
            return "I-001";
        }
    }
}
