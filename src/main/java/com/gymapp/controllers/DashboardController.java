package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.components.SidePanel;
import com.gymapp.enums.FxmlViewEnum;
import com.gymapp.service.GymMemberService;
import com.gymapp.service.HistoryService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.net.URL;
import java.time.YearMonth;

/**
 * Controller for {@code view} <a href="{@docRoot}\..\resources\com\gymapp\views\dashboard.fxml">dashboard.fxml</a>.
 */
public class DashboardController implements Initializable {
    @FXML
    private PieChart returningPieChart;
    @FXML
    private LineChart<String, Integer> historyLineChart;
    @FXML
    private Label todayCounter;
    @FXML
    private SidePanel sidePanel;

    private GymMemberService gms;
    private HistoryService hs;

    public DashboardController() {
        this.gms = new GymMemberService();
        this.hs = new HistoryService();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resources) {
        App.setActiveTab(sidePanel, FxmlViewEnum.DASHBOARD);
        this.buildPieChart(); 
        this.buildTodayCounter();
        this.buildLineChart();
    }

    private void buildPieChart() {
        try {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Non-active", gms.getInactiveMembersCount()));
            HashMap<String, Integer> activeMap = gms.getActiveMembersCount();

            activeMap.forEach((key, value) -> {
                pieChartData.add(new PieChart.Data(key, value));
            });

            returningPieChart.setData(pieChartData);
            pieChartData.forEach(data -> {
                String value = Double.toString(data.getPieValue());
                Tooltip toolTip = new Tooltip(value.substring(0, value.length() - 2));
                toolTip.setShowDelay(Duration.millis(250));
                Tooltip.install(data.getNode(), toolTip); 
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildTodayCounter() {
        todayCounter.setText(Integer.toString(gms.getTodayMembersCount()));
    }

    private void buildLineChart() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        HashMap<YearMonth, Integer> historyHashMap = hs.getHistoryOfMembersPerMonth();
        
        historyHashMap.forEach((key, value) -> series.getData().add(new XYChart.Data<>(key.toString(), value)));
        historyLineChart.getData().add(series);
    }
}
