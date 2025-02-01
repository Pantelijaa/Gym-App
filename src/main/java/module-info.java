module com.gymapp {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires transitive java.sql;
    requires java.sql.rowset;
    requires transitive com.google.zxing;
    requires com.google.zxing.javase;
    requires transitive java.desktop;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires webcam.capture;
    requires log4j;

    opens com.gymapp to javafx.fxml;
    exports com.gymapp;

    opens com.gymapp.controllers to javafx.fxml;
    exports com.gymapp.controllers;

    opens com.gymapp.components to javafx.fxml;
    exports com.gymapp.components;

    opens com.gymapp.service to javafx.fxml;
    exports com.gymapp.service;

    opens com.gymapp.entity;
    exports com.gymapp.entity;

    opens com.gymapp.converters to org.hibernate.orm.core;
    exports com.gymapp.converters;

    exports com.gymapp.enums;

}
