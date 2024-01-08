package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.BiteDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BiteModel {
    public boolean saveItem(BiteDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO bite VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getItem_id());
        pstm.setString(2, String.valueOf(dto.getUnit_price()));
        pstm.setString(3, dto.getDescription());


        int i = pstm.executeUpdate();

        return(i>0);
    }

    public boolean deleteItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM bite WHERE item_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateItem(BiteDto dto) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        String sql="UPDATE item SET unit_price=?,description=? WHERE item_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, String.valueOf(dto.getUnit_price()));
        pstm.setString(2, dto.getDescription());

        return pstm.executeUpdate()>0;

    }

    public List<BiteDto> getAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM bite";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<BiteDto> list=new ArrayList<>();
        while (resultSet.next()){
            BiteDto dto=new BiteDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
            list.add(dto);
        }
        return list;
    }

    public BiteDto search(String id) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM bite WHERE item_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        BiteDto dto = null;

        if(resultSet.next()) {
            String item_id = resultSet.getString(1);
            String unit_price = resultSet.getString(2);
            String desc = resultSet.getString(3);


            dto = new BiteDto(item_id,unit_price,desc);
        }
        return dto;
    }
}
