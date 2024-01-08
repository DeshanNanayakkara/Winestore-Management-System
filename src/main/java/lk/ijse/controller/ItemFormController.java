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
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.ItemTM;
import lk.ijse.model.ItemModel;
import lk.ijse.model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStockPrice;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtStockPrice;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id=txtId.getText();
        ItemModel model=new ItemModel();
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
           String stockPrice=txtStockPrice.getText();
           String unitPrice=txtUnitPrice.getText();
           String desc=txtDesc.getText();
           String qtyOnHand=txtQtyOnHand.getText();
           ItemDto dto=new ItemDto(id,unitPrice,stockPrice,desc,Integer.parseInt(qtyOnHand));

           ItemModel itemModel=new ItemModel();

           try {
               boolean isSaved= itemModel.saveItem(dto);
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
            String stockPrice=txtStockPrice.getText();
            String desc=txtDesc.getText();
            String qtyOnHand=txtQtyOnHand.getText();

            ItemDto dto=new ItemDto(id,unitpirce,stockPrice,desc,Integer.parseInt(qtyOnHand));

            ItemModel model=new ItemModel();
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

    public String generateId(){
        try {
            return IdGenarate.genarate("item_id","item","I0");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cleartxt(ActionEvent event) {
        txtId.clear();
        txtUnitPrice.clear();
        txtStockPrice.clear();
        txtDesc.clear();
        txtQtyOnHand.clear();
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colStockPrice.setCellValueFactory(new PropertyValueFactory<>("stock_price"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    public void initialize(){
        txtId.setText(generateId());
        setCellValueFactory();
        loadAllItems();
    }

    private void loadAllItems() {
        ItemModel model=new ItemModel();
        ObservableList<ItemTM> obList= FXCollections.observableArrayList();
        try {
            List<ItemDto> list= model.getAllItems();
            for(ItemDto dto:list){
                ItemTM itemTM=new ItemTM(dto.getItem_id(),
                        dto.getUnit_price(),
                        dto.getStock_price(),
                        dto.getDescription(),
                        dto.getQtyOnHand()
                );
                obList.add(itemTM);

            }
            tblItem.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    public void IdOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        var model = new ItemModel();
        try {
            ItemDto dto = model.search(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, " Item not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(ItemDto dto) {
        txtId.setText(dto.getItem_id());
        txtUnitPrice.setText(String.valueOf(dto.getUnit_price()));
        txtStockPrice.setText(String.valueOf(dto.getStock_price()));
        txtDesc.setText(dto.getDescription());
        txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
    }

    boolean validate(){
        return Validation.ItemValidate(txtId.getText(),txtUnitPrice.getText(),txtStockPrice.getText(),txtQtyOnHand.getText());
    }
}
