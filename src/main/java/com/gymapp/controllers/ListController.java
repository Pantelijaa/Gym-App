package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.components.SidePanel;
import com.gymapp.entity.GymMember;
import com.gymapp.service.GymMemberService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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

    private GymMemberService gms;

    public ListController() {
        this.gms = new GymMemberService();
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        App.setActiveTab(sidePanel, 1);
        id.setCellValueFactory(new PropertyValueFactory<GymMember, String>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<GymMember, String>("lastName"));
        lastName.setCellValueFactory(new PropertyValueFactory<GymMember, String>("firstName"));
        membershipType.setCellValueFactory(new PropertyValueFactory<GymMember, String>("membershipType"));
        recentPurchase.setCellValueFactory(new PropertyValueFactory<GymMember, String>("recentPurchase"));
        expiresAt.setCellValueFactory(new PropertyValueFactory<GymMember, String>("expiresAt"));

        membersTableView.setItems(gms.getAllMembers());
    }

}
