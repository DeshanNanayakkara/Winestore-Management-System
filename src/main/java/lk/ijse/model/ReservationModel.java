package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.TableDto;
import lk.ijse.dto.tm.TableTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationModel {

    public List<TableDto> getAvailableTables() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM tablee WHERE states = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, "Available");
        ResultSet resultSet = pstm.executeQuery();

        List<TableDto> list = new ArrayList<>();
        while (resultSet.next()) {
            TableDto dto = new TableDto(
                    resultSet.getString(1),
                    resultSet.getString(2));
            list.add(dto);
        }
        return list;
    }

    public List<TableDto> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM tablee Where states = ?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setString(1,"Unavailable");
        ResultSet resultSet = pstm.executeQuery();
        List<TableDto> list=new ArrayList<>();
        while (resultSet.next()){
            TableDto dto=new TableDto(
                    resultSet.getString(1),
                    resultSet.getString(2));

            list.add(dto);

        }
        return list;
    }

    public boolean updateTableStatus(String id) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        String sql="UPDATE tablee SET states=? WHERE Tabale_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, String.valueOf("Available"));
        pstm.setString(2, String.valueOf(id));


        return pstm.executeUpdate()>0;
    }
}
