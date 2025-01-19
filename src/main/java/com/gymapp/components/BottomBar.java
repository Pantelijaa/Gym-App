package com.gymapp.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.io.IOException;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.io.File;

import com.gymapp.App;

/**
 * Controller for {@code component} <a href ="{@docRoot}\..\resources\com\gymapp\components\bottomBar.fxml">bottomBar.fxml</a>.
 */
public class BottomBar extends HBox implements Initializable {
    @FXML
    private Label time;
    @FXML
    private Label databaseInfo;
    @FXML
    private HBox bottomBar;

    public BottomBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bottomBar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resources) {
        initializeClock();
        initializeDatabaseInfo();
    }

    private void initializeClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd")));
        }),
            new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.playFromStart();
    }
    
    private void initializeDatabaseInfo() {
        File database = App.getDatabase();
        if (database != null) {
            databaseInfo.setText("Currently open: " + database.getName());
        }
    }
}
