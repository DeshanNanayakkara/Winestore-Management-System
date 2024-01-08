package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Util.IdGenarate;
import lk.ijse.Util.Validation;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTM;
import lk.ijse.model.CustomerModel;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCustId;

    @FXML
    private TableColumn<?, ?> colCustName;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id=txtId.getText();
        CustomerModel model=new CustomerModel();
        try {
            boolean isDeleted=model.deleteCustomer(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION," Customer Deleted Successfully").show();
                loadAllCustomers();
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
           String contact=txtContact.getText();
           CustomerDto dto=new CustomerDto(id,name,contact);

           CustomerModel customerModel=new CustomerModel();

           try {
               boolean isSaved= customerModel.saveCustomer(dto);
               if(isSaved){
                   new Alert(Alert.AlertType.CONFIRMATION," Customer Saved Successfully").show();
                   loadAllCustomers();
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
    void btnUpdateOnAction(ActionEvent event) {
       if (validate()){
           String id=txtId.getText();
           String name=txtName.getText();
           String contact=txtContact.getText();

           CustomerDto dto=new CustomerDto(id,name,contact);

           CustomerModel model=new CustomerModel();
           try {
               boolean isUpdated=model.updateCustomer(dto);
               if(isUpdated){
                   new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated Successfully").show();
                   loadAllCustomers();
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
        txtContact.clear();
    }

    private void setCellValueFactory() {
        colCustId.setCellValueFactory(new PropertyValueFactory<>("cust_id"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        txtId.setText(genarateId());
        setCellValueFactory();
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        CustomerModel model=new CustomerModel();
        ObservableList<CustomerTM> obList= FXCollections.observableArrayList();
        try {
            List<CustomerDto> list= model.getAllCustomers();
            for(CustomerDto dto:list){
                CustomerTM customerTM=new CustomerTM(dto.getCust_id(),
                        dto.getName(),
                        dto.getContact()
                );
                obList.add(customerTM);

            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void IdOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new CustomerModel();
        try {
            CustomerDto dto = model.search(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, " Customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(CustomerDto dto) {
        txtId.setText(dto.getCust_id());
        txtName.setText(dto.getName());
        txtContact.setText(dto.getContact());
    }

    boolean validate(){
        return Validation.CustomerValidate(txtId.getText(),txtName.getText(),txtContact.getText());
    }
    String genarateId() throws SQLException, ClassNotFoundException {
       return IdGenarate.genarate("cust_id","customer","C0");
    }
}
