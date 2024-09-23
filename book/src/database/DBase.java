package database;

import java.sql.*;

public class DBase {
    // database properties
    public final static String DB_URL = "jdbc:mysql://localhost:3306/jdb_book";
    public final static String DB_USER= "root";
    public final static String DB_PASS= "root";

    //
    public DBase() {}

    //
    public static Connection getConnection_v2() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            return connection;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
