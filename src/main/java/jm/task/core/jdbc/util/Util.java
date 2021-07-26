package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String url = "jdbc:mysql://localhost:3306/testproject";
    private static final String user = "root";
    private static final String password = "1234";

    public static Connection getMyConnection() throws SQLException{
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }







}
