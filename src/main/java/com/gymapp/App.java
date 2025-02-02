package com.gymapp;

import com.gymapp.enums.FxmlViewEnum;

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

public class App extends Application {

    private static Scene scene;
    private static Stage window;

    public final static String CHECK_VALUE = "jdfhasdgfg32867jhbs";
    public final static String CONFIG_FILE = "src/main/resources/META-INF/app.config";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        window.setTitle("Gym app");
        scene = new Scene(loadFXML(FxmlViewEnum.DBSELECTOR));
        window.setScene(scene);
        window.show();
    }
    
    public static void changeView(FxmlViewEnum fxmlViewEnum) {
        if(!App.getRoot().getId().equals(fxmlViewEnum.name().toLowerCase())) {
            try {
                App.setRoot(fxmlViewEnum);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Parent loadFXML(FxmlViewEnum fxmlViewEnum) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlViewEnum.getFxmlFile()));
        return fxmlLoader.load();
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

    public static void setRoot(FxmlViewEnum fxmlViewEnum) throws IOException {
        scene.setRoot(loadFXML(fxmlViewEnum));
    }

    public static void setRoot(Parent parent) throws IOException {
        scene.setRoot(parent);
    }

    public static Stage getWindow() {
        return window;
    }

    public static Parent getRoot() {
        return scene.getRoot();
    }

}