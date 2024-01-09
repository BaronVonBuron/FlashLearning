package com.example.flashlearning;

import java.sql.*;
import java.util.ArrayList;
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
        String s = "INSERT INTO Flashcard (ID, Deck, Filepath,  Note) values ('"+flashcard.getID()+"', '"+flashcard.getDeckName()+"', '"+flashcard.getImagePath()+"', '"+flashcard.getNote()+"');";
        updateSomething(s);
    }

    public ArrayList<Deck> returnDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        String s = "SELECT * FROM Flashcard";
        try {
            Statement database = con.createStatement();
            ResultSet rs = database.executeQuery(s);
            while (rs.next()){
                String name = rs.getString("Deck");
                decks.add(new Deck(name));
            }
            System.out.println("Statement: "+s+" Has been executed.");
        } catch (SQLException e) {
            System.out.println("Can't do requested statement: "+s+ " Because: "+ e.getErrorCode() + e.getMessage());
        }
        return decks;
    }
}