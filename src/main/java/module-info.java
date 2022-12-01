module com.example.exemplobd {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires poi;

    opens com.example.exemplobd to javafx.fxml;
    exports com.example.exemplobd;
}