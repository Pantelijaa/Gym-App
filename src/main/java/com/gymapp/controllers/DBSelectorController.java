package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.enums.FxmlViewEnum;
import com.gymapp.helpers.PropertiesHelper;
import com.gymapp.service.HistoryService;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.Properties;

/**
 * Controller for {@code view} <a href="{@docRoot}\..\resources\com\gymapp\views\dbSelector.fxml">dbSelector.fxml</a>.
 */
public class DBSelectorController {

    private HistoryService hs;
    private final String dbPath;

    public DBSelectorController() {
        Properties prop = new Properties();
        PropertiesHelper.loadPropertiesFromFile(prop, App.CONFIG_FILE);
        dbPath = prop.getProperty("db.path");
    }

    public void handleOpen() {
        File file;
        if (dbPath != null && dbPath != "" && dbPath.endsWith(".db")) {
            file = new File(dbPath);
            if (file.exists() && !file.isDirectory()) {
                openDatabase(new File(dbPath));
                return;
            }
        }
    
        final FileChooser fileChooser = new FileChooser();
        configureOpenDialog(fileChooser);
        file = fileChooser.showOpenDialog(App.getWindow());
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

    private void openDatabase(File DBFile) {
        Properties prop = new Properties();
        
        PropertiesHelper.loadPropertiesFromFile(prop, App.CONFIG_FILE);
        PropertiesHelper.updateDBPath(prop, DBFile, App.CONFIG_FILE);

        hs = new HistoryService();
        hs.addCurrentMonth();

        App.changeView(FxmlViewEnum.DASHBOARD);
    }

}
