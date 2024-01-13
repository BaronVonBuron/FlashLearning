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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Logic {

    private DataAccesObject dao;
    private ArrayList<Deck> decks;
    private Deck selectedDeck;

    public Logic() {
        this.dao = new DataAccesObject();
        update();
    }

    private void update() {
        this.decks = dao.returnDecks();
        for (Deck deck : decks) {
            deck.setFlashcards(dao.returnCardsInDeck(deck.getName()));
        }
    }



    private byte[] readImageAsByteArray(String filepath) {
        try {
            return Files.readAllBytes(new File(filepath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null; // or handle this more appropriately
        }
    }


    public void selectDeck() {
        Stage window = new Stage();
        window.setTitle("Select Deck");
        ListView<Deck> deckListView = new ListView<>();
        deckListView.getItems().addAll(decks); // TODO get the actual decks from list of decks.

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            this.selectedDeck = deckListView.getSelectionModel().getSelectedItem();
            System.out.println("Selected Deck: " + selectedDeck);
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
        //TODO lav user vindue, med de forskellige brugere.
        //TODO lav field med valgte bruger, som controller skal hente. Ved opstart af programmet skal den sidste valgte bruger stadig være valgt.
    }

    public void editUser() {
        //TODO lav vindue til redigering af brugere/sletning af brugere.
    }
}
