package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public boolean saveSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSup_id());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getContact());
        pstm.setString(4, dto.getAddress());


        int i = pstm.executeUpdate();

        return(i>0);
    }

    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM supplier WHERE sup_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        String sql="UPDATE supplier SET name=?,contact=?,address=? WHERE sup_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getContact());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getSup_id());

        return pstm.executeUpdate()>0;

    }

    public List<SupplierDto> getAllSuppliers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM supplier";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<SupplierDto> list=new ArrayList<>();
        while (resultSet.next()){
            SupplierDto dto=new SupplierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
            list.add(dto);
        }
        return list;
    }

    public SupplierDto search(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM supplier WHERE sup_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        SupplierDto dto = null;

        if(resultSet.next()) {
            String sup_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);
            String address = resultSet.getString(4);


            dto = new SupplierDto(sup_id,name,contact,address);
        }
        return dto;
    }
}

