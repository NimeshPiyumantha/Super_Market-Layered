package com.superMarket.pos.bo.custom.impl;

import com.superMarket.pos.bo.custom.QueryBO;
import com.superMarket.pos.dao.DAOFactory;
import com.superMarket.pos.dao.custom.QueryDAO;
import com.superMarket.pos.dto.CustomDTO;
import com.superMarket.pos.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class QueryBOImpl implements QueryBO {
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public int getSumSales() throws SQLException, ClassNotFoundException {
        return queryDAO.getSumSales();
    }

    @Override
    public int getItem() throws SQLException, ClassNotFoundException {
        return queryDAO.getItem();
    }

    @Override
    public int getCustomer() throws SQLException, ClassNotFoundException {
        return queryDAO.getCustomer();
    }

    @Override
    public ArrayList<CustomDTO> LeastSoldItem() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.LeastSoldItem();
        ArrayList<CustomDTO> customEntityArrayList = new ArrayList<>();
        for (CustomEntity customEntity : all) {
            customEntityArrayList.add(new CustomDTO(customEntity.getItemCode(), customEntity.getDescription(), customEntity.getPackSize(), customEntity.getQtyOnHand(), customEntity.getUnitPrice(), customEntity.getOrderQty(), customEntity.getSumOfTotal()));
        }
        return customEntityArrayList;
    }

    @Override
    public ArrayList<CustomDTO> MostSoldItem() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.MostSoldItem();
        ArrayList<CustomDTO> customEntityMost = new ArrayList<>();
        for (CustomEntity customEntity : all) {
            customEntityMost.add(new CustomDTO(customEntity.getItemCode(), customEntity.getDescription(), customEntity.getPackSize(), customEntity.getQtyOnHand(), customEntity.getUnitPrice(), customEntity.getOrderQty(), customEntity.getSumOfTotal()));
        }
        return customEntityMost;
    }

    @Override
    public ArrayList<CustomDTO> loadDailyIncomeReport() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.loadDailyIncomeReport();
        ArrayList<CustomDTO> customEntityDailyIncome = new ArrayList<>();
        for (CustomEntity customEntityDaily : all) {
            customEntityDailyIncome.add(new CustomDTO(customEntityDaily.getOrderDate(), customEntityDaily.getItemSoldQty(), customEntityDaily.getOrderCount(), customEntityDaily.getSumOfTotal()));
        }
        return customEntityDailyIncome;
    }

    @Override
    public ArrayList<CustomDTO> loadMonthlyIncomeReport() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.loadMonthlyIncomeReport();
        ArrayList<CustomDTO> customEntityDailyIncome = new ArrayList<>();
        for (CustomEntity customEntityDaily : all) {
            customEntityDailyIncome.add(new CustomDTO(customEntityDaily.getOrderDate(), customEntityDaily.getItemSoldQty(), customEntityDaily.getOrderCount(), customEntityDaily.getSumOfTotal()));
        }
        return customEntityDailyIncome;
    }

    @Override
    public ArrayList<CustomDTO> loadAnnuallyIncomeReport() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.loadAnnuallyIncomeReport();
        ArrayList<CustomDTO> customEntityDailyIncome = new ArrayList<>();
        for (CustomEntity customEntityDaily : all) {
            customEntityDailyIncome.add(new CustomDTO(customEntityDaily.getOrderDate(), customEntityDaily.getItemSoldQty(), customEntityDaily.getOrderCount(), customEntityDaily.getSumOfTotal()));
        }
        return customEntityDailyIncome;
    }

    @Override
    public ArrayList<CustomDTO> searchOrderByOrderID(String id) throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.searchOrderByOrderID(id);
        ArrayList<CustomDTO> customEntitySearch = new ArrayList<>();
        for (CustomEntity customEntity : all) {
            customEntitySearch.add(new CustomDTO(customEntity.getOrderDate(), customEntity.getOrderId(), customEntity.getCusId(), customEntity.getItemCode(), customEntity.getDescription(), customEntity.getUnitPrice(), customEntity.getOrderQty(), customEntity.getDiscount(), customEntity.getSumOfTotal()));
        }
        return customEntitySearch;
    }

    @Override
    public ArrayList<CustomDTO> getAllDetails() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.LoadAll();
        ArrayList<CustomDTO> customEntitySearch = new ArrayList<>();
        for (CustomEntity customEntity : all) {
            customEntitySearch.add(new CustomDTO(customEntity.getOrderDate(), customEntity.getOrderId(), customEntity.getCusId(), customEntity.getItemCode(), customEntity.getDescription(), customEntity.getUnitPrice(), customEntity.getOrderQty(), customEntity.getDiscount(), customEntity.getSumOfTotal()));
        }
        return customEntitySearch;
    }
}
