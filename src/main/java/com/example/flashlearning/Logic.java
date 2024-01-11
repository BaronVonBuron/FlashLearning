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



    /*public void addDeck(ArrayList<String> cardData){
        String[] cards = cardData.getFirst().split("\t");
        Deck deck = new Deck(cards[2]);
        for (String cardDatum : cardData) {
            String[] cd = cardDatum.split("\t");
            String ID = cd[0];
            String deckName = cd[2];
            String filepath = cd[3];
            String note = " ";
            for (int i = 4; i < cards.length; i++) {
                note = note.concat(cd[i]);
                note = note.concat(", ");
            }
            deck.addCard(ID, deckName, filepath, note);
        }
        dao.addDeck(deck);
    }*/

    public void addDeck(HashMap<String, String> imageDetails, HashMap<String, byte[]> imageData, String directoryPath) {
        // Process each entry in the imageDetails HashMap
        for (Map.Entry<String, String> entry : imageDetails.entrySet()) {
            String imageFileName = entry.getKey();
            String cardData = entry.getValue();
            String[] cd = cardData.split("\t");

            if (cd.length < 4) continue;

            String ID = cd[0];
            String deckName = cd[2];
            String filepath = directoryPath + File.separator + imageFileName;
            StringBuilder note = new StringBuilder();

            for (int i = 4; i < cd.length; i++) {
                note.append(cd[i]);
                if (i < cd.length - 1) {
                    note.append(", ");
                }
            }

            byte[] imageBytes = imageData.get(imageFileName);

            // Assuming Deck is a class that you can instantiate and add cards to
            Deck deck = new Deck(deckName);
            deck.addCard(ID, deckName, filepath, note.toString(), imageBytes);

            // Assuming dao is an object through which you add the deck to the database
            dao.addDeck(deck);
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

    public void addCard(String cleanDatum, byte[] imageData) {
        String[] cardData = cleanDatum.split("\t");
        String ID = cardData[0];
        String deckName = cardData[2];
        String filepath = cardData[3];
        StringBuilder note = new StringBuilder(" ");

        for (int i = 4; i < cardData.length; i++) {
            note.append(cardData[i]);
            if (i < cardData.length - 1) {
                note.append(", ");
            }
        }

        // Passing the image byte array to the Flashcard constructor
        dao.addCard(new Flashcard(ID, deckName, filepath, note.toString(), imageData));
    }

    public void selectDeck() {
        Stage window = new Stage();
        window.setTitle("Select Deck");
        ListView<Deck> deckListView = new ListView<>();
        ObservableList<Deck> observableDecks = FXCollections.observableList(decks);
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
}
