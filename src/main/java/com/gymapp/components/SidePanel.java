package com.gymapp.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

import com.gymapp.App;

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

    /*
     * In ScanController.java private void sidePanelDestroysCameraStream() 
     * all handlers are overriden to support disabling camera stream on view swap
     */
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

    public void handleExit() {
        App.closeProgram();
    }



}
