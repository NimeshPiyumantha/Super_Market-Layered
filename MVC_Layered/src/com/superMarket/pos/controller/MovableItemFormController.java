package com.superMarket.pos.controller;

import com.superMarket.pos.bo.BOFactory;
import com.superMarket.pos.bo.custom.QueryBO;
import com.superMarket.pos.dto.CustomDTO;
import com.superMarket.pos.view.tm.CustomEntityTM;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovableItemFormController implements Initializable {
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Custom);

    public AnchorPane ItemMovableContext;
    public TableView<CustomEntityTM> tblMostMovableItem;
    public TableView<CustomEntityTM> tblLeastMovableItem;
    public Label lblItem;
    public Label lblCustomer;
    public Label lblOrders;


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            lblItem.setText(String.valueOf(queryBO.getItem()));//load Item Count
            lblCustomer.setText(String.valueOf(queryBO.getCustomer()));//load Customer Count
            lblOrders.setText(String.valueOf(queryBO.getSumSales()));//load Orders Count
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadAllMostMovableAndLestMovableItems(tblMostMovableItem);
        loadAllMostMovableAndLestMovableItems(tblLeastMovableItem);

        loadAllMostMovableItems();
        loadAllLestMovableItems();
    }

    private void loadAllMostMovableAndLestMovableItems(TableView<CustomEntityTM> tblMostMovableReport) {
        tblMostMovableReport.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblMostMovableReport.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblMostMovableReport.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblMostMovableReport.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblMostMovableReport.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblMostMovableReport.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        tblMostMovableReport.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("sumOfTotal"));
    }

    private void loadAllLestMovableItems() {
        tblLeastMovableItem.getItems().clear();
        try {
            /*Get all Lest Movable Items*/
            ArrayList<CustomDTO> allDetails = queryBO.LeastSoldItem();

            for (CustomDTO items : allDetails) {
                tblLeastMovableItem.getItems().add(new CustomEntityTM(items.getItemCode(), items.getDescription(), items.getPackSize(), items.getQtyOnHand(), items.getUnitPrice(), items.getOrderQty(), items.getSumOfTotal()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllMostMovableItems() {
        tblMostMovableItem.getItems().clear();
        try {
            /*Get all Lest Movable Items*/
            ArrayList<CustomDTO> allDetails = queryBO.MostSoldItem();

            for (CustomDTO items : allDetails) {
                tblMostMovableItem.getItems().add(new CustomEntityTM(items.getItemCode(), items.getDescription(), items.getPackSize(), items.getQtyOnHand(), items.getUnitPrice(), items.getOrderQty(), items.getSumOfTotal()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
