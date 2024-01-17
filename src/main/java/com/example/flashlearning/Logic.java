package com.example.flashlearning;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logic {

    private DataAccesObject dao;
    private ArrayList<Deck> decks;
    private Deck selectedDeck;
    private List<User> users;
    private User selectedUser;

    public Logic() {
        this.dao = new DataAccesObject();
        update();
    }

    private void update() {
        this.decks = dao.returnDecks();
        for (Deck deck : decks) {
            deck.setFlashcards(dao.returnCardsInDeck(deck.getName()));
        }
        this.users = dao.returnUsers();
    }



    private byte[] readImageAsByteArray(String filepath) {
        try {
            return Files.readAllBytes(new File(filepath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void selectDeck() {
        Stage window = new Stage();
        window.setTitle("Select Deck");
        ListView<Deck> deckListView = new ListView<>();
        deckListView.getItems().addAll(decks);

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            this.selectedDeck = deckListView.getSelectionModel().getSelectedItem();
            System.out.println("Selected Deck: " + selectedDeck);
            selectedUser.setDeck(selectedDeck);
            irrelevantCardIds();
            timestampForUser();
            setUserQueue();
            window.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.close());

        HBox buttonBox = new HBox(15, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, deckListView, buttonBox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 350);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }

    private void setUserQueue() {
        if (selectedDeck != null) {
            this.selectedUser.setQueue();
        }
    }

    public Deck getSelectedDeck() {
        return selectedDeck;
    }

    public void importCollection() {
        ImportCollection ic = new ImportCollection();
        ic.srcFolderChooser();
        dao.addDeck(ic.getDeck());
        update();
    }

    public void selectUser() {
        //TODO if users is empty, go to create new user.
        Stage window = new Stage();
        window.setTitle("Select User");
        ListView<User> userListView = new ListView<>();
        userListView.getItems().addAll(users);

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            this.selectedUser = userListView.getSelectionModel().getSelectedItem();
            System.out.println("Selected User: " + selectedUser);
            window.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.close());

        HBox buttonBox = new HBox(15, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, userListView, buttonBox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 350);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }

    public void editUser() {
        //TODO lav vindue til redigering af brugere/sletning af brugere.
    }

    public void createUser(){
        //TODO set up create user window.
    }

    public void userAnswer(int a, User selectedUser, Flashcard nextCard){
        String update;
        String insert;
        String checkrow = "SELECT * FROM UserAnswer WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
        switch (a){
            case 1:
                update = "UPDATE UserAnswer SET NextShowTime = DATEADD(day, 4, GETDATE()) WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', DATEADD(day, 4, GETDATE()), 0)";
                dao.updateUserAnswer(update, insert, checkrow);
                System.out.println("correct");
                break;
            case 2:
                update = "UPDATE UserAnswer SET NextShowTime = DATEADD(MINUTE, 720, GETDATE()) WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', DATEADD(MINUTE, 720, GETDATE()), 0)";
                dao.updateUserAnswer(update, insert, checkrow);
                System.out.println("medium");
                break;
            case 3:
                update = "UPDATE UserAnswer SET NextShowTime = DATEADD(MINUTE, 30, GETDATE()) WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', DATEADD(MINUTE, 30, GETDATE()), 0)";
                dao.updateUserAnswer(update, insert, checkrow);
                System.out.println("hard");
                break;
            case 4:
                update = "UPDATE UserAnswer SET NextShowTime = DATEADD(MINUTE, 10, GETDATE()) WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', DATEADD(MINUTE, 10, GETDATE()), 0)";
                dao.updateUserAnswer(update, insert, checkrow);
                System.out.println("wrong");
                break;
            case 5:
                update = "UPDATE UserAnswer SET NextShowTime = GETDATE(), Irrelevant = 1 WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', GETDATE(), 1)";
                dao.updateUserAnswer(update, insert, checkrow);
                //irrelevantCardIds();
                System.out.println("irrelevant");
                break;
        }
    }

    public void irrelevantCardIds(){
        List<String> irrelevantCardIds = dao.returnIrrelevantCards(this.selectedUser.getUserName());
        this.selectedUser.removeIrrelevantCards(irrelevantCardIds);

    }

    public void timestampForUser(){
        HashMap<String, Timestamp> timestampsAndIds = dao.returnTimestampsForCards(this.selectedUser.getUserName());
        this.selectedUser.setTimestamps(timestampsAndIds);
    }


}
