package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.sql.rowset.Predicate;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    /* в общем я не освоил, можно ли сделать реализацию try с ресурсами, если необходимо дополнительно ловить
    исключения роллбэков и коммитов, т.к. в таком случае я не могу созданный Connection обработать в блоке catch,
    чтобы сделать conn.rollback

    также не хотелось, чтобы блоки catch выглядели очень громоздко из-за вложенностей, поэтому в классе Util
    создал отдельные методы close и rollback с ловлей исключений
     */

    public void createUsersTable() {
        Connection conn = Util.getMyConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("create table IF NOT EXISTS USERS (\n" +
                    "   ID bigint not null AUTO_INCREMENT,\n" +
                    "   NAME varchar(255) not null,\n" +
                    "   LASTNAME varchar(255) not null,\n" +
                    "   AGE tinyint not null,\n" +
                    "   primary key (ID)\n" +
                    ");");
            conn.commit();
            System.out.println("Table Users has been successfully created");
        } catch (SQLException e) {
            Util.rollback(conn);
            System.out.println("Error during creating table");
            e.printStackTrace();
        } finally {
            Util.close(stmt);
            Util.close(conn);
        }
    }

    public void dropUsersTable() {
        Connection conn = Util.getMyConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("drop table if exists users;");
            conn.commit();
            System.out.println("Table Users has been successfully dropped");
        } catch (SQLException e) {
            Util.rollback(conn);
            System.out.println("Error during dropping table");
            e.printStackTrace();
        } finally {
            Util.close(stmt);
            Util.close(conn);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection conn = Util.getMyConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("insert into users (name, lastname, age) values (?, ?, ?);");
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            conn.commit();
            System.out.println("User with name: " + name + ", lastname: " + lastName +
                    ", age: " + age + " has been successfully added");
        } catch (SQLException e) {
            Util.rollback(conn);
            System.out.println("Error during removing user");
            e.printStackTrace();
        } finally {
            Util.close(stmt);
            Util.close(conn);
        }
    }

    public void removeUserById(long id) {
        Connection conn = Util.getMyConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("delete from users where id = ?;");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            conn.commit();
            System.out.println("User with id " + id + " has been successfully removed");
        } catch (SQLException e) {
            Util.rollback(conn);
            System.out.println("Error during removing user");
            e.printStackTrace();
        } finally {
            Util.close(stmt);
            Util.close(conn);
        }
    }

    public List<User> getAllUsers() {
        Connection conn = Util.getMyConnection();
        Statement stmt = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select id, name, lastname, age from users");
            while (rs.next()){
                Long id = rs.getLong(1);
                String name = rs.getString(2);
                String lastname = rs.getString(3);
                byte age = rs.getByte(4);
                User user = new User(id, name, lastname, age);
                list.add(user);
            }
        } catch (SQLException e){
            Util.rollback(conn);
            System.out.println("Error during getting all users");
        } finally {
            Util.close(rs);
            Util.close(stmt);
            Util.close(conn);
        }
        return list;
    }

    public void cleanUsersTable() {
        Connection conn = Util.getMyConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("truncate table users;");
            conn.commit();
            System.out.println("Users has been successfully removed");
        } catch (SQLException e) {
            Util.rollback(conn);
            System.out.println("Error during cleaning table");
            e.printStackTrace();
        } finally {
            Util.close(stmt);
            Util.close(conn);
        }

    }
}