package com.gymapp.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

import com.gymapp.App;

/**
 * Controller for {@code component} <a href ="{@docRoot}\..\resources\com\gymapp\components\sidePanel.fxml">sidePanel.fxml</a>.
 * <p>
 * In {@code ScanController} method
 * {@linkplain com.gymapp.controllers.ScanController#sidePanelOnClickDestroysStream sidePanelOnClickDestroysStream()}
 * all handlers are overriden to support disabling camera stream when view is changed.
 * </p>
 */
public class SidePanel extends VBox {
    @FXML
    private HBox dashboardBtn;
    @FXML
    private HBox listBtn;
    @FXML
    private HBox addBtn;
    @FXML
    private HBox scanBtn;
    @FXML
    private VBox sidePanel;

    public SidePanel() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sidePanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void handleDashboard() {
        App.changeView("dashboard"); 
    }

    public void handleList() {
        App.changeView("list");
    }

    public void handleAdd() {
        App.changeView("add");
    }

    public void handleScan() {
        App.changeView("scan");
    }

    public void handleDB() {
        App.setDatabase(null);
        App.changeView("dbSelector");
    }

    public void handleExit() {
        App.closeProgram();
    }

}
