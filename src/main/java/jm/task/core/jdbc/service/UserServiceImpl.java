package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;

public class UserServiceImpl implements UserService {

    //решил создать отдельный приватный метод проверки существования таблицы по условию задания (для dropUsersTable и createUsersTable)
    private boolean isTableExist() {
        String checkForExist = "SELECT count(*) FROM information_schema.TABLES WHERE (TABLE_SCHEMA = 'testproject') AND (TABLE_NAME = 'users');";
        int isExist = 0;
        try (Statement state = Util.getMyConnection().createStatement()) {
            ResultSet rs = state.executeQuery(checkForExist);
            while (rs.next()) {
                isExist = rs.getInt(1);
            }
            return isExist == 1;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    }

    public void createUsersTable() {
        String sql = "create table USERS (\n" +
                "ID bigint not null AUTO_INCREMENT,\n" +
                "NAME varchar(255) not null,\n" +
                "LASTNAME varchar(255) not null,\n" +
                "AGE tinyint not null,\n" +
                "primary key (ID)\n" +
                ");";

        try (Statement state = Util.getMyConnection().createStatement()) {
            if (isTableExist()) {
                System.out.println("Table Users already exists");
            } else {
                state.execute(sql);
                System.out.println("Table Users has been successfully created");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "drop table users;";
        try (Statement state = Util.getMyConnection().createStatement()) {
            if (isTableExist()) {
                state.execute(sql);
                System.out.println("Table Users has been deleted");
            } else {
                System.out.println("Table Users do not exists, nothing to delete");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (name, lastname, age) values (?, ?, ?);";
        try (PreparedStatement state = Util.getMyConnection().prepareStatement(sql)) {
            state.setString(1, name);
            state.setString(2, lastName);
            state.setByte(3, age);
            state.executeUpdate();
            System.out.println("User with name: " + name + ", lastname: " + lastName +
                    ", age: " + age + " has been successfully added");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id = ?";
        try (PreparedStatement state = Util.getMyConnection().prepareStatement(sql)) {
            state.setLong(1, id);
            state.executeUpdate();
            System.out.println("User with id " + id + " has been succesfully removed");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "select name, lastname, age from users";
        try (Statement state = Util.getMyConnection().createStatement()){
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()){
                String name = rs.getString(1);
                String lastname = rs.getString(2);
                byte age = rs.getByte(3);
                User user = new User(name, lastname, age);
                list.add(user);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "truncate table users;";
        try (Statement state = Util.getMyConnection().createStatement()) {
            state.execute(sql);
            System.out.println("Users has been removed");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
