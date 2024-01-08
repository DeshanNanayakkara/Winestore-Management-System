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
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.SupplierTM;
import lk.ijse.model.EmployeeModel;
import lk.ijse.model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colSupAddress;

    @FXML
    private TableColumn<?, ?> colSupContact;

    @FXML
    private TableColumn<?, ?> colSupID;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableView<SupplierTM> tblSupplier;

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
        SupplierModel model=new SupplierModel();
        try {
            boolean isDeleted=model.deleteSupplier(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION," Supplier Deleted Successfully").show();
                loadAllSuppliers();
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
            SupplierDto dto=new SupplierDto(id,name,contact,address);

            SupplierModel supplierModel=new SupplierModel();

            try {
                boolean isSaved= supplierModel.saveSupplier(dto);
                if(isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION," Supplier Saved Successfully").show();
                    loadAllSuppliers();
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
            String address=txtAddress.getText();
            String contact=txtContact.getText();

            SupplierDto dto=new SupplierDto(id,name,contact,address);

            SupplierModel model=new SupplierModel();
            try {
                boolean isUpdated=model.updateSupplier(dto);
                if(isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Updated Successfully").show();
                    loadAllSuppliers();
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
        colSupID.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSupAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        txtId.setText(genarateId());
        setCellValueFactory();
        loadAllSuppliers();
    }

    private void loadAllSuppliers() {
        SupplierModel model=new SupplierModel();
        ObservableList<SupplierTM> obList= FXCollections.observableArrayList();
        try {
            List<SupplierDto> list= model.getAllSuppliers();
            for(SupplierDto dto:list){
                SupplierTM supplierTM=new SupplierTM(dto.getSup_id(),
                        dto.getName(),
                        dto.getContact(),
                        dto.getAddress()
                );
                obList.add(supplierTM);

            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void IdOnAtion(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new SupplierModel();
        try {
            SupplierDto dto = model.search(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, " Supplier not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(SupplierDto dto) {
        txtId.setText(dto.getSup_id());
        txtName.setText(dto.getName());
        txtContact.setText(dto.getContact());
        txtAddress.setText(dto.getAddress());
    }

    boolean validate(){
        return Validation.SupplierValidate(txtId.getText(),txtName.getText(),txtContact.getText(),txtAddress.getText());
    }

    String genarateId() throws SQLException, ClassNotFoundException {
        return IdGenarate.genarate("sup_id","supplier","S0");
    }
}
