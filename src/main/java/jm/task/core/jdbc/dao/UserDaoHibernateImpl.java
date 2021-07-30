package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("create table IF NOT EXISTS USERS (\n" +
                    "   ID bigint not null AUTO_INCREMENT,\n" +
                    "   NAME varchar(255) not null,\n" +
                    "   LASTNAME varchar(255) not null,\n" +
                    "   AGE tinyint not null,\n" +
                    "   primary key (ID)\n" +
                    ");").executeUpdate();
            transaction.commit();
            System.out.println("Table Users has been successfully created");
        } catch (Exception e) {
            System.out.println("Error during creating table");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists users;").executeUpdate();
            transaction.commit();
            System.out.println("Table Users has been successfully dropped");
        } catch (Exception e) {
            System.out.println("Error during dropping table");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User with name: " + name + ", lastname: " + lastName +
                    ", age: " + age + " has been successfully added");
        } catch (Exception e) {
            System.out.println("Error during saving user");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("User with id " + id + " has been successfully removed");
        } catch (Exception e) {
            System.out.println("Error during removing user");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rootEntry = cq.from(User.class);
            CriteriaQuery<User> all = cq.select(rootEntry);

            TypedQuery<User> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            System.out.println("Error during getting all users");
            e.printStackTrace();
        }
        return getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            final List<User> users = getAllUsers();
            for (User user : users) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("Users has been successfully removed");
        } catch (Exception e) {
            System.out.println("Error during cleaning table");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
