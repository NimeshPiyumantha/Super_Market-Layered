package com.superMarket.pos.dao.custom.impl;

import com.superMarket.pos.dao.custom.CustomerDAO;
import com.superMarket.pos.entity.Customer;
import com.superMarket.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> allCustomers = new ArrayList<>();
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7)));
        }
        return allCustomers;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer (cusId,cusTitle,cusName,cusAddress,city,province,postalCode) VALUES (?,?,?,?,?,?,?)", entity.getCusId(), entity.getCusTitle(), entity.getCusName(), entity.getCusAddress(), entity.getCity(), entity.getProvince(), entity.getPostalCode());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET cusTitle=?, cusName=?,cusAddress=?, city=?,province=?, postalCode=? WHERE cusId=?", entity.getCusTitle(), entity.getCusName(), entity.getCusAddress(), entity.getCity(), entity.getProvince(), entity.getPostalCode(),entity.getCusId());

    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE cusId=?", id);
        if (rst.next()) {
            return new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7));
        }
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT cusId FROM Customer WHERE cusId=?", id).next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE cusId=?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT cusId FROM Customer ORDER BY cusId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("cusId");
            int newCustomerId = Integer.parseInt(id.replace("C-", "")) + 1;
            return String.format("C-%03d", newCustomerId);
        } else {
            return "C-001";
        }
    }
}
