package com.example.flashlearning;

import java.sql.*;



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



}