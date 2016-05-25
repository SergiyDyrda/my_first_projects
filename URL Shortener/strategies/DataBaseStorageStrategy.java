package com.javarush.test.level33.lesson15.big01.strategies;

import com.javarush.test.level33.lesson15.big01.ExceptionHandler;

import java.sql.*;


/**
 * Created by Segiy on 12.03.2016.
 */
public class DataBaseStorageStrategy implements StorageStrategy {
    private Connection connection = null;
    private PreparedStatement statement;
    int firstIndex = 1;
    int secondIndex = 2;

    public DataBaseStorageStrategy() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/idservice?autoReconnect=true&useSSL=false", "root", "root");
        }
        catch (Exception e) {
//            ExceptionHandler.log(e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean containsKey(Long key) {
        if (getValue(key) != null)
            return true;

        return false;
    }

    @Override
    public boolean containsValue(String value) {
        if (getKey(value) != null)
            return true;

        return false;
    }

    @Override
    public void put(Long key, String value) {

        try {
            statement = connection.prepareStatement("INSERT INTO ids (ID, STRING)" +
                    "VALUES (?,?);");

            statement.setLong(firstIndex, key);
            statement.setString(secondIndex, value);
            statement.executeUpdate();
        }
        catch (SQLException e) {
//            ExceptionHandler.log(e);
            e.printStackTrace();
        }
    }

    @Override
    public Long getKey(String value) {

        try {
            statement = connection.prepareStatement("SELECT ID FROM ids WHERE STRING LIKE ?");
            statement.setString(firstIndex, value);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getLong(1);

        }
        catch (SQLException e) {
//            ExceptionHandler.log(e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getValue(Long key) {

        try {
            statement = connection.prepareStatement("SELECT STRING FROM ids WHERE ID=?");
            statement.setLong(firstIndex, key);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getString(1);

        }
        catch (SQLException e) {
//            ExceptionHandler.log(e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void finalize() throws Throwable {

        if (connection != null) {
            connection.close();
        }
    }
}
