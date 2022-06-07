package com.superMarket.pos.dao.custom;

import com.superMarket.pos.dao.SuperDAO;
import com.superMarket.pos.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    int getSumSales() throws SQLException, ClassNotFoundException;

    int getItem() throws SQLException, ClassNotFoundException;

    int getCustomer() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> LeastSoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> MostSoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> loadDailyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> loadAnnuallyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> searchOrderByOrderID(String id) throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> LoadAll() throws SQLException, ClassNotFoundException;
}
