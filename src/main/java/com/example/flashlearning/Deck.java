package com.example.flashlearning;

import java.util.ArrayList;

public class Deck {

    private String name;
    private int numberOfCards;
    private ArrayList<Flashcard> flashcards = new ArrayList<>();

    public Deck(String name) {
        this.name = name;
    }

    public void setFlashcards(ArrayList<Flashcard> fc){
        this.flashcards = fc;
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
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public void addCard(String id, String deckName, String filepath, String note) {
        this.flashcards.add(new Flashcard(id, deckName, filepath, note));
    }

    @Override
    public String toString() {
        return "Deck{" +
                "name='" + name + '\'' +
                ", numberOfCards=" + numberOfCards +
                '}';
    }


}
