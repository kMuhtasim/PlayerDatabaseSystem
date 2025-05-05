module com.example.playerdatabasesystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.playerdatabasesystem to javafx.fxml;
    exports com.example.playerdatabasesystem;
}