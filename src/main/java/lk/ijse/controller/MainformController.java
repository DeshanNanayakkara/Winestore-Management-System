package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainformController implements Initializable {

    @FXML
    private AnchorPane pane;


    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        pane.getChildren().clear();
        onTheTopNavigation(pane,"customerForm.fxml");
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        pane.getChildren().clear();
        onTheTopNavigation(pane,"dashboard.fxml");
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        pane.getChildren().clear();
        onTheTopNavigation(pane,"employeeForm.fxml");
    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        pane.getChildren().clear();
        onTheTopNavigation(pane,"itemForm.fxml");
    }
    @FXML
    void btnBitesOnAction(ActionEvent event) {
        pane.getChildren().clear();
        onTheTopNavigation(pane,"BiteForm.fxml");
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        pane.getChildren().clear();
        onTheTopNavigation(pane,"orderForm.fxml");
    }

    @FXML
    void btnSignoutOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
        Parent root = loader.load();

        LoginFormController LoginFormController = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        Stage currentStage = (Stage) pane.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void btnSpplierOnAction(ActionEvent event) {
        pane.getChildren().clear();
        onTheTopNavigation(pane,"supplierForm.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onTheTopNavigation(pane,"dashboard.fxml");
    }

    private void onTheTopNavigation(Pane pane, String link) {
        try {
            FXMLLoader loader = new FXMLLoader(MainformController.class.getResource("/view/" + link));
            Parent root = loader.load();
            pane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void navigateToReservatioForm(ActionEvent actionEvent) {
        pane.getChildren().clear();
        onTheTopNavigation(pane,"ReservationForm.fxml");
    }
}
