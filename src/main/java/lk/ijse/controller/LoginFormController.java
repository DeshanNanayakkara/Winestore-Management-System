package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.Email.EmailController;
import lk.ijse.model.UserModel;


import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPw;
    @FXML
    private AnchorPane root;
    public static AnchorPane pane;

    @FXML
    public void btnLoginOnAction(javafx.event.ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPw.getText();

        try {
            boolean userIsExist = UserModel.isExistUser(username,password);

            if(userIsExist){
                navigateToDashboard();
            }else{
                new Alert(Alert.AlertType.WARNING,"Username and Password is Wrong!").show();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void navigateToDashboard() throws IOException{
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/mainform.fxml"));
        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Main Form");
    }
    @FXML
    public void registerOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signupform.fxml"));
        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("SignUp Form");
    }

    public static int otp = 0;
    public void forgetPassOnActhion(ActionEvent actionEvent) throws MessagingException {
        otp = new Random().nextInt(9000) + 1000;
        EmailController.sendEmail("udayangadeshan2528@gmail.com ",otp);
        System.out.println(1);
        openPopupInThread();
    }

    private void openPopupInThread() {
        new Thread(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/otpForm.fxml"));
                Parent root = loader.load();
                Platform.runLater(() -> {
                    Stage popupStage = new Stage();
                    popupStage.setTitle("Popup Form");
                    popupStage.setScene(new Scene(root));
                    popupStage.showAndWait();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane = root;
    }
}

