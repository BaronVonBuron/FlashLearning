package com.example.flashlearning;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void updateSomething(String s){
        try {
            Statement database = con.createStatement();
            database.executeUpdate(s);
            System.out.println("Statement: "+s+" Has been executed.");
        } catch (SQLException e) {
            System.out.println("Can't do requested statement: "+s+ " \nCode: "+ e.getErrorCode()+" Because: " + e.getMessage());
        }
    }

    public void addDeck(Deck deck){
        String s1 = "INSERT INTO Deck (Name) VALUES ('"+deck.getName()+"');";
        updateSomething(s1);
        for (Flashcard flashcard : deck.getFlashcards()) {
            addCard(flashcard);
        }
    }


    public void addCard(Flashcard flashcard) {
        // SQL statement with placeholders for values
        String sql = "INSERT INTO Flashcard (ID, Deck, Question, Answer, BonusInfo, ImageData) VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {//TODO change name, fix comments.
            // Set parameters
            pstmt.setString(1, flashcard.getID());
            pstmt.setString(2, flashcard.getDeck());
            pstmt.setString(3, flashcard.getQuestion());
            pstmt.setString(4, flashcard.getAnswer());
            pstmt.setString(5, flashcard.getBonusinfo());
            pstmt.setBytes(6, flashcard.getImageData()); // Set image data as byte array

            // Execute update
            pstmt.executeUpdate();
            System.out.println("Card added: " + flashcard.getID());
        } catch (SQLException e) {
            System.err.println("Error adding card: " + e.getMessage());
            logFailedFlashcard(flashcard, e.getMessage());
        }
    }

    public ArrayList<Deck> returnDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        String s = "SELECT * FROM Deck";
        try {
            Statement database = con.createStatement();
            ResultSet rs = database.executeQuery(s);
            while (rs.next()){
                String name = rs.getString("Name");
                decks.add(new Deck(name));
            }
            System.out.println("Statement: "+s+" Has been executed.");
        } catch (SQLException e) {
            System.out.println("Can't do requested statement: "+s+ " Because: "+ e.getErrorCode() + e.getMessage());
        }
        return decks;
    }

    public ArrayList<Flashcard> returnCardsInDeck(String name) {
        ArrayList<Flashcard> cards = new ArrayList<>();
        String sql = "SELECT * FROM Flashcard WHERE Deck = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String ID = rs.getString("ID");
                    String deck = rs.getString("Deck");
                    String question = rs.getString("Question");
                    String answer = rs.getString("Answer");
                    String bonusInfo = rs.getString("BonusInfo");
                    byte[] imageData = rs.getBytes("ImageData"); // Fetch image data as byte array

                    cards.add(new Flashcard(ID, deck, question, answer, bonusInfo, imageData));
                }
                System.out.println("Cards fetched for deck: " + name);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching cards for deck: " + name + " - " + e.getMessage());
        }
        return cards;
    }

    public List<User> returnUsers() {
        List<User> users = new ArrayList<>();
        String s = "SELECT * FROM [User]";
        try {
            Statement database = con.createStatement();
            ResultSet rs = database.executeQuery(s);
            while (rs.next()){
                String name = rs.getString("Name");
                users.add(new User(name));
            }
            System.out.println("Statement: "+s+" Has been executed.");
        } catch (SQLException e) {
            System.out.println("Can't do requested statement: "+s+ " Because: "+ e.getErrorCode() + e.getMessage());
        }
        return users;
    }

    public void logFailedFlashcard(Flashcard flashcard, String errorMessage) {
        String logFileName = "failed_flashcards_log.txt";
        try (FileWriter writer = new FileWriter(logFileName, true)) {
            writer.write("Failed to add Flashcard: " + flashcard.getID() + "\n");
            writer.write("Error Message: " + errorMessage + "\n");
            writer.write("-------------------------------------------------\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public void updateUserAnswer(String updateSQL, String insertSQL, String checkRowSQL) {
        try {
            // Check if the row exists in the database
            PreparedStatement checkRowStmt = con.prepareStatement(checkRowSQL);
            ResultSet resultSet = checkRowStmt.executeQuery();

            if (resultSet.next()) {
                // The row exists, so execute the update statement
                PreparedStatement updateStmt = con.prepareStatement(updateSQL);
                updateStmt.executeUpdate();
                System.out.println(updateStmt);
            } else {
                // The row does not exist, so execute the insert statement
                PreparedStatement insertStmt = con.prepareStatement(insertSQL);
                insertStmt.executeUpdate();
                System.out.println(insertStmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public List<String> returnIrrelevantCards(String username) {
        List<String> irrelevantCardIds = new ArrayList<>();
        String s = "SELECT * FROM UserAnswer WHERE user_name = '"+username+"' AND Irrelevant = 'true'";
        try {
            Statement database = con.createStatement();
            ResultSet rs = database.executeQuery(s);
            while (rs.next()){
                String name = rs.getString("flashcard_id");
                irrelevantCardIds.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return irrelevantCardIds;
    }

    public HashMap<String, Timestamp> returnTimestampsForCards(String username) {
        HashMap<String, Timestamp> timestampCardIdMap = new HashMap<>();
        String s = "SELECT * FROM UserAnswer WHERE user_name = '"+username+"'";
        try {
            Statement database = con.createStatement();
            ResultSet rs = database.executeQuery(s);
            while (rs.next()){
                String cardId = rs.getString("flashcard_id");
                Timestamp timestamp = rs.getTimestamp("NextShowTime");
                timestampCardIdMap.put(cardId, timestamp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return timestampCardIdMap;
    }
}