package com.superMarket.pos.controller;

import com.superMarket.pos.bo.BOFactory;
import com.superMarket.pos.bo.custom.QueryBO;
import com.superMarket.pos.dao.DAOFactory;
import com.superMarket.pos.dao.custom.QueryDAO;
import com.superMarket.pos.dto.CustomDTO;
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

public class MonthlyIncomeFormController implements Initializable {
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Custom);
    public AnchorPane MonthlyIncomeContext;
    public AreaChart<?, ?> MonthlyAreaChart;
    public TableView<CustomEntity> MonthlyIncomeTbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            //----------Load AreaChart-------------//
            areaChart();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //----------Load Table-------------//
        MonthlyIncomeTbl.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        MonthlyIncomeTbl.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemSoldQty"));
        MonthlyIncomeTbl.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("orderCount"));
        MonthlyIncomeTbl.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("sumOfTotal"));
        monthlyIncomeLoad();
    }

    private void monthlyIncomeLoad() {
        try {
            ArrayList<CustomDTO> arrayList = queryBO.loadMonthlyIncomeReport();
            if (arrayList != null) {
                for (CustomDTO customEntity : arrayList) {
                    MonthlyIncomeTbl.getItems().add(new CustomEntity(customEntity.getOrderDate(), customEntity.getItemSoldQty(), customEntity.getOrderCount(), customEntity.getSumOfTotal()));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void areaChart() throws SQLException, ClassNotFoundException {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Monthly Income Chart");
        ArrayList<CustomDTO> arrayList = queryBO.loadMonthlyIncomeReport();

        String date = null;
        BigDecimal total=BigDecimal.ZERO;

        for (CustomDTO customEntity : arrayList) {

            date += customEntity.getOrderDate();
            total.add(customEntity.getSumOfTotal());

            series1.getData().add(new XYChart.Data(customEntity.getOrderDate(), customEntity.getSumOfTotal()));
        }
        MonthlyAreaChart.getData().addAll(series1);
    }
}
