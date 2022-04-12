package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users (ID MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                    "NAME VARCHAR (50) NOT NULL, " +
                    "LASTNAME VARCHAR (50) NOT NULL, AGE INT, PRIMARY KEY (ID))");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE Users");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM Users WHERE ID = " + id + ";");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("FROM Users").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) {
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM Users");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) {
                transaction.rollback();
            }
        }
    }
}
