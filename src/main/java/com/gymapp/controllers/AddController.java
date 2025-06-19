package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.components.SidePanel;
import com.gymapp.entity.Membership;
import com.gymapp.enums.FxmlViewEnum;
import com.gymapp.service.GymMemberService;
import com.gymapp.service.MembershipService;
import com.gymapp.service.QRService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;

/**
 * Controller for {@code view} <a href="{@docRoot}\..\resources\com\gymapp\views\add.fxml">add.fxml</a>.
 */
public class AddController implements Initializable {
    @FXML
    private ChoiceBox<String> chooseMembership;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private SidePanel sidePanel;

    private final GymMemberService gms;
    private final MembershipService ms;

    public AddController() {
        this.gms = new GymMemberService();
        this.ms = new MembershipService();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resources) {
        sidePanel.setActiveTab(FxmlViewEnum.ADD);
        List<String> membershipTypes = ms.getAllTypes();
        chooseMembership.getItems().setAll(membershipTypes);
        try {
            chooseMembership.setValue(membershipTypes.get(0));
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("No memberships");
        }
    }

    public void handleAddBtn() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String data = null;

        if (firstName.equals("") || lastName.equals("")) return;

        Membership membership = ms.getByType(chooseMembership.getValue());
        int id = gms.registerNewMember(firstName, lastName, membership);

        /*
        * Random junk at the end of data to force formatting to bigger QR code
        * Cuz small ones are hard to read
        * And adds additional possibility for security check
        */
        data = String.format("%d %s %s %s",
                            id,
                            firstName,
                            lastName,
                            App.CHECK_VALUE);
        this.generateQR(data);

        this.resetUI();
    }


    private void generateQR(String data) {
        String path = "src/main/resources/com/gymapp/QR/" + data + ".png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        int height = 1000;
        int width = 1000;
        try {
            QRService.createQR(data, path, charset, hashMap, height, width);
        } catch (IOException e) { 
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void resetUI() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        chooseMembership.setValue(ms.getAllTypes().get(0));
    }
}
