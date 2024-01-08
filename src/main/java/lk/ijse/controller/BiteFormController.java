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
import lk.ijse.dto.BiteDto;
import lk.ijse.dto.tm.BiteTM;
import lk.ijse.model.BiteModel;

import java.sql.SQLException;
import java.util.List;

public class BiteFormController {


    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<BiteTM> tblItem;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id=txtId.getText();
        BiteModel model=new BiteModel();
        try {
            boolean isDeleted=model.deleteItem(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION," Item Deleted Successfully").show();
                loadAllItems();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
       if (validate()){
           String id=txtId.getText();
           String unitPrice=txtUnitPrice.getText();
           String desc=txtDesc.getText();
           BiteDto dto=new BiteDto(id,unitPrice,desc);

           BiteModel biteModel = new BiteModel();

           try {
               boolean isSaved= biteModel.saveItem(dto);
               if(isSaved){
                   new Alert(Alert.AlertType.CONFIRMATION," Item Saved Successfully").show();
                   loadAllItems();
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
            String unitpirce=txtUnitPrice.getText();
            String desc=txtDesc.getText();

            BiteDto dto = new BiteDto(id,unitpirce,desc);

            BiteModel model=new BiteModel();
            try {
                boolean isUpdated=model.updateItem(dto);
                if(isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item Updated Successfully").show();
                    loadAllItems();
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
        txtUnitPrice.clear();
        txtDesc.clear();
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    public void initialize() throws SQLException, ClassNotFoundException {
        txtId.setText(IDgenarate());
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {
        BiteModel model=new BiteModel();
        ObservableList<BiteTM> obList= FXCollections.observableArrayList();
        try {
            List<BiteDto> list= model.getAllItems();
            for(BiteDto dto:list){
                BiteTM biteTM = new BiteTM(dto.getItem_id(),
                        dto.getUnit_price(),
                        dto.getDescription()
                );
                obList.add(biteTM);

            }
            tblItem.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    public void IdOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new BiteModel();
        try {
            BiteDto dto = model.search(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, " Item not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(BiteDto dto) {
        txtId.setText(dto.getItem_id());
        txtUnitPrice.setText(String.valueOf(dto.getUnit_price()));
        txtDesc.setText(dto.getDescription());
    }

    boolean validate(){
       return Validation.BiteValidate(txtId.getText(),txtUnitPrice.getText());
    }

    String IDgenarate() throws SQLException, ClassNotFoundException {
        return IdGenarate.genarate("item_id","bite","B0");
    }
}
