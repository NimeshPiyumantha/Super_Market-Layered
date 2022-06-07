package com.superMarket.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.superMarket.pos.bo.BOFactory;
import com.superMarket.pos.bo.custom.ItemBO;
import com.superMarket.pos.dto.ItemDTO;
import com.superMarket.pos.util.NotificationController;
import com.superMarket.pos.util.UILoader;
import com.superMarket.pos.view.tm.ItemTM;
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
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageItemFormController implements Initializable {

    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    public AnchorPane ItemContext;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public TableView<ItemTM> tblItem;
    public JFXButton btnSave;
    public JFXButton btnAddNew;
    public JFXButton btnDelete;

    //----------------Navigate To Home-----------------------//
    public void navigateToHome(MouseEvent mouseEvent) throws IOException, SQLException {
        UILoader.NavigateToHome(ItemContext, "AdminDashBoardForm");
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtItemCode.setDisable(false);
        txtDescription.setDisable(false);
        txtPackSize.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtItemCode.clear();
        txtItemCode.setText(generateNewId());
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtDescription.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblItem.getSelectionModel().clearSelection();
    }

    //--------------------Btn Save ----------------------//
    public void btnSave_OnAction(ActionEvent actionEvent) {
        String code = txtItemCode.getText();
        String description = txtDescription.getText();
        String pakSize = txtPackSize.getText();

        if (!description.matches("^[A-Za-z0-9 ]+$")) {
            NotificationController.Warring("Item Description", "Invalid description");
            txtDescription.requestFocus();
            return;
        } else if (!pakSize.matches("^[1-9][0-9]*( )(|g|kg|ml|l)$")) {
            NotificationController.Warring("Item Pack Size", "Invalid Pack Size");
            txtPackSize.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]{1,5}[.][0-9]{1,3}$")) {
            NotificationController.Warring("Item UnitPrice", "Invalid unit price");
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            NotificationController.Warring("Item QtyOnHand", "Invalid qty on hand");
            txtQtyOnHand.requestFocus();
            return;
        }

        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText());


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existItem(code)) {
                    NotificationController.WarringError("Item Save Warning", code, " Already exists");
                }
                //Save Item
                itemBO.saveItem(new ItemDTO(code, description, pakSize, unitPrice, qtyOnHand));
                tblItem.getItems().add(new ItemTM(code, description, pakSize, unitPrice, qtyOnHand));
                NotificationController.SuccessfulTableNotification("Save", "Item");
            } catch (SQLException e) {
                NotificationController.WarringError("Save Item Warning", code + e.getMessage(), "Failed to save the Item ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (!existItem(code)) {
                    NotificationController.WarringError("Update Customer Warning", code, "There is no such item associated with the ");
                }
                /*Update Item*/
                itemBO.updateItem(new ItemDTO(code, description, pakSize, unitPrice, qtyOnHand));
                ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
                NotificationController.SuccessfulTableNotification("Update", "Item");
                selectedItem.setDescription(description);
                selectedItem.setPackSize(pakSize);
                selectedItem.setUnitPrice(unitPrice);
                selectedItem.setQtyOnHand(qtyOnHand);
                tblItem.refresh();
            } catch (SQLException e) {
                NotificationController.WarringError("Update Item Warning", code + e.getMessage(), "Failed to update the Item ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        btnAddNew.fire();
    }

    //--------------btn Delete --------------------//
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        /*Delete Item*/
        String code = tblItem.getSelectionModel().getSelectedItem().getItemCode();
        try {
            if (!existItem(code)) {
                NotificationController.WarringError("Delete Item Warning", code, "There is no such Item associated with the ");
            }
            itemBO.deleteItem(code);
            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Item");
            tblItem.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            NotificationController.WarringError("Delete Item Warning", code, "Failed to delete the Item ");
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

        //-------------Table Load-------------------//
        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItem.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));


        initUI();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtItemCode.setText(newValue.getItemCode());
                txtDescription.setText(newValue.getDescription());
                txtPackSize.setText(newValue.getPackSize());
                txtUnitPrice.setText(newValue.getUnitPrice() + "");
                txtQtyOnHand.setText(newValue.getQtyOnHand() + "");

                txtItemCode.setDisable(false);
                txtDescription.setDisable(false);
                txtPackSize.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
            }
        });
        txtQtyOnHand.setOnAction(event -> btnSave.fire());
        loadAllItems();
    }


    private void loadAllItems() {
        tblItem.getItems().clear();
        try {
            /*Get all items*/
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                tblItem.getItems().add(new ItemTM(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtItemCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtItemCode.setDisable(true);
        txtDescription.setDisable(true);
        txtPackSize.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtItemCode.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    //-------------Exit Item----------------------//
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemBO.itemExist(code);
    }

    //---------------Generate New Id-------------------//
    private String generateNewId() {
        try {
            return itemBO.generateNewItemCode();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I-001";
    }
}
