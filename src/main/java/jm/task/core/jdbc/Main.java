package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {

        UserService users = new UserServiceImpl();
        users.cleanUsersTable();
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
