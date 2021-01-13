package com.igorbochkor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't found MySQL driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "12345678");
        String url = "jdbc:mysql://localhost:3306";
        try {
            Connection connection = DriverManager.getConnection(url, dbProperties);
            System.out.println("Connection to DB established");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Can't established the connection to db", e);
        }
    }
}
