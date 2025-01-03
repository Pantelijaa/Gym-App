package com.gymapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The {@code DatabaseConnection} class is used for communication with SQLite database.
 * Uses <b>only</b> database from local files
 * 
 */
public class DatabaseConnection {

    private Connection databaseLink;
    private ResultSet resultSet;

    public void getDBConnection() {
        String url = "jdbc:sqlite:src/main/resources/com/gymapp/db/teretana.db";

        try {
            databaseLink = DriverManager.getConnection(url);
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDBConnetion() {
        try {
            databaseLink.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet querySearchDB(String connectQuery) {
        try {
            Statement statement = databaseLink.createStatement();
            resultSet = statement.executeQuery(connectQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    
    public void queryInsertDB(String connectQuery) {
        try {
            Statement statement = databaseLink.createStatement();
            statement.executeUpdate(connectQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
