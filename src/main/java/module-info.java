module com.gymapp {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires transitive java.sql;
    requires transitive com.google.zxing;
    requires com.google.zxing.javase;
    requires transitive java.desktop;
    requires webcam.capture;
    requires log4j;


    opens com.gymapp to javafx.fxml;
    exports com.gymapp;

    opens com.gymapp.controllers to javafx.fxml;
    exports com.gymapp.controllers;

    opens com.gymapp.components to javafx.fxml;
    exports com.gymapp.components;

    opens com.gymapp.db to javafx.fxml;
    exports com.gymapp.db;

    opens com.gymapp.model to javafx.fxml;
    exports com.gymapp.model;
}
