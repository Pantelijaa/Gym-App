package com.gymapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.gymapp.App;
import com.gymapp.model.MembershipType;

/**
 * The {@code DatabaseConnection} class is used for communication with SQLite database.
 * Uses <b>only</b> database from local files
 * 
 */
public class DatabaseConnection {

    private Connection databaseLink;

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
     *                          the data produced by the given query; {@code null} if something went wrong while 
     *                          executing query
     */
    public ResultSet querySearch(String connectQuery) {
        ResultSet resultSet = null;
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
     * @return                   either (1) the row count for SQL Data Manipulation Language (DML) statements 
     *                           or (2) 0 for SQL statements that return nothing
     */
    public int queryInsert(String connectQuery) {
        int success = 0;
        try {
            Statement statement = databaseLink.createStatement();
            success = statement.executeUpdate(connectQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Checks if database contains all tables required for proper use.
     * If not creates missing tables.
     */
    public void assertValidity() {
        final String asertHistory = "CREATE TABLE IF NOT EXISTS \"History\" (" +
                              "\"id\"	INTEGER NOT NULL UNIQUE," +
                              "\"month\"	TEXT NOT NULL UNIQUE," +
                              "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
                              ")";
        final String asertMembers = "CREATE TABLE IF NOT EXISTS \"Members\" (" +
	                          "\"id\"	INTEGER NOT NULL UNIQUE," +
	                          "\"first_name\"	TEXT NOT NULL," +
	                          "\"last_name\"	TEXT NOT NULL," +
                              "\"membership_id\"	INT NOT NULL," +
                              "\"recent_purchase\"	TEXT NOT NULL," +
                              "\"expires_at\"	TEXT NOT NULL," +
                              "PRIMARY KEY(\"id\" AUTOINCREMENT)," +
                              "FOREIGN KEY(\"membership_id\") REFERENCES \"Memberships\"(\"id\")" +
                              ")";
        final String asertMembersHistory = "CREATE TABLE IF NOT EXISTS \"MembersHistory\" (" +
                                     "\"id\"	INTEGER NOT NULL UNIQUE," +
                                     "\"member_id\"	INTEGER NOT NULL," +
                                     "\"history_id\"	INTEGER NOT NULL," +
                                     "PRIMARY KEY(\"id\" AUTOINCREMENT)," +
                                     "FOREIGN KEY(\"history_id\") REFERENCES \"History\"(\"id\")," +
                                     "FOREIGN KEY(\"member_id\") REFERENCES \"Members\"(\"id\")" +
                                     ")";                 
        final String asertMemberships = "CREATE TABLE IF NOT EXISTS \"Memberships\" (" +
                                  "\"id\"	INTEGER NOT NULL UNIQUE," +
                                  "\"type\"	TEXT NOT NULL," +
                                  "PRIMARY KEY(\"id\" AUTOINCREMENT)" +
                                  ")";
        final String[] asserList = {asertHistory, asertMembers, asertMemberships, asertMembersHistory};
        for (String assertQuery : asserList) {
            queryInsert(assertQuery);
        }
    }

    /**
     * Creates database record for current month. Format used is YYYY-MM.
     */
    public void addCurrentMonthToHistory() {
        if (currentMonthExists()) return;
        final String insertQuery = "INSERT INTO History(month) VALUES(strftime('%Y-%m', 'now'))";
        queryInsert(insertQuery);
    }

    /**
     * Creates database record for each membership type.
     */
    public void addMemberships() {
        String searchQuery;
        int presence;
        for (MembershipType membership : MembershipType.values() ) {
            searchQuery = String.format("SELECT COUNT(*) FROM Memberships WHERE type='%s'", membership);
            try {
                presence = querySearch(searchQuery).getInt(1);
                if (presence == 0) {
                    queryInsert(String.format("INSERT INTO Memberships(type) VALUES('%s')", membership));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if current month is already in database. Format used is YYYY-MM.
     * 
     * @return {@code true} if month exists, {@code false} if it doesn't
     */
    private Boolean currentMonthExists() {
        int presence = 0;
        final String searchQuery = "SELECT COUNT(*) FROM History where month=(strftime('%Y-%m', 'now'))";
        try {
            presence = querySearch(searchQuery).getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (presence >= 1 ) {
            return true;
        }
        return false;
    } 

}
