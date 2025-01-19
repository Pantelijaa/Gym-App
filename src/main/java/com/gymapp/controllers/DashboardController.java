package com.gymapp.controllers;

import com.gymapp.App;
import com.gymapp.components.SidePanel;
import com.gymapp.db.DatabaseConnection;

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

import java.util.ResourceBundle;
import java.net.URL;
import java.sql.ResultSet;

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

    private DatabaseConnection dbLink;

    public DashboardController() {
        this.dbLink = new DatabaseConnection();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resources) {
        App.setActiveTab(sidePanel, 0);
        dbLink.getDBConnection();
        this.buildPieChart(); 
        this.buildTodayCounter();
        this.buildLineChart();
        dbLink.closeDBConnetion();
    }

    private void buildPieChart() {
        String countInactiveQuery = "SELECT count() FROM Members WHERE expires_at < date('now', 'start of day', 'localtime')";
        String countActiveQuery = "SELECT count(), Memberships.type FROM Members RIGHT JOIN Memberships ON Members.membership_id = Memberships.id AND Members.expires_at >= date('now', 'start of day', 'localtime') GROUP BY Memberships.id";
        try {
            int inactiveCount = dbLink.querySearch(countInactiveQuery).getInt(1);
            ResultSet active = dbLink.querySearch(countActiveQuery);

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( new PieChart.Data("Non-active", inactiveCount));
            while (active.next()) {
                pieChartData.add(new PieChart.Data(active.getString("type"), active.getInt("count()")));
            }

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
        String searchQuery = "SELECT count() FROM Members WHERE recent_purchase = date('now')";
        try {
            int today = dbLink.querySearch(searchQuery).getInt(1);
            todayCounter.setText(Integer.toString(today));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildLineChart() {
        String searchQuery = "SELECT count(), History.month FROM MembersHistory, History WHERE MembersHistory.history_id = History.id GROUP BY History.month";
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        try {
            ResultSet queryOutput = dbLink.querySearch(searchQuery);
            while (queryOutput.next()) {
                int numberOfMember = queryOutput.getInt(1);
                String month = queryOutput.getString(2);
                series.getData().add(new XYChart.Data<>(month, numberOfMember));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        historyLineChart.getData().add(series);
    }
}
