package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderFormController {
    @FXML
    private ComboBox cmbTable;
    @FXML
    private ComboBox<String> cmbctg;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty1;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lbalOrderId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDesc;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private TextField txtQty;

    private final ObservableList<CartTm> obList = FXCollections.observableArrayList();

    private ObservableList<String> typeOfProduct=FXCollections.observableArrayList();

    @FXML
    void addToCartOnAction(ActionEvent event) {
        try {
            String code = (String) cmbItemCode.getValue();
            String description = lblDesc.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(lblUnitPrice.getText());
            double total = qty * unitPrice;
            JFXButton btn = new JFXButton("Remove");
            btn.setCursor(Cursor.HAND);
            btn.setStyle("-fx-background-color: #35A29F");

            btn.setOnAction((e) -> {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {
                    int index = tblOrderCart.getSelectionModel().getSelectedIndex();
                    obList.remove(index);
                    tblOrderCart.refresh();


                    calculateNetTotal();
                }
            });

            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (code.equals(colItemCode.getCellData(i))) {
                    qty += (int) colQty1.getCellData(i);
                    total = qty * unitPrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTot(total);

                    tblOrderCart.refresh();
                    calculateNetTotal();
                    return;
                }
            }


            obList.add(new CartTm(
                    code,
                    description,
                    qty,
                    unitPrice,
                    total,
                    btn
            ));

            tblOrderCart.setItems(obList);
            calculateNetTotal();
            txtQty.clear();
        } catch (NumberFormatException e) {

        }
    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        lblTotal.setText(String.valueOf(total));

    }


    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        //CustomerDto dto = new CustomerModel().search((String) cmbCustomerId.getValue());
        try {
            CustomerModel customerModel = new CustomerModel();
            System.out.println(cmbCustomerId.getValue());
            CustomerDto dto = customerModel.search((String) cmbCustomerId.getValue());

            lblCustomerName.setText(dto.getName());
        } catch (Exception e) {

        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String type = cmbctg.getValue();

        if ("wines".equals(type)){
            ItemDto dto = new ItemModel().search((String) cmbItemCode.getValue());
            lblDesc.setText(dto.getDescription());
            lblUnitPrice.setText(String.valueOf(dto.getUnit_price()));
        } else {
            BiteDto dto = new BiteModel().search((String) cmbItemCode.getValue());
            lblDesc.setText(dto.getDescription());
            lblUnitPrice.setText(String.valueOf(dto.getUnit_price()));
        }

    }

    @FXML
    void placeOrderOnAction(ActionEvent event) throws SQLException {

        String orderId = lbalOrderId.getText();
        String cusId = (String) cmbCustomerId.getValue();
        LocalDate date = LocalDate.parse(lblDate.getText());
        double tot = Double.parseDouble(lblTotal.getText());
        List<CartTm> tmList = new ArrayList<>();

        for (CartTm cartTm : obList) {
            tmList.add(cartTm);
        }




        var dto = new OrdersDto(
                orderId,
                tot,
                date,
                cusId,
                tmList
        );

        try{
            if (new OrderModel().saveOrder(dto.getOrder_id(),dto.getPrice(),dto.getDate(), dto.getCust_id())) {
                String tableId = (String) cmbTable.getValue();
                if(new OrderModel().TableAvilable(tableId)){
                    new Alert(Alert.AlertType.INFORMATION,"Order Placed").show();
                }
                initialize();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public   void initialize() throws SQLException {
        loadAvailableTables();
        generateNextOrderid();
        setDate();
        loadCustomerIds();
        setCellValueFactory();
        typeOfProduct.add("wines");
        typeOfProduct.add("bites");
        cmbctg.setItems(typeOfProduct);

    }
    private void loadAvailableTables() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<TableDto> tableList = new ReservationModel().getAvailableTables();

        for (TableDto dto : tableList) {
            obList.add(dto.getId());
        }
        cmbTable.setItems(obList);
    }








    private void generateNextOrderid(){
        try {
            String orderId = new OrderModel().generateNextOrderId();
            lbalOrderId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadCustomerIds() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<CustomerDto> cusList = new CustomerModel().getAllCustomers();

        for (CustomerDto dto : cusList) {
            obList.add(dto.getCust_id());
        }
        cmbCustomerId.setItems(obList);
    }

    private void loadWinesItemCodes(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemList = new ItemModel().getAllItems();

            for (ItemDto itemDto : itemList) {
                obList.add(itemDto.getItem_id());
            }

            cmbItemCode.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadBitesItemCodes(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<BiteDto> itemList = new BiteModel().getAllItems();

            for (BiteDto biteDto : itemList) {
                obList.add(biteDto.getItem_id());
            }

            cmbItemCode.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void CmbCtgOnAction(ActionEvent event) {

        String selectedCategory = (String) cmbctg.getValue();

        if ("wines".equals(selectedCategory)) {
            // Handle wines category
            // Update the item code ComboBox with wines-related data
            loadWinesItemCodes();
        } else if ("bites".equals(selectedCategory)) {
            // Handle bites category
            // Update the item code ComboBox with bites-related data
            loadBitesItemCodes();
        } else {
            // Handle other categories or provide a default behavior
        }
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty1.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/orders.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void cmbTableOnAction(ActionEvent actionEvent) {
    }
}
