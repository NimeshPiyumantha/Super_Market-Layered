package com.superMarket.pos.dao.custom.impl;

import com.superMarket.pos.dao.custom.QueryDAO;
import com.superMarket.pos.entity.CustomEntity;
import com.superMarket.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public int getItem() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT COUNT(itemCode) FROM Item");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public int getCustomer() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT COUNT(cusId) FROM Customer");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public int getSumSales() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT count(orderId) FROM `Orders`");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public ArrayList<CustomEntity> LeastSoldItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderDetail.ItemCode, Item.description, Item.packSize, Item.qtyOnHand, Item.unitPrice, sum(OrderQty),sum(total)FROM Item INNER JOIN OrderDetail on Item.ItemCode = OrderDetail.ItemCode GROUP BY ItemCode ORDER BY sum(OrderQty) ASC limit 1");
        ArrayList<CustomEntity> leastSoldItem = new ArrayList();
        while (rst.next()) {
            leastSoldItem.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getBigDecimal(5), rst.getInt(6), rst.getBigDecimal(7)));
        }
        return leastSoldItem;
    }

    @Override
    public ArrayList<CustomEntity> MostSoldItem() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderDetail.ItemCode, Item.description, Item.packSize, Item.qtyOnHand, Item.unitPrice, sum(OrderQty),sum(total)FROM Item INNER JOIN OrderDetail on Item.ItemCode = OrderDetail.ItemCode GROUP BY ItemCode ORDER BY sum(OrderQty) DESC limit 1");
        ArrayList<CustomEntity> mostSoldItem = new ArrayList();
        while (rst.next()) {
            mostSoldItem.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getBigDecimal(5), rst.getInt(6), rst.getBigDecimal(7)));
        }
        return mostSoldItem;
    }

    @Override
    public ArrayList<CustomEntity> loadDailyIncomeReport() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT `Orders`.orderDate,sum(`OrderDetail`.OrderQty),count(`Orders`.OrderID),sum(`OrderDetail`.Total) FROM `Orders` INNER JOIN `OrderDetail` ON `Orders`.OrderID = `OrderDetail`.OrderID GROUP BY OrderDate");
        ArrayList<CustomEntity> dailyIncomeReport = new ArrayList();
        while (rst.next()) {
            dailyIncomeReport.add(new CustomEntity(rst.getString(1), rst.getInt(2), rst.getInt(3), rst.getBigDecimal(4)));
        }
        return dailyIncomeReport;
    }

    @Override
    public ArrayList<CustomEntity> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT (MONTHNAME(OrderDate)) ,sum(`OrderDetail`.OrderQty),count(`Orders`.OrderID),sum(`OrderDetail`.Total)  FROM `Orders` INNER JOIN `OrderDetail` ON `Orders`.OrderID = `OrderDetail`.OrderID GROUP BY extract(MONTH FROM(OrderDate))");
        ArrayList<CustomEntity> monthlyIncomeReport = new ArrayList();
        while (rst.next()) {
            monthlyIncomeReport.add(new CustomEntity(rst.getString(1), rst.getInt(2), rst.getInt(3), rst.getBigDecimal(4)));
        }
        return monthlyIncomeReport;
    }

    @Override
    public ArrayList<CustomEntity> loadAnnuallyIncomeReport() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT (YEAR(OrderDate)) ,sum(`OrderDetail`.OrderQty),count(`Orders`.OrderID),sum(`OrderDetail`.Total)  FROM `Orders` INNER JOIN `OrderDetail` ON `Orders`.OrderID = `OrderDetail`.OrderID GROUP BY extract(YEAR FROM(OrderDate))");
        ArrayList<CustomEntity> annuallyIncomeReport = new ArrayList();
        while (rst.next()) {
            annuallyIncomeReport.add(new CustomEntity(rst.getString(1), rst.getInt(2), rst.getInt(3), rst.getBigDecimal(4)));
        }
        return annuallyIncomeReport;
    }

    @Override
    public ArrayList<CustomEntity> searchOrderByOrderID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT Orders.orderDate,Orders.orderId,Orders.cusId,OrderDetail.itemCode,OrderDetail.description,OrderDetail.unitPrice,OrderDetail.orderQty,OrderDetail.discount,OrderDetail.total FROM Orders INNER JOIN OrderDetail on Orders.orderId = OrderDetail.orderId where Orders.orderId=?", id);
        ArrayList<CustomEntity> orderRecords = new ArrayList();
        while (rst.next()) {
            orderRecords.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getBigDecimal(6), rst.getInt(7), rst.getBigDecimal(8), rst.getBigDecimal(9)));
        }
        return orderRecords;
    }

    @Override
    public ArrayList<CustomEntity> LoadAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT Orders.orderDate,Orders.orderId,Orders.cusId,OrderDetail.itemCode,OrderDetail.description,OrderDetail.unitPrice,OrderDetail.orderQty,OrderDetail.discount,OrderDetail.total FROM Orders INNER JOIN OrderDetail on Orders.orderId = OrderDetail.orderId");
        ArrayList<CustomEntity> orderRecords = new ArrayList();
        while (rst.next()) {
            orderRecords.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getBigDecimal(6), rst.getInt(7), rst.getBigDecimal(8), rst.getBigDecimal(9)));
        }
        return orderRecords;
    }
}
