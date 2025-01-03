package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.components.SidePanel;
import com.gymapp.db.DatabaseConnection;
import com.gymapp.model.GymMember;
import com.gymapp.model.MembershipType;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.ResultSet;

public class ScanViewerController implements Initializable {
    @FXML 
    private Label labelFirstName;
    @FXML 
    private Label labelLastName;    
    @FXML 
    private Label labelID;
    @FXML
    private Label labelMembership;
    @FXML
    private Label labelPurchase;
    @FXML
    private Label labelExpire;
    @FXML
    private ImageView ivQr;
    @FXML
    private ChoiceBox<MembershipType> chooseMembership;
    @FXML
    private SidePanel sidePanel;

    private String data;
    private GymMember gymMember;
    private DatabaseConnection dbLink;

    public ScanViewerController(String data) {
        this.data = data;
        this.gymMember = new GymMember();
        this.dbLink = new DatabaseConnection();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resources) {
        App.setActiveTab(sidePanel, 3);
        chooseMembership.getItems().setAll(MembershipType.values());
        chooseMembership.setValue(MembershipType.ONE_MONTH);
        //data is forced to always have same format by AddController
        String[] dataSubstrings = data.split(" ");
        String searchQuery = null;

        if (dataSubstrings.length != 4 || !ValidateQR(dataSubstrings[3])) {
            System.out.println("Illegal QRcode");
            App.changeView("list");
            return;
        }

        dbLink.getDBConnection();
        searchQuery = String.format("SELECT Members.id, Members.first_name, Members.last_name, Members.recent_purchase, Members.expires_at, Memberships.type FROM Members, Memberships WHERE Members.id = %s AND Members.first_name='%s' AND Members.last_name='%s' AND Memberships.id = Members.membership_id", dataSubstrings[0], dataSubstrings[1], dataSubstrings[2]);
        ResultSet queryOutput = dbLink.querySearchDB(searchQuery);
        try {
            gymMember.setId(queryOutput.getInt("id"));
            gymMember.setFirstName(queryOutput.getString("first_name"));
            gymMember.setLastName(queryOutput.getString("last_name"));
            gymMember.setRecentPurchase(queryOutput.getString("recent_purchase"));
            gymMember.setMembershipType(MembershipType.fromString(queryOutput.getString("type")));
            gymMember.setExpiresAt(queryOutput.getString("expires_at"));

            labelID.setText(String.valueOf(gymMember.getId()));
            labelFirstName.setText(gymMember.getFirstName());
            labelLastName.setText(gymMember.getLastName());
            labelMembership.setText(gymMember.getMembershipType().toString());
            labelPurchase.setText(gymMember.getRecentPurchase());
            labelExpire.setText(gymMember.getExpiresAt());

            Image image = new Image(String.format("file:src/main/resources/com/gymapp/QR/%s %s %s %s.png", gymMember.getId(), gymMember.getFirstName(), gymMember.getLastName(), App.getCheckValue()), 150, 150, true, true);
            ivQr.setImage(image);

        } catch (Exception e) {
            e.printStackTrace();
        }

        dbLink.closeDBConnetion();
    }

    private Boolean ValidateQR(String checkValue) {
        if (checkValue.equals(App.getCheckValue())) {
            return true;
        } else {
            return false;
        }
    }

    private void extendMembership(MembershipType purchasedType) {
        String idSearchQuery = String.format("SELECT id FROM Memberships WHERE type == '%s'", purchasedType.toString());
        int membershipId = 0;
        dbLink.getDBConnection();
        try {
            membershipId = dbLink.querySearchDB(idSearchQuery).getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String updateQuery = String.format("UPDATE Members SET membership_id = %d, recent_purchase = date('now'), expires_at = date(expires_at, '+%s') WHERE id = %d", membershipId , purchasedType.toDateModifier(), gymMember.getId());
        try {
            dbLink.queryInsertDB(updateQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbLink.closeDBConnetion();
    }

    public void handleExtendButton() {
        MembershipType purchasedType = chooseMembership.getValue();
        extendMembership(purchasedType);
        App.changeView("list");
    }
}
