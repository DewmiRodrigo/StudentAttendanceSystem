package lk.cmjd111.ijse.studentattendancesystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dBConnection;
    private Connection connection;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_attendance_system?serverTimezone=UTC",
                "root",
                "dew08"
        );
    }

    public static DBConnection getInstance() throws SQLException {
        if (dBConnection == null) {
            dBConnection = new DBConnection();
        }
        return dBConnection;
    }

    public Connection getConnection() {
        return connection;
    }

}