package com.tanmay.ecommerce.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
        return null;
    }

    public static void testConnection() {
        Connection conn = getConnection();

        if (conn != null) {
            System.out.println("✅ Database Connected Successfully!");
        } else {
            System.out.println("❌ Database Connection Failed!");
        }
    }
}