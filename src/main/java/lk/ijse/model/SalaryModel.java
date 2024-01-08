package lk.ijse.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public boolean save(SalaryDto dto) throws SQLException {
        Connection connection= DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO salary VALUES (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,dto.getSalary_id());
        pstm.setString(2,dto.getEmp_id());
        String date = String.valueOf(LocalDate.now());
        pstm.setString(3,date);
        pstm.setString(4,dto.getAmount());
        pstm.setString(5,dto.getMonth());

        return pstm.executeUpdate() > 0;
    }

    public ObservableList<String> getAllId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT emp_id FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        while (resultSet.next()){
            observableList.add(resultSet.getString(1));
        }
        return observableList;
    }

    public boolean Update(SalaryDto dto) throws SQLException {
        Connection connection= DbConnection.getInstance().getConnection();
        String sql = "UPDATE salary SET emp_id = ?, amount = ?, Month = ? WHERE salary_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,dto.getEmp_id());
        pstm.setString(2,dto.getAmount());
        pstm.setString(3,dto.getSalary_id());
        pstm.setString(4, dto.getMonth());

        return pstm.executeUpdate() > 0;
    }

    public List<SalaryDto> getAll() throws SQLException {
        Connection connection= DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM salary";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<SalaryDto> dto = new ArrayList<>();
        while (resultSet.next()){
            dto.add(
                    new SalaryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        return dto;
    }

    public boolean delete(String text) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM salary WHERE salary_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);
        return pstm.executeUpdate() > 0;
    }
}
