package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String username = "root";
    private static final String password = "MyBigCat1606";
    private static final String url = "jdbc:mysql://localhost:3306/mytest";

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Соединение установлено");
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Невозможно установить соединение");
        }
        return con;
    }
    // реализуйте настройку соеденения с БД
}
