package com.gymapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.gymapp.App;

/**
 * The {@code DatabaseConnection} class is used for communication with SQLite database.
 * Uses <b>only</b> database from local files
 * 
 */
public class DatabaseConnection {

    private Connection databaseLink;
    private ResultSet resultSet;

    /**
     * Establishes {@code Connection} with SQLite database.
    */
    public void getDBConnection() {
        String url = "jdbc:sqlite:" + App.getDatabase().getAbsolutePath();

        try {
            databaseLink = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Releases this {@code Connection} object's database and JDBC resources immediately
     * instead of waiting for them to be automatically released. 
     */    
    public void closeDBConnetion() {
        try {
            databaseLink.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Executes single SQL statement. Primarly used for searching data from database.
     * 
     * @param connectQuery  -   an {@code SQL} statement to be sent to the database,
     *                          typically a static SQL {@code SELECT} statement
     * @return                  {@linkplain java.sql.ResultSet ResultSet} object that contains 
     *                          the data produced by the given query; never {@code null}
     */
    public ResultSet querySearch(String connectQuery) {
        try {
            Statement statement = databaseLink.createStatement();
            resultSet = statement.executeQuery(connectQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Executes single SQL statement. Primarly used for inserting or editing data in database.
     * 
     * @param connectQuery  -    an SQL Data Manipulation Language (DML) statement,
     *                           such as {@code INSERT}, {@code UPDATE} or {@code DELETE};
     *                           or an SQL statement that returns nothing,
     *                           such as a DDL statement.
     */
    public void queryInsert(String connectQuery) {
        try {
            Statement statement = databaseLink.createStatement();
            statement.executeUpdate(connectQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if database contains all tables required for proper use.
     * If not creates missing tables.
     */
    public void assertValidity() {
        String asertHistory = "CREATE TABLE IF NOT EXISTS \"History\" (" +
                              "\"id\"	INTEGER NOT NULL UNIQUE," +
                              "\"month\"	TEXT NOT NULL," +
                              "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
                              ")";
        String asertMembers = "CREATE TABLE IF NOT EXISTS \"Members\" (" +
	                          "\"id\"	INTEGER NOT NULL UNIQUE," +
	                          "\"first_name\"	TEXT NOT NULL," +
	                          "\"last_name\"	TEXT NOT NULL," +
                              "\"membership_id\"	INT NOT NULL," +
                              "\"recent_purchase\"	TEXT NOT NULL," +
                              "\"expires_at\"	TEXT NOT NULL," +
                              "PRIMARY KEY(\"id\" AUTOINCREMENT)," +
                              "FOREIGN KEY(\"membership_id\") REFERENCES \"Memberships\"(\"id\")" +
                              ")";
        String asertMembersHistory = "CREATE TABLE IF NOT EXISTS \"MembersHistory\" (" +
                                     "\"id\"	INTEGER NOT NULL UNIQUE," +
                                     "\"member_id\"	INTEGER NOT NULL," +
                                     "\"history_id\"	INTEGER NOT NULL," +
                                     "PRIMARY KEY(\"id\" AUTOINCREMENT)," +
                                     "FOREIGN KEY(\"history_id\") REFERENCES \"History\"(\"id\")," +
                                     "FOREIGN KEY(\"member_id\") REFERENCES \"Members\"(\"id\")" +
                                     ")";                 
        String asertMemberships = "CREATE TABLE IF NOT EXISTS \"Memberships\" (" +
                                  "\"id\"	INTEGER NOT NULL UNIQUE," +
                                  "\"type\"	TEXT NOT NULL," +
                                  "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
                                  ")";
        String[] asserList = {asertHistory, asertMembers, asertMembersHistory, asertMemberships};
        for (String assertQuery : asserList) {
            queryInsert(assertQuery);
        }
    }
}
