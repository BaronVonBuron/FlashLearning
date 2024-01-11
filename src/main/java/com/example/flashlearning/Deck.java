package com.example.flashlearning;

import java.util.ArrayList;

public class Deck {

    private String name;
    private ArrayList<Flashcard> flashcards = new ArrayList<>();
    private int numberOfCards;

    public Deck(String name) {
        this.name = name;
    }

    public void setFlashcards(ArrayList<Flashcard> fc){
        this.flashcards = fc;
        setNumberOfCards();
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfCards() {
        if (flashcards.isEmpty()){
            return 0;
        }else return flashcards.size();
    }

    public void setNumberOfCards() {
        this.numberOfCards = flashcards.size();
    }

    public void addCard(String id, String deckName, String imagePath, String note, byte[] imageData) {
        this.flashcards.add(new Flashcard(id, deckName, imagePath, note, imageData));
    }

    @Override
    public String toString() {
        return name+" - Cards: "+numberOfCards;
    }


}
