package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class OtpFormController {

    @FXML
    private TextField fild;

    @FXML
    void submitBtnOnActhion(ActionEvent event) throws IOException {
        String otp = String.valueOf(LoginFormController.otp);
        if (fild.getText().equals(otp)){
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/mainform.fxml"));
            Scene scene = new Scene(rootNode);

            LoginFormController.pane.getChildren().clear();
            Stage primaryStage = (Stage) LoginFormController.pane.getScene().getWindow();

            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Main Form");
        }
    }

}
