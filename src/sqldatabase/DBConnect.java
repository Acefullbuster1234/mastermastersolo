package sqldatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_db?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "PLACEHOLDER";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_db?serverTimezone=UTC", "root", "PLACEHOLDER");
    }
}
