package jm.task.core.jdbc;

import java.sql.*;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl users = new UserServiceImpl();
        users.createUsersTable();
        users.saveUser("Egor", "Brows", (byte) 24);
        users.saveUser("Sven", "Cat", (byte) 3);
        users.saveUser("Daria", "Ice", (byte) 19);
        users.saveUser("Max", "Cat", (byte) 7);
        System.out.println(users.getAllUsers());
        users.cleanUsersTable();
        users.dropUsersTable();
    }
}
