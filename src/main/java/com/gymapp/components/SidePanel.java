package com.gymapp.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Properties;

import com.gymapp.App;
import com.gymapp.enums.FxmlViewEnum;
import com.gymapp.helpers.PropertiesHelper;

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

    public void setActiveTab(FxmlViewEnum fxmlViewEnum) {
        sidePanel.getChildren().get(fxmlViewEnum.getSidePanelIndex()).getStyleClass().add("sidepanel-active");
    }    

    public void handleDashboard() {
        App.changeView(FxmlViewEnum.DASHBOARD); 
    }

    public void handleList() {
        App.changeView(FxmlViewEnum.LIST);
    }

    public void handleAdd() {
        App.changeView(FxmlViewEnum.ADD);
    }

    public void handleMembership() {
        App.changeView(FxmlViewEnum.MEMBERSHIP);
    }

    public void handleScan() {
        App.changeView(FxmlViewEnum.SCAN);
    }

    public void handleDB() {
        Properties prop = new Properties();
        PropertiesHelper.loadPropertiesFromFile(prop, App.CONFIG_FILE);
        PropertiesHelper.deleteDBPath(prop, App.CONFIG_FILE);

        App.changeView(FxmlViewEnum.DBSELECTOR);
    }

    public void handleExit() {
        App.closeProgram();
    }

}
