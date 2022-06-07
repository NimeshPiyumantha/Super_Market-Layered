package com.superMarket.pos.controller;

import com.jfoenix.controls.JFXRadioButton;
import com.superMarket.pos.bo.BOFactory;
import com.superMarket.pos.bo.custom.UserBO;
import com.superMarket.pos.dto.UserDetailDTO;
import com.superMarket.pos.util.NotificationController;
import com.superMarket.pos.util.UILoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginFormController {

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.User);
    public TextField txtUserName;
    public PasswordField txtPassword;
    public AnchorPane context;
    public JFXRadioButton adminRadBtn;
    public JFXRadioButton recRadBtn;

    public void CashierLoginPage(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        //--------------------------------UI Login Cashier form in Database-------------------------------//
        final boolean[] goToIn = {false};

        ArrayList<UserDetailDTO> receptionDetails = userBO.getAllUsers(recRadBtn.getText());
        receptionDetails.forEach(e -> {
            if (e.getUserName().equals(txtUserName.getText()) && e.getPassword().equals(txtPassword.getText()) && recRadBtn.isSelected()) {
                System.out.println(e.getAccountType());
                try {
                    UILoader.LoginOnAction(context, "CashierDashBoardForm");
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
                NotificationController.LoginSuccessfulNotification("Cashier");
                goToIn[0] = true;
            }
        });

        ArrayList<UserDetailDTO> adminDetails = userBO.getAllUsers(adminRadBtn.getText());
        adminDetails.forEach(e -> {
            if (e.getUserName().equals(txtUserName.getText()) && e.getPassword().equals(txtPassword.getText()) && adminRadBtn.isSelected()) {
                System.out.println(e.getAccountType());
                try {
                    UILoader.LoginOnAction(context, "AdminDashBoardForm");
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
                NotificationController.LoginSuccessfulNotification("Admin");
                goToIn[0] = true;
            }
        });

        if (!goToIn[0]) {
            NotificationController.LoginUnSuccessfulNotification("User");
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

    public void goToCancelPage(ActionEvent actionEvent) {
        txtUserName.setText("");
        txtPassword.setText("");
    }
}
