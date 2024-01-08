package lk.ijse.Util;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdGenarate {
    public static String genarate(String colum, String table,String type) throws SQLException, ClassNotFoundException {

        Connection connection = DbConnection.getInstance().getConnection();
        String qry = "SELECT " + colum + " FROM " + table + " ORDER BY " + colum + " DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(qry);

        ResultSet resultSet = pstm.executeQuery();

        String lastId = null;
        if (resultSet.next()) {
            lastId = resultSet.getString(1);
        }
        if (lastId == null) {
            return type + "1";
        }
        String[] split = lastId.split(type);
        int index = Integer.parseInt(split[1]);
        index++;
        System.out.println(type + index);
        return type + index;
    }
}
