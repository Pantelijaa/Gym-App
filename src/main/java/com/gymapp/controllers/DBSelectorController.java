package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.db.DatabaseConnection;

import javafx.stage.FileChooser;

import java.io.File;

/**
 * Controller for {@code view} <a href="{@docRoot}\..\resources\com\gymapp\views\dbSelector.fxml">dbSelector.fxml</a>.
 */
public class DBSelectorController {
   
    public void handleOpen() {
        final FileChooser fileChooser = new FileChooser();
        configureOpenDialog(fileChooser);
        File file = fileChooser.showOpenDialog(App.getWindow());
        if (file != null && file.getAbsolutePath().endsWith(".db")) {
            openDatabase(file);
        }
    } 

    public void handleCreate() {
        final FileChooser fileChooser = new FileChooser();
        configureSaveDialog(fileChooser);
        File file = fileChooser.showSaveDialog(App.getWindow());
        if (file != null && file.getAbsolutePath().endsWith(".db")) {
            openDatabase(file);
        }
    }

    public void handleExit() {
        App.closeProgram();
    }

    private void configureOpenDialog(final FileChooser fileChooser) {
        fileChooser.setTitle("Open database file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Items", "*.*"),
            new FileChooser.ExtensionFilter("Databases", "*.db")
        );
    }

    private void configureSaveDialog(final FileChooser fileChooser) {
        fileChooser.setTitle("Save database file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Databases", "*.db"));
    }

    private void openDatabase(File file) {
        App.setDatabase(file);
        DatabaseConnection dbLink = new DatabaseConnection();
        dbLink.getDBConnection();
        dbLink.assertValidity();
        dbLink.addCurrentMonthToHistory();
        dbLink.closeDBConnetion();
        App.changeView("dashboard");
    }
}
