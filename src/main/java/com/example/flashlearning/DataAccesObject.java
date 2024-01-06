package com.example.flashlearning;

import java.sql.*;
import java.util.List;


public class DataAccesObject {

    private Connection con;


    public DataAccesObject() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Anders_FlashLearning;userName=CSe2023t_t_1;password=CSe2023tT1#23;encrypt=true;trustServerCertificate=true");
        } catch (SQLException e) {
            System.err.println("Can't connect to Database: " + e.getErrorCode() + e.getMessage());
        }
        System.out.println("Connected to Database");
    }

    public void createTable(String tableName, List<String> columns) {
        if (columns == null || columns.isEmpty()) {
            System.out.println("No columns provided for the table creation.");
            return;
        }

        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE ");
        sqlBuilder.append(tableName).append(" (");

        for (int i = 0; i < columns.size(); i++) {
            sqlBuilder.append(columns.get(i));
            if (i < columns.size() - 1) {
                sqlBuilder.append(", ");
            }
        }

        sqlBuilder.append(");");

        try (Statement stmt = con.createStatement()) {
            stmt.execute(sqlBuilder.toString());
            System.out.println("Table " + tableName + " created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    List<String> columns = Arrays.asList(
        "ID VARCHAR(255)",
        "NoteType VARCHAR(255)",
        "DeckName VARCHAR(255)",
        // Add more columns as required
    );
    dataAccessObject.createTable("PresidentsFlashcards", columns);
    */



}