package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserModel {

    public static boolean registerUser(UserDto userDto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user VALUES (? , ? , ?)");
        preparedStatement.setObject(1,userDto.getUsername());
        preparedStatement.setObject(2,userDto.getPassword());

        boolean isRegistered = preparedStatement.executeUpdate() > 0;

        return isRegistered;

    }

    public static String getUserName(String username) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM user WHERE username = ?");
        preparedStatement.setObject(1,username);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean isExistUser(String username,String password) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT username,password  FROM user WHERE username = ? AND password = ?");
        preparedStatement.setObject(1, username);
        preparedStatement.setObject(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        String dbusername = null;
        String dbpassword = null;

        if (resultSet.next()) {
            dbusername = resultSet.getString(1);
            dbpassword = resultSet.getString(2);

        }
        boolean isCorrect = false;

        if (username.equals(dbusername) &&  password.equals(dbpassword)){
            isCorrect = true;
        }

        return isCorrect;
    }



}

