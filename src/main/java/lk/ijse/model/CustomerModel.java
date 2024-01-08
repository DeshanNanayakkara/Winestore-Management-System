package lk.ijse.model;


import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class   CustomerModel {
    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCust_id());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getContact());

        int i = pstm.executeUpdate();

        return(i>0);
    }

    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM customer WHERE cust_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        String sql="UPDATE customer SET name=?,contact=? WHERE cust_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getContact());
        pstm.setString(3, dto.getCust_id());

        return pstm.executeUpdate()>0;

    }

    public List<CustomerDto> getAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM customer";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<CustomerDto> list=new ArrayList<>();
        while (resultSet.next()){
            CustomerDto dto=new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
            list.add(dto);
        }
        return list;
    }

    public CustomerDto search(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM customer WHERE cust_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto = null;

        if(resultSet.next()) {
            String cust_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);


            dto = new CustomerDto(cust_id,name,contact);
        }
        return dto;
    }
}
