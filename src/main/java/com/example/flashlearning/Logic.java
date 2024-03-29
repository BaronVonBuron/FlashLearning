package com.example.flashlearning;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Logic {

    private DataAccesObject dao;
    private ArrayList<Deck> decks;
    private Deck selectedDeck;
    private List<User> users;
    private User selectedUser;
    UserStatistic userStatistic;

    public Logic() {
        this.dao = new DataAccesObject();
        userStatistic = new UserStatistic(this);
        update();
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    private void update() {
        this.decks = dao.returnDecks();
        for (Deck deck : decks) {
            deck.setFlashcards(dao.returnCardsInDeck(deck.getName()));
        }
        this.users = dao.returnUsers();
    }


    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
        this.userStatistic.setUser(selectedUser);
    }

    public void selectDeck() {
        Stage window = new Stage();
        window.setTitle("Select Deck");
        ListView<Deck> deckListView = new ListView<>();
        deckListView.getItems().addAll(decks);

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            if (deckListView.getSelectionModel().getSelectedItem() != null) {
                this.selectedDeck = deckListView.getSelectionModel().getSelectedItem();
                System.out.println("Selected Deck: " + selectedDeck);
                selectedUser.setDeck(selectedDeck);
                irrelevantCardIds();
                timestampForUser();
                setUserQueue();
                setUserStatistics();
                window.close();
            }
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
        if (ic.getDeck() != null) {
            dao.addDeck(ic.getDeck());
        }
        update();
    }


    public void selectUser() {
        UserMenuController userMenuController = new UserMenuController(this);
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user-menu.fxml"));
        fxmlLoader.setController(userMenuController);
        try {
            Pane root = fxmlLoader.load();
            Scene newScene = new Scene(root);
            newStage.setScene(newScene);
            newStage.setTitle("Bruger Menu");
            newStage.setResizable(false);
            newStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editUser(User user, String text) {
        dao.updateSomething("UPDATE [User] SET Name = '"+text+"' WHERE Name = ('"+user.getUserName()+"');");
    }

    public void createUser(String text){
        dao.updateSomething("INSERT INTO [User] (Name) VALUES ('"+text+"');");
    }

    public void deleteUser(User user){
        dao.updateSomething("DELETE FROM UserAnswer WHERE user_name = ('"+user.getUserName()+"')");
        dao.updateSomething("DELETE FROM [User] WHERE Name = ('"+user.getUserName()+"')");
    }

    public void userAnswer(int a, User selectedUser, Flashcard nextCard){
        String update;
        String insert;
        String checkrow = "SELECT * FROM UserAnswer WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
        switch (a){
            case 1:
                update = "UPDATE UserAnswer SET NextShowTime = DATEADD(day, 4, GETDATE()), Statistic = 1 WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant, Statistic) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', DATEADD(day, 4, GETDATE()), 0, 1)";
                dao.updateUserAnswer(update, insert, checkrow);
                System.out.println("correct");
                break;
            case 2:
                update = "UPDATE UserAnswer SET NextShowTime = DATEADD(MINUTE, 720, GETDATE()), Statistic = 2 WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant, Statistic) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', DATEADD(MINUTE, 720, GETDATE()), 0, 2)";
                dao.updateUserAnswer(update, insert, checkrow);
                System.out.println("medium");
                break;
            case 3:
                update = "UPDATE UserAnswer SET NextShowTime = DATEADD(MINUTE, 30, GETDATE()), Statistic = 3 WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant, Statistic) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', DATEADD(MINUTE, 30, GETDATE()), 0, 3)";
                dao.updateUserAnswer(update, insert, checkrow);
                System.out.println("hard");
                break;
            case 4:
                update = "UPDATE UserAnswer SET NextShowTime = DATEADD(MINUTE, 10, GETDATE()), Statistic = 4 WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant, Statistic) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', DATEADD(MINUTE, 10, GETDATE()), 0, 4)";
                dao.updateUserAnswer(update, insert, checkrow);
                System.out.println("wrong");
                break;
            case 5:
                update = "UPDATE UserAnswer SET NextShowTime = GETDATE(), Irrelevant = 1, Statistic = 5 WHERE user_name = '" + selectedUser.getUserName() + "' AND flashcard_id = '" + nextCard.getID() + "'";
                insert = "INSERT INTO UserAnswer (user_name, flashcard_id, NextShowTime, Irrelevant, Statistic) VALUES ('" + selectedUser.getUserName() + "', '" + nextCard.getID() + "', GETDATE(), 1, 5)";
                dao.updateUserAnswer(update, insert, checkrow);
                System.out.println("irrelevant");
                break;
        }
        setUserStatistics();
    }

    public void irrelevantCardIds(){
        List<String> irrelevantCardIds = dao.returnIrrelevantCards(this.selectedUser.getUserName());
        this.selectedUser.removeIrrelevantCards(irrelevantCardIds);
    }

    public void timestampForUser(){
        HashMap<String, Timestamp> timestampsAndIds = dao.returnTimestampsForCards(this.selectedUser.getUserName());
        this.selectedUser.setTimestamps(timestampsAndIds);
    }

    public List<User> getUsers() {
        update();
        return users;
    }

    public void setUserStatistics(){
        if (this.selectedUser != null && this.userStatistic != null) {
            dao.getUserStatistics(this.selectedUser, this.userStatistic);
        }
    }

    public UserStatistic getUserStatistic() {
        return userStatistic;
    }

    public void deleteUserAnswers(String userName) {
        dao.deleteUserAnswers(userName);
        setUserStatistics();
    }

    public void newCard() {
        AddEditFlashcardMenuController addEditFlashcardMenuController = new AddEditFlashcardMenuController(this);
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-edit-flashcard-menu.fxml"));
        fxmlLoader.setController(addEditFlashcardMenuController);
        try {
            Pane root = fxmlLoader.load();
            Scene newScene = new Scene(root);
            newStage.setScene(newScene);
            newStage.setTitle("Bruger Menu");
            newStage.setResizable(false);
            newStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFlashcard(Flashcard flashcard) {
        boolean deckexists = false;
        for (Deck deck : this.decks) {
            if (Objects.equals(deck.getName(), flashcard.getDeck())){
                deckexists = true;
            }
        }
        if (deckexists){
            dao.addCard(flashcard);
        } else {
            Deck d = new Deck(flashcard.getDeck());
            d.getFlashcards().add(flashcard);
            dao.addDeck(d);
        }

        update();
    }
}
