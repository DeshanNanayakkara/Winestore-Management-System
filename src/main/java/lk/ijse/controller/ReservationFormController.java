package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dto.TableDto;
import lk.ijse.dto.tm.TableTM;
import lk.ijse.model.ReservationModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static javafx.collections.FXCollections.observableList;

public class ReservationFormController {

    @FXML
    private TableColumn action;
    @FXML
    private TableColumn tableId;
    @FXML
    private TableColumn availability;
    @FXML
    private TableView tblReservation;
    ReservationModel reservationModel = new ReservationModel();


    public void initialize() throws SQLException {
        loadTableData();
        setCellValueFactory();
    }

    private void loadTableData() throws SQLException {
        ObservableList<TableTM> observableList = FXCollections.observableArrayList();

        List<TableDto> list = reservationModel.getAll();
        for (TableDto dto : list) {
            JFXButton btn = new JFXButton("Mark as available");
            btn.setCursor(Cursor.HAND);
            btn.setStyle("-fx-background-color: #35A29F");

            btn.setOnAction((e) -> {
                        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure?", yes, no).showAndWait();

                        if (type.orElse(no) == yes) {

                            int index = tblReservation.getSelectionModel().getFocusedIndex();
                            TableTM focusedRow = (TableTM) tblReservation.getFocusModel().getFocusedItem();
                            String id = focusedRow.getId();
                            try {
                                boolean updated = updateTableStatus(id);
                                if (updated){
                                    tblReservation.getItems().remove(id);
                                    tblReservation.refresh();
                                }
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    }

            );
            TableTM tableTM = new TableTM(
                    dto.getId(),
                    dto.getAvailability(),
                    btn
            );
            observableList.add(tableTM);

            tblReservation.setItems(observableList);

        }
    }


    private void setCellValueFactory() {
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        availability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        action.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }


    private boolean updateTableStatus(String id) throws SQLException {
        System.out.println(id);
       return reservationModel.updateTableStatus(id);
    }
}

