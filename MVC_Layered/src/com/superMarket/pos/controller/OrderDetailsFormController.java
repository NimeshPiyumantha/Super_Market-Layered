package com.superMarket.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.superMarket.pos.bo.BOFactory;
import com.superMarket.pos.bo.custom.OrderBO;
import com.superMarket.pos.bo.custom.OrderDetailBO;
import com.superMarket.pos.bo.custom.QueryBO;
import com.superMarket.pos.dto.CustomDTO;
import com.superMarket.pos.dto.OrderDetailDTO;
import com.superMarket.pos.util.NotificationController;
import com.superMarket.pos.util.UILoader;
import com.superMarket.pos.view.tm.CustomEntityTM;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderDetailsFormController implements Initializable {
    private final OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERDETAILS);
    private final OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    private final QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Custom);

    public AnchorPane OrderDetailContext;
    public TableView<CustomEntityTM> tblOrderDetails;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnCancel;
    public TextField txtSearchId;
    public JFXTextField txtOrderDate;
    public JFXTextField txtOrderId;
    public JFXTextField txtCustomerId;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtOrderQty;
    public JFXTextField TxtDiscount;
    public JFXTextField txtTotal;


    //----------------Navigate To Home----------------//
    public void navigateToHome(MouseEvent mouseEvent) throws IOException, SQLException {
        UILoader.NavigateToHome(OrderDetailContext, "CashierDashBoardForm");
    }

    public void BtnMinimizeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void BtnCloseOnAction(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void BtnRestoreDownOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint("");
        stage.setResizable(true);
    }

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
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        //    disableTextFields();
        //------------------Load Order Details Table-------------------//
        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        tblOrderDetails.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblOrderDetails.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("sumOfTotal"));

        try {
            loadAll();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnUpdate.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtOrderId.setText(newValue.getOrderId());
                txtCustomerId.setText(newValue.getCusId());
                txtItemCode.setText(newValue.getItemCode());
                txtOrderDate.setText(newValue.getOrderDate());
                txtDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(newValue.getUnitPrice() + "");
                txtOrderQty.setText(newValue.getOrderQty() + "");
                TxtDiscount.setText(newValue.getDiscount() + "");
                txtTotal.setText(newValue.getSumOfTotal() + "");

                disableTextFields();
                txtOrderQty.setDisable(false);
                TxtDiscount.setDisable(false);
            }
        });
    }

    private void loadAll() throws SQLException, ClassNotFoundException {
        ArrayList<CustomDTO> arrayList = queryBO.getAllDetails();
        if (arrayList != null) {
            for (CustomDTO customDTO : arrayList) {
                tblOrderDetails.getItems().add(new CustomEntityTM(customDTO.getOrderDate(), customDTO.getOrderId(), customDTO.getCustomerId(), customDTO.getItemCode(), customDTO.getDescription(), customDTO.getUnitPrice(), customDTO.getOrderQty(), customDTO.getDiscount(), customDTO.getSumOfTotal()));
            }
        }
    }

    //------------------------Disable Text Filed----------------------//
    private void disableTextFields() {
        txtOrderDate.setDisable(true);
        txtOrderId.setDisable(true);
        txtCustomerId.setDisable(true);
        txtItemCode.setDisable(true);
        txtDescription.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtOrderQty.setDisable(true);
        TxtDiscount.setDisable(true);
        txtTotal.setDisable(true);
    }


    //------------------------Text Update Order Details----------------------//
    public void btnUpdate_OnAction(ActionEvent actionEvent) {
        String orderId = txtOrderId.getText();
        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        String cusId = txtCustomerId.getText();
        String date = txtOrderDate.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtOrderQty.getText());
        BigDecimal discount = new BigDecimal(TxtDiscount.getText()).setScale(2);
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).subtract(new BigDecimal(qty).multiply(unitPrice.multiply(discount)).divide(new BigDecimal(100))).setScale(2);

        if (btnUpdate.getText().equalsIgnoreCase("update")) {
            try {
                //Order update
                orderDetailBO.updateOrderDetail(new OrderDetailDTO(qty, discount, total, itemCode));
                CustomEntityTM selectOrder = tblOrderDetails.getSelectionModel().getSelectedItem();
                NotificationController.SuccessfulTableNotification("Update", "Order Details");
                selectOrder.setOrderId(orderId);
                selectOrder.setCusId(cusId);
                selectOrder.setItemCode(itemCode);
                selectOrder.setOrderDate(date);
                selectOrder.setDescription(description);
                selectOrder.setUnitPrice(unitPrice);
                selectOrder.setOrderQty(qty);
                selectOrder.setDiscount(discount);
                selectOrder.setSumOfTotal(total);
                tblOrderDetails.refresh();

            } catch (SQLException e) {
                NotificationController.WarringError("Update Order Detail Warning", itemCode + e.getMessage(), " Failed to update the Order Detail ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //------------------------Order Delete----------------------//
    public void btnDelete_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        /*Delete Order Details*/
        String id = tblOrderDetails.getSelectionModel().getSelectedItem().getOrderId();
        try {
            if (!OrderExit(id)) {
                btnDelete.setDisable(false);
                NotificationController.WarringError("Delete Order Warning", id, "There is no such Order associated with the ");
            }
            orderBO.deleteOrder(id);
            tblOrderDetails.getItems().remove(tblOrderDetails.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Order");
            disableAll();
        } catch (SQLException e) {
            NotificationController.WarringError("Delete Order Warning", id, " Failed to delete the Order.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //------------------------Order Exit----------------------//
    private boolean OrderExit(String id) throws SQLException, ClassNotFoundException {
        return orderDetailBO.orderDetailExit(id);
    }

    //------------------------Cancel Text----------------------//
    public void btnCancelTbl(ActionEvent actionEvent) {
        disableAll();
    }

    //------------------------Disable Text Filed & Table----------------------//
    private void disableAll() {
        txtOrderDate.clear();
        txtOrderId.clear();
        txtCustomerId.clear();
        txtItemCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtOrderQty.clear();
        TxtDiscount.clear();
        txtTotal.clear();


        disableTextFields();
        tblOrderDetails.getItems().clear();
    }

    //------------------------Search Details----------------------//
    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (txtSearchId.getText().trim().isEmpty()) {
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAll();
        } else {
            if (OrderExit(txtSearchId.getText())) {
                tblOrderDetails.getItems().clear();
                ArrayList<CustomDTO> arrayList = queryBO.searchOrderByOrderID(txtSearchId.getText());
                if (arrayList != null) {
                    for (CustomDTO customDTO : arrayList) {
                        tblOrderDetails.getItems().add(new CustomEntityTM(customDTO.getOrderDate(), customDTO.getOrderId(), customDTO.getCustomerId(), customDTO.getItemCode(), customDTO.getDescription(), customDTO.getUnitPrice(), customDTO.getOrderQty(), customDTO.getDiscount(), customDTO.getSumOfTotal()));
                    }
                }
            } else {
                tblOrderDetails.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }
}


