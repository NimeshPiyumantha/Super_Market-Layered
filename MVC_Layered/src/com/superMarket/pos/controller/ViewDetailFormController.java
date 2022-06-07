package com.superMarket.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.superMarket.pos.util.UILoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewDetailFormController implements Initializable {
    public AnchorPane MainAnchorPaneContext;
    public JFXButton btnIncomeReport;
    public JFXButton btnMovableItem;
    public JFXButton btnGoHome;
    public AnchorPane SubAnchorPaneContext;


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

    //------------Income Reports Anchorman Load----------//
    public void btnIncomeReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(SubAnchorPaneContext, "IncomeReport");
    }

    //------------Movable Item Anchorman Load----------//
    public void btnMovableItemOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(SubAnchorPaneContext, "MovableItemForm");
    }

    //------------Go Main Anchorman ----------//
    public void btnGoHomeOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        UILoader.NavigateToHome(MainAnchorPaneContext, "AdminDashBoardForm");
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
        URL resource = getClass().getResource("../view/IncomeReport.fxml");
        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SubAnchorPaneContext.getChildren().clear();
        SubAnchorPaneContext.getChildren().add(load);
    }
}
