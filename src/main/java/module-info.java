module lk.cmjd111.ijse.studentattendancesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires mysql.connector.j;
    requires java.sql;


    opens lk.cmjd111.ijse.studentattendancesystem to javafx.fxml;
    exports lk.cmjd111.ijse.studentattendancesystem;
    exports lk.cmjd111.ijse.studentattendancesystem.controller;
    opens lk.cmjd111.ijse.studentattendancesystem.controller to javafx.fxml;
}