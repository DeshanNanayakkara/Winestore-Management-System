package lk.ijse.model;


import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class   EmployeeModel {
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmp_id());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getContact());
        pstm.setString(4, dto.getAddress());


        int i = pstm.executeUpdate();

        return(i>0);
    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM employee WHERE emp_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        String sql="UPDATE employee SET name=?,contact=?,address=? WHERE emp_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getContact());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getEmp_id());

        return pstm.executeUpdate()>0;

    }

    public List<EmployeeDto> getAllEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM employee";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<EmployeeDto> list=new ArrayList<>();
        while (resultSet.next()){
            EmployeeDto dto=new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
            list.add(dto);
        }
        return list;
    }

    public EmployeeDto search(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM employee WHERE emp_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if(resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);
            String address = resultSet.getString(4);


            dto = new EmployeeDto(emp_id,name,contact,address);
        }
        return dto;
    }
}
