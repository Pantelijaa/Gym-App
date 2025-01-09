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

    /**
     * Establishes connection with database. {@code url} where database is stored defined locally. 
     * If you want to use other database {@code url} needs to be changed.
     * 
    */
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

    /**
     *  Executes single SQL statement. Primarly used for searching data from database.
     * 
     * @param connectQuery  -   an {@code SQL} statement to be sent to the database,
     *                          typically a static SQL {@code SELECT} statement
     * @return                  {@linkplain java.sql.ResultSet ResultSet} object that contains 
     *                          the data produced by the given query; never {@code null}
     */
    public ResultSet querySearchDB(String connectQuery) {
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
    public void queryInsertDB(String connectQuery) {
        try {
            Statement statement = databaseLink.createStatement();
            statement.executeUpdate(connectQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
