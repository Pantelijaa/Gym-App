package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.components.SidePanel;
import com.gymapp.entity.GymMember;
import com.gymapp.enums.FxmlViewEnum;
import com.gymapp.service.GymMemberService;
import com.gymapp.service.MembershipService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

/**
 * Controller for {@code view} <a href ="{@docRoot}\..\resources\com\gymapp\views\scanViewer.fxml">scanViewer.fxml</a>.
 */
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
    private ChoiceBox<String> chooseMembership;
    @FXML
    private SidePanel sidePanel;

    GymMemberService gms;
    MembershipService ms;
    GymMember member;

    public ScanViewerController(int resultId) {
        this.gms = new GymMemberService();
        this.ms = new MembershipService();
        this.member = gms.getById(resultId);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resources) {
        App.setActiveTab(sidePanel, FxmlViewEnum.SCANVIEWER);
        List<String> membershipTypes = ms.getAllTypes();
        chooseMembership.getItems().setAll(membershipTypes);
        chooseMembership.setValue(membershipTypes.get(0));

        labelID.setText(String.valueOf(member.getId()));
        labelFirstName.setText(member.getFirstName());
        labelLastName.setText(member.getLastName());
        labelMembership.setText(member.getMembershipType());
        labelPurchase.setText(member.getRecentPurchase().toString());
        labelExpire.setText(member.getExpiresAt().toString());

        loadQrImage(); 
    }

    public void handleExtendButton() {
        gms.extendMembership(member.getId(), ms.getByType(chooseMembership.getValue()));
        App.changeView(FxmlViewEnum.LIST);
    }

    private void loadQrImage() {
        Image image = new Image(String.format("file:src/main/resources/com/gymapp/QR/%s %s %s %s.png",
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                App.CHECK_VALUE
                ), 
                150,
                150,
                true, 
                true
            );
        ivQr.setImage(image);
    }

}
