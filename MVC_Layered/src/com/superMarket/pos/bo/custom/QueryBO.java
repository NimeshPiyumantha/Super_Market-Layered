package com.superMarket.pos.bo.custom;

import com.superMarket.pos.bo.SuperBO;
import com.superMarket.pos.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public interface QueryBO extends SuperBO {
    int getSumSales() throws SQLException, ClassNotFoundException;

    int getItem() throws SQLException, ClassNotFoundException;

    int getCustomer() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> LeastSoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> MostSoldItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> loadDailyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> loadAnnuallyIncomeReport() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> searchOrderByOrderID(String id) throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> getAllDetails() throws SQLException, ClassNotFoundException;
}
