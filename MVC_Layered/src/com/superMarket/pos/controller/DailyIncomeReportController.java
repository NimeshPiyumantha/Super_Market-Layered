package com.superMarket.pos.controller;

import com.superMarket.pos.dao.DAOFactory;
import com.superMarket.pos.dao.custom.QueryDAO;
import com.superMarket.pos.entity.CustomEntity;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DailyIncomeReportController implements Initializable {
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);
    public AreaChart<?, ?> DailyAreaChart;
    public TableView<CustomEntity> DailyIncomeTbl;
    public AnchorPane DailyIncomeContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //----------Load Area Chart--------------//
        try {
            areaChart();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //------------Table load---------------//
        DailyIncomeTbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        DailyIncomeTbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemSoldQty"));
        DailyIncomeTbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("orderCount"));
        DailyIncomeTbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("sumOfTotal"));
        DailyIncomeLoad();
    }

    private void DailyIncomeLoad() {
        try {
            ArrayList<CustomEntity> arrayList = queryDAO.loadDailyIncomeReport();
            if (arrayList != null) {
                for (CustomEntity customEntity : arrayList) {
                    DailyIncomeTbl.getItems().add(new CustomEntity(customEntity.getOrderDate(), customEntity.getItemSoldQty(), customEntity.getOrderCount(), customEntity.getSumOfTotal()));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void areaChart() throws SQLException, ClassNotFoundException {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Daily Income Chart");
        ArrayList<CustomEntity> arrayList = queryDAO.loadDailyIncomeReport();
        String date = null;
        BigDecimal total=BigDecimal.ZERO;

        for (CustomEntity customEntity : arrayList) {

            date += customEntity.getOrderDate();
            total.add(customEntity.getSumOfTotal());


            series1.getData().add(new XYChart.Data(customEntity.getOrderDate(), customEntity.getSumOfTotal()));
        }
        DailyAreaChart.getData().addAll(series1);
    }
}
