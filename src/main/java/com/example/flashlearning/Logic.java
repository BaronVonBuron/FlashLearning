package com.example.flashlearning;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Logic {

    private DataAccesObject dao;
    private ArrayList<Deck> decks;

    public Logic() {
        this.dao = new DataAccesObject();
        update();
    }

    private void update() {
        this.decks = dao.returnDecks();
    }



    public void addDeck(ArrayList<String> cardData){
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
    }

    public void addCard(String cleanDatum) {
        String[] cardData = cleanDatum.split("\t");
        String ID = cardData[0];
        String deckName = cardData[2];
        String filepath = cardData[3];
        String note = " ";
        for (int i = 4; i < cardData.length; i++) {
            note = note.concat(cardData[i]);
            note = note.concat(", ");
        }
        dao.addCard(new Flashcard(ID, deckName, filepath, note));
    }

    public void selectDeck() {
        Stage window = new Stage();
        window.setTitle("Select Deck");
        ListView<String> deckListView = new ListView<>();
        deckListView.getItems().addAll("Deck 1", "Deck 2", "Deck 3"); // TODO get the actual decks from list of decks.

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            // Handle OK button action here (e.g., get selected deck)
            String selectedDeck = deckListView.getSelectionModel().getSelectedItem();
            System.out.println("Selected Deck: " + selectedDeck);
            window.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.close());

        HBox buttonBox = new HBox(15, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, deckListView, buttonBox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 250); // Adjust size as needed
        window.setScene(scene);
        window.showAndWait();
    }
}
