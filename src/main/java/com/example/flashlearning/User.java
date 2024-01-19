package com.example.flashlearning;

import java.sql.Timestamp;
import java.util.*;

public class User {

    private String userName;
    private Deck deck;
    int currentIndex, initialQueuesize;
    private boolean isLastCard;
    private Flashcard nextCard;

    private Comparator<Flashcard> flashcardComparator = new Comparator<Flashcard>() {
        @Override
        public int compare(Flashcard o1, Flashcard o2) {
            Timestamp timestamp1 = o1.getTimestamp();
            Timestamp timestamp2 = o2.getTimestamp();

            if (timestamp1 == null && timestamp2 == null){
                return 0;
            } else if (timestamp1 == null) {
                return -1;
            } else if (timestamp2 == null) {
                return 1;
            } else {
                return timestamp1.compareTo(timestamp2);
            }
        }
    };

    private PriorityQueue<Flashcard> userQueue = new PriorityQueue<>(flashcardComparator);
    public void setQueue() {
        if (!this.userQueue.isEmpty()){
            this.userQueue.clear();
        }
        if (!this.deck.getFlashcards().isEmpty()) {
            userQueue.addAll(this.deck.getFlashcards());
            initialQueuesize = userQueue.size();
            isLastCard = false;
        }
    }


    public User(String userName) {
        this.userName = userName;
        currentIndex = 0;
        isLastCard = false;
    }

    public int getInitialQueuesize() {
        return initialQueuesize;
    }

    public PriorityQueue<Flashcard> getUserQueue() {
        return userQueue;
    }

    public void setNextCard(){
        if (!this.userQueue.isEmpty()) {
            nextCard = userQueue.poll();
        } else {
            isLastCard = true;
        }
    }

    public Flashcard getNextCard(){
        if (nextCard != null){
            return nextCard;
        } else {
            setNextCard();
            return nextCard;
        }
    }


    public boolean isLastCard() {
        return isLastCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return userName;
    }




    public void removeIrrelevantCards(List<String> irrelevantCardIds) {
        List<Flashcard> cardsToRemove = new ArrayList<>();
        for (Flashcard flashcard : this.deck.getFlashcards()) {
            for (String irrelevantCardId : irrelevantCardIds) {
                if (Objects.equals(flashcard.getID(), irrelevantCardId)){
                    cardsToRemove.add(flashcard);
                }
            }
        }
        if (!cardsToRemove.isEmpty()){
            for (Flashcard flashcard : cardsToRemove) {
                this.deck.getFlashcards().remove(flashcard);
            }
        }
    }


    public void setTimestamps(HashMap<String, Timestamp> timestampsAndIds) {
        for (Flashcard flashcard : deck.getFlashcards()) {
            if (timestampsAndIds.containsKey(flashcard.getID())){
                flashcard.setTimestamp(timestampsAndIds.get(flashcard.getID()));//Sætter timestampen hvis ID'et findes hashmappet.
            }
        }
    }


}
