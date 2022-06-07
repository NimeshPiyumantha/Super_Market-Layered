package com.superMarket.pos.controller;

import com.superMarket.pos.util.UILoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IncomeReportController implements Initializable {
    public AnchorPane MainContext;
    public AnchorPane IncomeContext;

    public void DailyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(IncomeContext, "DailyIncomeForm");

    }

    public void MonthlyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(IncomeContext, "MonthlyIncomeForm");
    }

    public void AnnuallyReportOnAction(ActionEvent actionEvent) throws IOException {
        UILoader.loadUiDashBoard(IncomeContext, "AnnuallyIncomeForm");
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
        URL resource = getClass().getResource("../view/DailyIncomeForm.fxml");
        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IncomeContext.getChildren().clear();
        IncomeContext.getChildren().add(load);
    }
}
