package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.components.SidePanel;
import com.gymapp.db.DatabaseConnection;
import com.gymapp.model.GymMember;
import com.gymapp.model.MembershipType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Controller for {@code view} <a href="{@docRoot}\..\resources\com\gymapp\views\list.fxml">list.fxml</a>.
 */
public class ListController implements Initializable {
    @FXML
    private TableColumn<GymMember , String> id;
    @FXML
    private TableColumn<GymMember , String> firstName;
    @FXML
    private TableColumn<GymMember , String> lastName;
    @FXML
    private TableColumn<GymMember , String> membershipType;
    @FXML
    private TableColumn<GymMember , String> recentPurchase;
    @FXML
    private TableColumn<GymMember, String> expiresAt;
    @FXML
    private TableView<GymMember> membersTableView;
    @FXML 
    private SidePanel sidePanel;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        App.setActiveTab(sidePanel, 1);
        id.setCellValueFactory(new PropertyValueFactory<GymMember, String>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<GymMember, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<GymMember, String>("lastName"));
        membershipType.setCellValueFactory(new PropertyValueFactory<GymMember, String>("membershipType"));
        recentPurchase.setCellValueFactory(new PropertyValueFactory<GymMember, String>("recentPurchase"));
        expiresAt.setCellValueFactory(new PropertyValueFactory<GymMember, String>("expiresAt"));

        membersTableView.setItems(loadData());
    }

    private ObservableList<GymMember> loadData() {
        GymMember member;
        ObservableList<GymMember> gymMembers = FXCollections.observableArrayList();
        String connectQuery = "SELECT Memberships.type, Members.id, Members.first_name, Members.last_name, Members.recent_purchase, Members.expires_at FROM Members JOIN Memberships on Members.membership_id = Memberships.id";
        DatabaseConnection dbLink = new DatabaseConnection();
        dbLink.getDBConnection();
        try {
            
            ResultSet queryOutput = dbLink.querySearch(connectQuery);

            while (queryOutput.next()) {
                int id = queryOutput.getInt("id");
                String firstName = queryOutput.getString("first_name");
                String lastName = queryOutput.getString("last_name");
                String membershipType = queryOutput.getString("type");
                String recentPurchase = queryOutput.getString("recent_purchase");
                String expiresAt = queryOutput.getString("expires_at");

                member = new GymMember(id, firstName, lastName, MembershipType.fromString(membershipType) , recentPurchase, expiresAt);


                gymMembers.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbLink.closeDBConnetion();
        return gymMembers;
    }
}
