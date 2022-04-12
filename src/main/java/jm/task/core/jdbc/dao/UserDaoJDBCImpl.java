package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users(ID MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                    "NAME VARCHAR (50) NOT NULL, " +
                    "LASTNAME VARCHAR (50) NOT NULL, AGE INT, PRIMARY KEY (ID))");
            System.out.println("Таблица создана");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            System.out.println("Таблица удалена");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try (PreparedStatement prepst = connection.prepareStatement("INSERT INTO Users(NAME, LASTNAME, AGE) " +
                     "VALUES(?, ?, ?)")) {
            connection.setAutoCommit(false);
            prepst.setString(1, name);
            prepst.setString(2, lastName);
            prepst.setByte(3, age);
            prepst.executeUpdate();
            System.out.println("User c именем " + name + " добавлен в БД");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try (PreparedStatement ps = connection.prepareStatement("DELETE " +
                "FROM Users WHERE ID = ?")) {
            connection.setAutoCommit(false);
            ps.setLong(1,id);
            ps.executeUpdate();
            System.out.println("Позьзователь был удален");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                userList.add(user);
            }
            System.out.println("Данные из БД получены");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM Users");
            System.out.println("Данные из таблицы были удалены");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
