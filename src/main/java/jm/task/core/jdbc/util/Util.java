package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String url = "jdbc:mysql://localhost:3306/testproject";
    private static final String user = "root";
    private static final String password = "1234";

    public static Connection getMyConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load class");
            e.printStackTrace();
        } catch (SQLException se) {
            System.out.println("Unable to open connection");
            se.printStackTrace();
        }
        return conn;
    }

    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e){
                System.out.println("Error during rollback");
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn) {
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e){
                System.out.println("Error during closing connection");
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException e){
                System.out.println("Error during closing statement");
                e.printStackTrace();
            }
        }
    }

    public static void close(PreparedStatement ps){
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e){
                System.out.println("Error during closing prepared statement");
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e){
                System.out.println("Error during closing prepared statement");
                e.printStackTrace();
            }
        }
    }

}


