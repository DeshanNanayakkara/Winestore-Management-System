package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public boolean saveOrderDetail(String orderId, List<CartTm> tmList) throws SQLException {
        for (CartTm cartTm : tmList) {
            if(!saveOrderDetail1(orderId, cartTm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail1(String orderId, CartTm cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO order_detail VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, cartTm.getCode());
        pstm.setInt(3, cartTm.getQty());

        return pstm.executeUpdate() > 0;
    }

}
