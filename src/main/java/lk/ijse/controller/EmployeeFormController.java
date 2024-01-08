package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.IdGenarate;
import lk.ijse.Util.Validation;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTM;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.EmployeeModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<?, ?> colEmpAddress;

    @FXML
    private TableColumn<?, ?> colEmpContact;

    @FXML
    private TableColumn<?, ?> colEmpID;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id=txtId.getText();
        EmployeeModel model=new EmployeeModel();
        try {
            boolean isDeleted=model.deleteEmployee(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION," Employee Deleted Successfully").show();
                loadAllEmployees();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (validate()){
            String id=txtId.getText();
            String name=txtName.getText();
            String address=txtAddress.getText();
            String contact=txtContact.getText();
            EmployeeDto dto=new EmployeeDto(id,name,contact,address);

            EmployeeModel employeeModel=new EmployeeModel();

            try {
                boolean isSaved= employeeModel.saveEmployee(dto);
                if(isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION," Employee Saved Successfully").show();
                    loadAllEmployees();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
        new Alert(Alert.AlertType.INFORMATION,"Validation fail").show();
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (validate()){
            String id=txtId.getText();
            String name=txtName.getText();
            String address=txtAddress.getText();
            String contact=txtContact.getText();

            EmployeeDto dto=new EmployeeDto(id,name,contact,address);

            EmployeeModel model=new EmployeeModel();
            try {
                boolean isUpdated=model.updateEmployee(dto);
                if(isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated Successfully").show();
                    loadAllEmployees();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
        else {
            new Alert(Alert.AlertType.INFORMATION,"Validation fail").show();
        }
    }

    @FXML
    void cleartxt(ActionEvent event) {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    private void setCellValueFactory() {
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        txtId.setText(genarateId());
        setCellValueFactory();
        loadAllEmployees();
    }

    private void loadAllEmployees() {
        EmployeeModel model=new EmployeeModel();
        ObservableList<EmployeeTM> obList= FXCollections.observableArrayList();
        try {
            List<EmployeeDto> list= model.getAllEmployees();
            for(EmployeeDto dto:list){
                EmployeeTM employeeTM=new EmployeeTM(dto.getEmp_id(),
                        dto.getName(),
                        dto.getContact(),
                        dto.getAddress()
                );
                obList.add(employeeTM);

            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void IdOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new EmployeeModel();
        try {
            EmployeeDto dto = model.search(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, " Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(EmployeeDto dto) {
        txtId.setText(dto.getEmp_id());
        txtName.setText(dto.getName());
        txtContact.setText(dto.getContact());
        txtAddress.setText(dto.getAddress());
    }

    boolean validate(){
        return Validation.EmployeeValidate(txtId.getText(),txtName.getText(),txtContact.getText(),txtAddress.getText());
    }

    String genarateId() throws SQLException, ClassNotFoundException {
        return IdGenarate.genarate("emp_id","employee","E0");
    }

    @SneakyThrows
    public void salaryManageOnActhion(ActionEvent actionEvent) {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/view/empSalaryForm.fxml"));
        this.pane.getChildren().clear();
        this.pane.getChildren().add(parent);
    }

}
