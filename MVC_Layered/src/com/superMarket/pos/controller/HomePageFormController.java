package com.superMarket.pos.controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePageFormController {
    public AnchorPane context;
    public JFXProgressBar progressBar;
    public ProgressIndicator progressRange;

    public void initialize() {
        new ShowSplashScreen().start();
    }

    class ShowSplashScreen extends Thread {
        public void run() {
            try {
                for (int i = 0; i <= 10; i++) {
                    double x = i * (0.1);
                    progressBar.setProgress(x);
                    progressRange.setProgress(x);

                    if (i * 10 == 100) {
                        progressRange.setVisible(true);
                        progressRange.setProgress(1);
                    }

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/superMarket/pos/view/LoginForm.fxml")));
                    } catch (IOException ex) {
                        Logger.getLogger(HomePageFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    assert root != null;
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    context.getScene().getWindow().hide();
                });
            } catch (Exception ex) {
                Logger.getLogger(HomePageFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
