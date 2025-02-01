package com.gymapp.controllers;

import com.gymapp.components.SidePanel;
import com.gymapp.entity.Membership;
import com.gymapp.enums.FxmlViewEnum;
import com.gymapp.enums.MembershipEnum;
import com.gymapp.service.MembershipService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.List;

public class MembershipController implements Initializable {
    @FXML
    private TableColumn<Membership, String> idColumn;
    @FXML
    private TableColumn<Membership, String> nameColumn;
    @FXML
    private TableColumn<Membership, String> durationColumn;
    @FXML
    private TableView<Membership> membershipsTableView;
    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<Integer> durationChoiceBox;
    @FXML
    private ChoiceBox<MembershipEnum> durationTypeChoiceBox;

    @FXML
    private SidePanel sidePanel;

    private MembershipService ms;

    public MembershipController() {
        this.ms = new MembershipService();
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        sidePanel.setActiveTab(FxmlViewEnum.MEMBERSHIP);
        this.buildTableView();
        this.buildChoiceBoxes(); 
    }

    public void handleAddBtn() {
        if (!this.validateInput()) return;

        Period duration = this.parseInput();
        ms.registerNewMembership(nameTextField.getText(), duration);

        this.resetUI();
    }

    private void buildTableView() {
    idColumn.setCellValueFactory(new PropertyValueFactory<Membership, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Membership, String>("type"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Membership, String>("duration"));

        membershipsTableView.setItems(ms.getAllMemberships());
    }

    private void buildChoiceBoxes() {
        ObservableList<MembershipEnum> typesList = FXCollections.observableArrayList();
        for (MembershipEnum type : MembershipEnum.values()) {
            typesList.add(type);
        } 
        durationTypeChoiceBox.getItems().setAll(typesList);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                List<Integer> availableOptions = durationTypeChoiceBox.getValue().getAvailableOptions();
                durationChoiceBox.getItems().setAll(FXCollections.observableArrayList(availableOptions));
                durationChoiceBox.setValue(null);
            }
        };

        durationTypeChoiceBox.setOnAction(event);
    }

    private Boolean validateInput() {
        if (durationTypeChoiceBox.getValue() == null || durationChoiceBox.getValue() == null) return Boolean.valueOf(false);
        if (nameTextField.getText().equals("") || nameTextField.getText() == null) return Boolean.valueOf(false);

        return Boolean.valueOf(true);
    }

    private Period parseInput() {
        String duration = String.valueOf(durationChoiceBox.getValue());
        String modifier = durationTypeChoiceBox.getValue().getModifier();
        String periodString = "P" + duration + modifier;

        return Period.parse(periodString);
    }

    private void resetUI() {
        this.buildTableView();
        durationChoiceBox.setValue(null);
        durationTypeChoiceBox.setValue(null);
        nameTextField.setText("");
    }

}
