package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.components.SidePanel;
import com.gymapp.enums.FxmlViewEnum;
import com.gymapp.service.QRService;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import java.util.Map;
import java.net.URL;

import com.github.sarxos.webcam.Webcam;

/**
 * Controller for {@code view} <a href ="{@docRoot}\..\resources\com\gymapp\views\scan.fxml">scan.fxml</a>.
 */
public class ScanController implements Initializable {
    @FXML
    private ImageView ivCameraOutput;
    @FXML
    private Button dashboardBtn;
    @FXML
    private AnchorPane cameraContainer;
    @FXML
    private SidePanel sidePanel;

    private ObjectProperty<WritableImage> imageProperty = new SimpleObjectProperty<>();
    private QRService qrService = new QRService();

    private Webcam webcam;
    private Boolean showingDialog = false;
    private Boolean stopCamera = false;

    @FXML
    public void initialize(URL url, ResourceBundle resources) {
        sidePanel.setActiveTab(FxmlViewEnum.SCAN);
        sidePanelOnClickDestroysStream();
        startCameraInput();
    }

    /**
     * Establish connection with first available <b>webcam</b>
     */
    private void startCameraInput() {
        Task<Void> webCamTask = new Task<>() {
            @Override
            protected Void call() {
                webcam = Webcam.getDefault();
                try {
                    webcam.open();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startWebCamStream();
                return null;
            }
        };
        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();
    }

    /**
     * Called after connection with <b>webcam</b> is established.
     * <p>
     * Handles displaying <b>webcam</b> input onto {@code ImageView} and decodes data from QR Code.
     * </p>
     */
    private void startWebCamStream() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                final AtomicReference<WritableImage> ref = new AtomicReference<>();
                BufferedImage image;

                while(!stopCamera) {
                    try {
                        if ((image = webcam.getImage()) != null) {
                            ref.set(SwingFXUtils.toFXImage(image, ref.get()));
                            image.flush();
                            Platform.runLater(() -> imageProperty.set(ref.get()));

                            String scanResult = qrService.decodeQRCode(image);
                            if (scanResult != null) {
                                onQrResult(scanResult);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;                
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        
        ivCameraOutput.imageProperty().bind(imageProperty);
    }

    /**
     * Called after QR Code is successfully decoded.
     * <p>
     * Displays confirmation dialog and waits for approval. If approved stops webcam and loads new scene with decoded {@code data}
     * </p>
     * @param scanResult - data decoded from QR Code
     */
    private void onQrResult(String scanResult) {
        Platform.runLater(() -> {
            if (!showingDialog) {
                showingDialog = true;
                try {
                    int resultId = Integer.parseInt(scanResult.split(" ")[0]);
                    Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmDialog.setTitle("QR Code Detected");
                    confirmDialog.setHeaderText(String.format("%s", scanResult));
                    confirmDialog.initOwner(App.getWindow());

                    ButtonType yes = new ButtonType("Confirm");
                    ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmDialog.getButtonTypes().setAll(yes, no);
                    Optional<ButtonType> result = confirmDialog.showAndWait();
                    if (result.isPresent() && result.get() == yes) {
                        stopWebCamStream();
                        loadScanViewer(resultId);
                    }
                    showingDialog = false;
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
            }
        });
    }

    private void stopWebCamStream() {
        webcam.close();
        stopCamera = true;
    }

    /**
     * Loads new scene with precomputed data passed as function parameter.
     * Used to extend functionallity of App.setRoot(String) method
     * @param scanResult - Precomputed data
     * @TODO               Move in App module if needed in other modules
     */
    private void loadScanViewer(int resultId) {
        Parent root = null;
        Map<Class<ScanViewerController>, Callable<?>> creators = new HashMap<>();
        creators.put(ScanViewerController.class, new Callable<ScanViewerController>() {
            @Override
            public ScanViewerController call() throws Exception {
                return new ScanViewerController(resultId);
            }
        });
        FXMLLoader loader = new FXMLLoader(App.class.getResource("views/scanViewer.fxml"));

        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                Callable<?> callable = creators.get(param);
                if (callable == null) {
                    throw new IllegalArgumentException();
                } else {
                    try {
                        return callable.call();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        });
        try {
            root = loader.load();
            App.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds additional layer on top of {@code MouseEvent} defined in {@link SidePanel} to support stopping webcam 
     * stream when scene is chagned by buttons on side panel.
     */
    private void sidePanelOnClickDestroysStream() {
        sidePanel.getChildren().forEach(data -> {
            EventHandler<? super MouseEvent> previousEvent = data.getOnMouseClicked();
            data.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String regionName = data.getClass().getName();
                    if (!regionName.equals("javafx.scene.layout.Region")) {
                        /* Don't execute anything if blank space (Region) is clicked */
                        if (!regionName.equals("javafx.scene.image.ImageView")) {
                            // Don't stop camera stream if exitBtn (ImageView) is clicked cuz it will stop camera even if no is selected in prompt
                            stopWebCamStream(); // Addition for destryoing stream
                        }
                        // execute previous handler normally
                        previousEvent.handle(event);
                    }
                } 
            }); 
        });
    }

}
