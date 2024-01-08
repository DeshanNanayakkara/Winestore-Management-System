package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.ItemDto;
import lk.ijse.dto.OrdersDto;

import java.sql.*;
import java.time.LocalDate;

public class OrderModel {
    public String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM orders ORDER BY order_id desc ";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentOrderId = null;

        if (resultSet.next()) {
            currentOrderId = resultSet.getString(1);
            return splitOrderId(currentOrderId);
        }
        return splitOrderId(null);
    }

    private String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O");
            int id = Integer.parseInt(split[1]);
            id++;
            if (9 >= id && id > 0)
                return "O00" + id;
            else if (99 >= id && id > 9)
                return "O00" + id;
            else if (id>99) {
                return String.valueOf(id);

            }
        }
        return "O001";
    }

    public boolean placeOrder(OrdersDto dto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (OrderModel.saveOrder(dto.getOrder_id(),dto.getPrice(),dto.getDate(), dto.getCust_id())) {

                if (new ItemModel().updateItem((ItemDto) dto.getTmList())) {

                    if (new OrderDetailModel().saveOrderDetail(dto.getOrder_id(), dto.getTmList())) {
                        connection.commit();
                        result = true;
                    }
                } else {
                    System.out.println("wada na 1");
                }
            } else {
                System.out.println("wada na 2");
            }

        } catch (SQLException e) {
            connection.rollback();
            System.out.println(e.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
        return result;

    }

    public static boolean saveOrder(String orderId, double tot, LocalDate date, String cusId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, orderId);
        pstm.setString(2, String.valueOf(tot));
        pstm.setString(3, String.valueOf(date));
        pstm.setString(4, cusId);

        int i = pstm.executeUpdate();

        return(i>0);
    }

    public boolean TableAvilable(String value) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE tablee SET states=? WHERE Tabale_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, "Unavailable");
        pstm.setString(2, value);
        return pstm.executeUpdate()>0;
    }
}
