package com.superMarket.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.superMarket.pos.bo.BOFactory;
import com.superMarket.pos.bo.custom.CustomerBO;
import com.superMarket.pos.dto.CustomerDTO;
import com.superMarket.pos.util.NotificationController;
import com.superMarket.pos.util.UILoader;
import com.superMarket.pos.view.tm.CustomerTM;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ManageCustomerFormController implements Initializable {

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    public AnchorPane CustomerFormContext;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerTitle;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerProvince;
    public JFXTextField txtCustomerCity;
    public JFXTextField txtCustomerPostalCode;
    public JFXButton btnSave;
    public JFXButton btnAddNew;
    public TableView<CustomerTM> tblCustomers;
    public JFXButton btnDelete;
    public JFXButton btnPayment;
    public AnchorPane CustomerAnchorPane;

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
        //------------------Table Load-------------------------------//
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblCustomers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        tblCustomers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        tblCustomers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("customerProvince"));
        tblCustomers.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));


        initUI();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtCustomerId.setText(newValue.getCustomerId());
                txtCustomerTitle.setText(newValue.getCustomerTitle());
                txtCustomerName.setText(newValue.getCustomerName());
                txtCustomerAddress.setText(newValue.getCustomerAddress());
                txtCustomerCity.setText(newValue.getCustomerCity());
                txtCustomerProvince.setText(newValue.getCustomerProvince());
                txtCustomerPostalCode.setText(newValue.getCustomerPostalCode());

                txtCustomerId.setDisable(false);
                txtCustomerTitle.setDisable(false);
                txtCustomerName.setDisable(false);
                txtCustomerAddress.setDisable(false);
                txtCustomerCity.setDisable(false);
                txtCustomerProvince.setDisable(false);
                txtCustomerPostalCode.setDisable(false);
            }
        });

        txtCustomerPostalCode.setOnAction(event -> btnSave.fire());
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        tblCustomers.getItems().clear();
        /*Get all customers*/
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            for (CustomerDTO customer : allCustomers) {
                tblCustomers.getItems().add(new CustomerTM(customer.getCustomerId(), customer.getCustomerTitle(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerCity(), customer.getCustomerProvince(), customer.getCustomerPostalCode()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtCustomerId.clear();
        txtCustomerTitle.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerCity.clear();
        txtCustomerProvince.clear();
        txtCustomerPostalCode.clear();

        txtCustomerId.setDisable(true);
        txtCustomerTitle.setDisable(true);
        txtCustomerName.setDisable(true);
        txtCustomerAddress.setDisable(true);
        txtCustomerCity.setDisable(true);
        txtCustomerProvince.setDisable(true);
        txtCustomerPostalCode.setDisable(true);

        txtCustomerId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    //------------------Navigate Home----------------//
    public void navigateToHome(MouseEvent mouseEvent) throws IOException, SQLException {
        UILoader.NavigateToHome(CustomerFormContext, "CashierDashBoardForm");
    }

    //----------------------Btn Save--------------------------//
    public void btnSave_OnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();
        String title = txtCustomerTitle.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String city = txtCustomerCity.getText();
        String province = txtCustomerProvince.getText();
        String postalCode = txtCustomerPostalCode.getText();

        if (!title.matches("^[Mr|Mrs]{2,3}$")) {
            NotificationController.Warring("Customer Title", "Invalid Title");
            txtCustomerTitle.requestFocus();
            return;
        } else if (!name.matches("^[A-z ]{3,20}$")) {
            NotificationController.Warring("Customer Name", "Invalid Name");
            txtCustomerName.requestFocus();
            return;
        } else if (!address.matches("^[A-z0-9/ ]{4,30}$")) {
            NotificationController.Warring("Customer Address", "Address should be at least 3 characters long");
            txtCustomerAddress.requestFocus();
            return;
        } else if (!city.matches("^[A-z ]{3,20}$")) {
            NotificationController.Warring("Customer City", "Invalid City");
            txtCustomerCity.requestFocus();
            return;
        } else if (!province.matches("^[A-z]{3,}$")) {
            NotificationController.Warring("Customer Province", "Invalid Province");
            txtCustomerProvince.requestFocus();
            return;
        } else if (!postalCode.matches("^[0-9]{3,10}$")) {
            NotificationController.Warring("Customer Postal Code", "Invalid Postal Code");
            txtCustomerPostalCode.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existCustomer(id)) {
                    NotificationController.WarringError("Save Customer Warning", id, "Already exists ");
                }
                customerBO.saveCustomer(new CustomerDTO(id, title, name, address, city, province, postalCode));
                tblCustomers.getItems().add(new CustomerTM(id, title, name, address, city, province, postalCode));
                NotificationController.SuccessfulTableNotification("Save", "Customer");
            } catch (SQLException e) {
                NotificationController.WarringError("Save Customer Warning", id + e.getMessage(), "Failed to save the customer ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update customer*/
            try {
                if (!existCustomer(id)) {
                    NotificationController.WarringError("Update Customer Warning", id, "There is no such customer associated with the ");
                }
                //Customer update
                customerBO.updateCustomer(new CustomerDTO(id, title, name, address, city, province, postalCode));
                NotificationController.SuccessfulTableNotification("Update", "Customer");
            } catch (SQLException e) {
                NotificationController.WarringError("Update Customer Warning", id + e.getMessage(), "Failed to update the customer ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTM selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();
            selectedCustomer.setCustomerTitle(title);
            selectedCustomer.setCustomerName(name);
            selectedCustomer.setCustomerAddress(address);
            selectedCustomer.setCustomerCity(city);
            selectedCustomer.setCustomerProvince(province);
            selectedCustomer.setCustomerPostalCode(postalCode);
            tblCustomers.refresh();
        }
        btnAddNew.fire();
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.customerExist(id);
    }

    //----------Add New Customer-------------//
    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCustomerId.setDisable(false);
        txtCustomerTitle.setDisable(false);
        txtCustomerName.setDisable(false);
        txtCustomerAddress.setDisable(false);
        txtCustomerCity.setDisable(false);
        txtCustomerProvince.setDisable(false);
        txtCustomerPostalCode.setDisable(false);
        txtCustomerId.clear();
        txtCustomerTitle.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerCity.clear();
        txtCustomerProvince.clear();
        txtCustomerPostalCode.clear();
        txtCustomerId.setText(generateNewId());
        txtCustomerTitle.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomers.getSelectionModel().clearSelection();
    }

    //-------------Generate Ne Id--------------//
    private String generateNewId() {
        try {
            return customerBO.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (tblCustomers.getItems().isEmpty()) {
            return "C-001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C-%03d", newCustomerId);
        }

    }

    //---------------Get Last Id----------//
    private String getLastCustomerId() {
        List<CustomerTM> tempCustomersList = new ArrayList<>(tblCustomers.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getCustomerId();

    }

    //---------------btn Delete Customer------------------//
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        /*Delete Customer*/
        String id = tblCustomers.getSelectionModel().getSelectedItem().getCustomerId();
        try {
            if (!existCustomer(id)) {
                NotificationController.WarringError("Delete Customer Warning", id, "There is no such customer associated with the ");
            }
            customerBO.deleteCustomer(id);
            tblCustomers.getItems().remove(tblCustomers.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Customer");
            tblCustomers.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            NotificationController.WarringError("Delete Customer Warning", id, "Failed to delete the customer ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    //-----------btn Payment Form load---------------//
    public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(CustomerAnchorPane, "PlaceOrderForm");

    }

}
