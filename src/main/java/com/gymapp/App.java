package com.gymapp;

import com.gymapp.components.SidePanel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;

import java.io.IOException;
import java.util.Optional;

//import org.apache.log4j.BasicConfigurator;

public class App extends Application {

    private static Scene scene;
    private static Stage window;

    private final static String checkValue = "jdfhasdgfg32867jhbs";

    public static void main(String[] args) {
        //BasicConfigurator.configure();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        scene = new Scene(loadFXML("dashboard"), 640, 480);
        window.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setTitle("Gym app");
        window.setScene(scene);
        window.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(Parent parent) throws IOException {
        scene.setRoot(parent);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Stage getWindow() {
        return window;
    }

    public static Parent getRoot() {
        return scene.getRoot();
    }

    public final static String getCheckValue() {
        return checkValue;
    }

    /*
     * Only for UI purpose
     */
    public static void setActiveTab(SidePanel sidePanel, int index) {
        sidePanel.getChildren().get(index).getStyleClass().add("sidepanel-active");
    }    
    
    public static void changeView(String view) {
        if(!App.getRoot().getId().equals(view)) {
            try {
                App.setRoot(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeProgram() {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Exit Program");
        confirmDialog.setHeaderText("Sure want to exit?");
        confirmDialog.initOwner(window);
        ButtonType yes = new ButtonType("Confirm");
        ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmDialog.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            Platform.exit();
            System.exit(0); 
        }
    }
}