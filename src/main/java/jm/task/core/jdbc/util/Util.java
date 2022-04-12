package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String username = "root";
    private static final String password = "MyBigCat1606";
    private static final String url = "jdbc:mysql://localhost:3306/mytest";
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);
            //System.out.println("Соединение установлено");
            con.setAutoCommit(false);
        } catch (SQLException e) {
            //System.err.println("Невозможно установить соединение");
        }
        return con;
    }
    // реализуйте настройку соеденения с БД
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.URL, url);
                properties.put(Environment.USER,username);
                properties.put(Environment.PASS, password);
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.FORMAT_SQL, "true");
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                        applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
                System.out.println("Соединение установлено");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ошибка соединения");
            }
        }
        return sessionFactory;
    }
}
