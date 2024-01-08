package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.UserDto;
import lk.ijse.model.UserModel;


import java.io.IOException;

public class SignupFormController {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtUsername2;

    @FXML
    private PasswordField txtPw2;


    public void btnRegisterOnAction(ActionEvent actionEvent) {
        try{
            boolean userCheck = txtUsername2.getText().equals(UserModel.getUserName(txtUsername2.getText()));

            if(!userCheck){

                UserDto userDto = new UserDto();

                userDto.setUsername(txtUsername2.getText());
                userDto.setPassword(txtPw2.getText());

                boolean saved = UserModel.registerUser(userDto);

                if(saved) {
                    new Alert(Alert.AlertType.INFORMATION,"User Registered!").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"User not Registered!").show();
                }
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Already Registered!").show();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/loginform.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }
}
