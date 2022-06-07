package com.superMarket.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.superMarket.pos.bo.BOFactory;
import com.superMarket.pos.bo.custom.PurchaseOrderBO;
import com.superMarket.pos.dto.CustomerDTO;
import com.superMarket.pos.dto.ItemDTO;
import com.superMarket.pos.dto.OrderDTO;
import com.superMarket.pos.dto.OrderDetailDTO;
import com.superMarket.pos.util.NotificationController;
import com.superMarket.pos.util.UILoader;
import com.superMarket.pos.view.tm.OrderDetailTM;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class PlaceOrderFormController implements Initializable {

    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);
    public AnchorPane MainAnchorPane;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public Label lblDate;
    public TableView<OrderDetailTM> tblCart;
    public Label lblTotal;
    public Label lblOrderId;
    public JFXTextField txtPackageSize;
    public JFXButton btnAddToCart;
    public TextField txtQty;
    public JFXButton btnPlaceOrder;
    public TextField txtDiscount;
    public JFXComboBox<String> cmbCustomerId;
    public JFXTextField txtCustomerName;
    public Label lblTime;
    private String orderId;

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

        orderId = generateNewOrderId();
        lblOrderId.setText(orderId);

        loadDateAndTime();//load Date and Time

        btnPlaceOrder.setDisable(true);

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer(newValue + "")) {
                            //"There is no such customer associated with the id " + id
                            NotificationController.WarringError("Search Customer Warning", newValue, "There is no such customer associated with the ");
                        }
                        /*Search Customer*/
                        CustomerDTO customerDTO = purchaseOrderBO.searchCustomer(newValue + "");
                        txtCustomerName.setText(customerDTO.getCustomerName());

                    } catch (SQLException e) {
                        NotificationController.WarringError("Search Customer Warning", newValue, "Failed to find the customer ");
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQtyOnHand.setEditable(newItemCode != null);
            btnAddToCart.setDisable(newItemCode == null);

            if (newItemCode != null) {
                try {
                    if (!existItem(newItemCode + "")) {
                        //throw new NotFoundException("There is no such item associated with the id " + code);
                    }
                    /*Find Item*/
                    ItemDTO item = purchaseOrderBO.searchItem(newItemCode + "");
                    txtDescription.setText(item.getDescription());
                    txtPackageSize.setText(item.getPackSize());
                    txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                    Optional<OrderDetailTM> optOrderDetail = tblCart.getItems().stream().filter(detail -> detail.getItemCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getOrderQty() : item.getQtyOnHand()) + "");

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQtyOnHand.clear();
                txtQty.clear();
                txtUnitPrice.clear();
                txtDiscount.clear();
            }
        });
        //-----------Table Cart Load-------//
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        tblCart.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblCart.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("total"));

        //---------------------Delete Add Item---------------//
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblCart.getColumns().get(7);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(event -> {
                tblCart.getItems().remove(param.getValue());
                NotificationController.SuccessfulTableNotification("Cart Item Delete", "Cart Item");
                tblCart.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        tblCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {
            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbCustomerId.setDisable(true);
                txtCustomerName.setDisable(true);
                txtDescription.setDisable(true);
                txtPackageSize.setDisable(true);
                txtQtyOnHand.setDisable(true);
                txtUnitPrice.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getItemCode());
                btnAddToCart.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getOrderQty() + "");
                txtDiscount.setText(selectedOrderDetail.getDiscount() + "");
                txtQty.setText(selectedOrderDetail.getOrderQty() + "");
            } else {
                btnAddToCart.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQtyOnHand.clear();
            }
        });
        loadAllCustomerIds();
        loadAllItemCodes();
        btnAddToCart.setDisable(true);
    }

    //----------------Load Time and date----------//
    private void loadDateAndTime() {
        //Set Date
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = purchaseOrderBO.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems().add(customerDTO.getCustomerId());
            }

        } catch (SQLException e) {
            NotificationController.Warring("Customer Load", "Failed to load customer ids.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemCodes() {
        try {
            /*Get all items*/
            ArrayList<ItemDTO> all = purchaseOrderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemCode.getItems().add(dto.getItemCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //------------------Calculate Total----------//
    private void calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderDetailTM detail : tblCart.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblTotal.setText(String.valueOf(total));
    }

    //------------Item Exit-------//
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.checkItemIsAvailable(code);
    }

    //--------------Customer Exit-----//
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.checkCustomerIsAvailable(id);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblCart.getItems().isEmpty()));
    }

    //----------Generate Order Id-------------//
    private String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateNewOrderID();
        } catch (SQLException e) {
            NotificationController.Warring("Generate Order Id", "Failed to generate a new order id...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "O-001";
    }

    //----------Btn Place Order----------------//
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        cmbCustomerId.setDisable(false);
        txtCustomerName.setDisable(false);
        txtDescription.setDisable(false);
        txtPackageSize.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtUnitPrice.setDisable(false);
        boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerId.getValue(),
                tblCart.getItems().stream().map(tm -> new OrderDetailDTO(orderId,tm.getItemCode(),tm.getDescription(),tm.getUnitPrice(),tm.getOrderQty(), tm.getDiscount(), tm.getTotal())).collect(Collectors.toList()));
        if (b) {
            //makePayment();
            NotificationController.SuccessfulTableNotification("Order Place", "Orders Place");
        } else {
            System.out.println(b);
            NotificationController.UnSuccessfulTableNotification("Order Place", "Orders Place");
        }

        PrintBill(); //PrintBill
        orderId = generateNewOrderId(); //Generate id
        lblOrderId.setText(orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblCart.getItems().clear();
        txtQty.clear();
        txtPackageSize.clear();
        calculateTotal();
    }

    //---------------------Print Bill------------//
    public void PrintBill() throws SQLException, ClassNotFoundException, JRException {
        try {
            ObservableList<OrderDetailTM> items = tblCart.getItems();
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/com/superMarket/pos/view/reports/Report.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, new JRBeanCollectionDataSource(items));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    //--------------btn Add Cart --------------//
    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            NotificationController.Warring("Order Qty", "Invalid Qty");
            txtQty.requestFocus();
            txtQty.selectAll();
            return;

        } else if (!txtDiscount.getText().matches("^[\\d]+$")) {
            NotificationController.Warring("Order Item Discount", "Invalid item Discount");
            txtDiscount.requestFocus();
            return;
        }

        btnPlaceOrder.setDisable(false);
        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal discount = new BigDecimal(txtDiscount.getText()).setScale(2);
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).subtract(new BigDecimal(qty).multiply(unitPrice.multiply(discount)).divide(new BigDecimal(100))).setScale(2);

        if (qty <= Integer.parseInt(txtQtyOnHand.getText())) {
            boolean exists = tblCart.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(itemCode));

            if (exists) {
                OrderDetailTM orderDetailTM = tblCart.getItems().stream().filter(detail -> detail.getItemCode().equals(itemCode)).findFirst().get();

                if (btnAddToCart.getText().equalsIgnoreCase("Update")) {
                    txtDescription.setDisable(false);
                    txtQtyOnHand.setDisable(false);
                    txtPackageSize.setDisable(false);
                    txtUnitPrice.setDisable(false);
                    orderDetailTM.setOrderQty(qty);
                    orderDetailTM.setTotal(total);
                    orderDetailTM.setDiscount(discount);
                    tblCart.getSelectionModel().clearSelection();
                } else {
                    orderDetailTM.setOrderQty(orderDetailTM.getOrderQty() + qty);
                    total = new BigDecimal(orderDetailTM.getOrderQty()).multiply(unitPrice).setScale(2);
                    orderDetailTM.setTotal(total);
                }
                tblCart.refresh();
            } else {
                tblCart.getItems().add(new OrderDetailTM(orderId,cmbCustomerId.getValue(),itemCode,txtDescription.getText(),txtPackageSize.getText(),unitPrice,qty, discount, total));
            }
            cmbItemCode.getSelectionModel().clearSelection();
            txtPackageSize.clear();
            cmbCustomerId.requestFocus();
            calculateTotal();
            enableOrDisablePlaceOrderButton();
        } else {
            NotificationController.Warring("Item Stock", "Item Stock Out ...");
            cmbItemCode.getSelectionModel().clearSelection();
            txtDescription.clear();
            txtQtyOnHand.clear();
            txtQty.clear();
            txtUnitPrice.clear();
            txtDiscount.clear();
            txtPackageSize.clear();
        }
    }

    //----------------Save order---------------//
    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {
        try {
            return purchaseOrderBO.purchaseOrder(new OrderDTO(orderId, orderDate, customerId, orderDetails));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //-------------Navigate To Home------//
    public void navigateToHome(MouseEvent mouseEvent) throws IOException, SQLException {
        UILoader.NavigateToHome(MainAnchorPane, "ManageCustomerForm");
    }
}
