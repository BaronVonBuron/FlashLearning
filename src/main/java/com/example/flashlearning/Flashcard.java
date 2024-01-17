package com.example.flashlearning;

import java.sql.Timestamp;

public class Flashcard {

    private String ID, deck, question, answer, bonusinfo;
    private Timestamp timestamp;
    private byte[] imageData; // Field to store the image data as a byte array

    public Flashcard(String ID, String deck, String question, String answer, String bonusinfo, byte[] imageData) {
        this.ID = ID;
        this.deck = deck;
        this.question = question;
        this.answer = answer;
        this.bonusinfo = bonusinfo;
        this.imageData = imageData;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getBonusinfo() {
        return bonusinfo;
    }

    public void setBonusinfo(String bonusinfo) {
        this.bonusinfo = bonusinfo;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "ID='" + ID + '\'' +
                ", deck='" + deck + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", bonusinfo='" + bonusinfo + '\'' +
                '}';
    }
}