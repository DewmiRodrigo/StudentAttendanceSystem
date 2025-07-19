module lk.cmjd111.ijse.studentattendancesystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.cmjd111.ijse.studentattendancesystem to javafx.fxml;
    exports lk.cmjd111.ijse.studentattendancesystem;
}