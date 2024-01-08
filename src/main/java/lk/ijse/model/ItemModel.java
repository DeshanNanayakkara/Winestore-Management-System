package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.ItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public boolean saveItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO item VALUES (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getItem_id());
        pstm.setString(2, String.valueOf(dto.getUnit_price()));
        pstm.setString(3, String.valueOf(dto.getStock_price()));
        pstm.setString(4, dto.getDescription());
        pstm.setString(5, String.valueOf(dto.getQtyOnHand()));


        int i = pstm.executeUpdate();

        return(i>0);
    }

    public boolean deleteItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="DELETE FROM item WHERE item_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateItem(ItemDto dto) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        String sql="UPDATE item SET unit_price=?,stock_price=?,description=?,qtyOnHand=? WHERE item_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, String.valueOf(dto.getUnit_price()));
        pstm.setString(2, String.valueOf(dto.getStock_price()));
        pstm.setString(3, dto.getDescription());
        pstm.setString(4, String.valueOf(dto.getQtyOnHand()));

        return pstm.executeUpdate()>0;

    }

    public List<ItemDto> getAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM item";
        PreparedStatement pstm=connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<ItemDto> list=new ArrayList<>();
        while (resultSet.next()){
            ItemDto dto=new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5));
            list.add(dto);
        }
        return list;
    }

    public ItemDto search(String id) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM item WHERE item_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        ItemDto dto = null;

        if(resultSet.next()) {
            String item_id = resultSet.getString(1);
            String unit_price = resultSet.getString(2);
            String stock_price = resultSet.getString(3);
            String desc = resultSet.getString(4);
            String qtyOnHand = resultSet.getString(5);


            dto = new ItemDto(item_id,unit_price,stock_price,desc, Integer.valueOf(qtyOnHand));
        }
        return dto;
    }

}
