package lk.cmjd111.ijse.studentattendancesystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_attendance_system",
                "root",
                "dew08"
        );
    }

    public static DBConnection getInstance() throws SQLException {
        return (dbConnection == null) ? new DBConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}