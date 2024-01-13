package com.example.flashlearning;

import java.sql.*;
import java.util.ArrayList;


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
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
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
}