package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.components.SidePanel;
import com.gymapp.db.DatabaseConnection;
import com.gymapp.model.GymMember;
import com.gymapp.model.QRgenerator;
import com.gymapp.model.MembershipType;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;


import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;

public class AddController implements Initializable {
    @FXML
    private ChoiceBox<MembershipType> chooseMembership;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private SidePanel sidePanel;

    private DatabaseConnection dbLink;


    public AddController() {
        this.dbLink = new DatabaseConnection();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resources) {
        App.setActiveTab(sidePanel, 2);
        chooseMembership.getItems().setAll(MembershipType.values());
        chooseMembership.setValue(MembershipType.ONE_MONTH);
    }

    public void handleAddBtn() {
        GymMember gymMember = new GymMember();
        gymMember.setFirstName(firstNameTextField.getText());
        gymMember.setLastName(lastNameTextField.getText());
        gymMember.setMembershipType(chooseMembership.getValue());

        dbLink.getDBConnection();
        this.addNewMember(gymMember);
        /*
        * Random junk at the end of data to force formatting to bigger QR code
        * Cuz small ones are hard to read
        * And adds additional possibility for security check
        */
        String data = String.format("%d %s %s %s", this.getLastID(), gymMember.getFirstName(), gymMember.getLastName(), App.getCheckValue());
        try {
            generateQR(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbLink.closeDBConnetion();

        // Reset UI components to default
        firstNameTextField.clear();
        lastNameTextField.clear();
        chooseMembership.setValue(MembershipType.ONE_MONTH);
    }

    private void addNewMember(GymMember gymMember) {
        int membershipID = this.membershipTypeToID(gymMember.getMembershipType());
        String insertQuery = String.format("INSERT INTO Members(first_name, last_name, membership_id, recent_purchase, expires_at) VALUES('%s', '%s', %d, '%s', '%s')", gymMember.getFirstName(), gymMember.getLastName(), membershipID, this.getCurrentDate(), this.extendMembership(gymMember.getMembershipType()));
        try {
            dbLink.queryInsertDB(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getLastID() {
        String searchQuery = "SELECT last_insert_rowid() FROM Members";
        int lastID = 0;
        try {
            lastID = dbLink.querySearchDB(searchQuery).getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastID;
    }

    private void generateQR(String data) {
        String path = "src/main/resources/com/gymapp/QR/"+ data + ".png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
        int height = 1000;
        int width = 1000;
        try {
            QRgenerator.createQR(data, path, charset, hashMap, height, width);
        } catch (IOException e) { 
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private int membershipTypeToID(MembershipType membership) {
        int membershipID = 1;
        try {
            String searchQuery = String.format("SELECT id FROM Memberships WHERE type = '%s'", membership.toString());
            membershipID = dbLink.querySearchDB(searchQuery).getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return membershipID;
    }

    /**
     * Returns current time expressed in ISO 8601 format
     * @return {@code String} in format {@code yyyy-MM-dd}
     */
    private String getCurrentDate() {
        Date now = Date.from(Instant.now());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(now);
        return date;
    }

    private String extendMembership(MembershipType membership) {
        String additionalTime;
        String date = null;
        Date expireDate;
        SimpleDateFormat df;
        switch (membership) {
            case ONE_MONTH:
                additionalTime = "P30d";
                break;
            case THREE_MOTHNS:
                additionalTime = "P90d";
                break;
            case ONE_YEAR:
                additionalTime = "P365d";
                break;
            default:
                additionalTime = null;
                break;
        }

        expireDate = Date.from(Instant.now().plus(Duration.parse(additionalTime)));
        df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(expireDate);

        return date;
    }
}
