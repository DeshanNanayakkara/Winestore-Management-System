package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Util.IdGenarate;
import lk.ijse.Util.Validation;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.tm.SalaryTm;
import lk.ijse.model.SalaryModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EmpSalaryFormController implements Initializable {

    @FXML
    private TextField amount;

    @FXML
    private ComboBox<String> empId;

    @FXML
    private ComboBox<String> month;

    @FXML
    private TextField salaryId;

    SalaryModel salaryModel = new SalaryModel();

    @FXML
    void clearbtnOnActhion(ActionEvent event) {
        salaryId.clear();
        empId.setValue(null);
        amount.clear();
        month.setValue(null);
    }

    @FXML
    void deleteonActhion(ActionEvent event) {
        try {
            boolean isDeleted = salaryModel.delete(salaryId.getText());
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                loadTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void savebtnOnActhion(ActionEvent event) {
        if (validate()){
            String EmpId = (String) empId.getValue();
            String month = (String) this.month.getValue();
            try {
                boolean isSaved = salaryModel.save(new SalaryDto(salaryId.getText(),EmpId,null,amount.getText(),month));
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Saved").show();
                    loadTable();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void updatebtnOnActhion(ActionEvent event) {

        if (validate()){
            String EmpId = (String) empId.getValue();
            String month = (String) this.month.getValue();
            try {
                boolean isSaved = salaryModel.Update(new SalaryDto(salaryId.getText(),EmpId,null,amount.getText(),month));
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Updated").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    boolean validate(){
        return Validation.salaryValidate(amount.getText());
    }
    String genatateId() throws SQLException, ClassNotFoundException {
        return IdGenarate.genarate("salary_id","salary","S0");
    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salaryId.setText(genatateId());
        ObservableList<String> observableList = FXCollections.observableArrayList(
                "January","February","March","April","May","June","July","August","September","October","November","December"
        );
        month.setItems(observableList);
        getAllId();
        loadTable();
    }

    void getAllId() throws SQLException {
        ObservableList<String> id = salaryModel.getAllId();
        empId.setItems(id);
    }

    @FXML
    private TableView<SalaryTm> table;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colEmp;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colMonth;


    void loadTable() throws SQLException {

        colID.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmp.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));


        List<SalaryDto> list = salaryModel.getAll();
        ObservableList<SalaryTm> obList= FXCollections.observableArrayList();
        for(SalaryDto dto:list){
            SalaryTm Salary =new SalaryTm(
                    dto.getSalary_id(),
                    dto.getEmp_id(),
                    dto.getAmount(),
                    dto.getMonth()
            );
            obList.add(Salary);

        }
        table.setItems(obList);
    }
}
