package com.example.flashlearning;

import javafx.scene.image.Image;

import java.util.UUID;

public class Flashcard {

    private String ID, category, deckName, imagePath, artist, title, empty7, year, movement, empty10, empty11, empty12, note;

    public Flashcard(String ID, String deckName, String imagePath, String note) {
        this.ID = ID;
        this.deckName = deckName;
        this.imagePath = imagePath;
        this.note = note;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmpty7() {
        return empty7;
    }

    public void setEmpty7(String empty7) {
        this.empty7 = empty7;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public String getEmpty10() {
        return empty10;
    }

    public void setEmpty10(String empty10) {
        this.empty10 = empty10;
    }

    public String getEmpty11() {
        return empty11;
    }

    public void setEmpty11(String empty11) {
        this.empty11 = empty11;
    }

    public String getEmpty12() {
        return empty12;
    }

    public void setEmpty12(String empty12) {
        this.empty12 = empty12;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "ID='" + ID + '\'' +
                ", category='" + category + '\'' +
                ", deckName='" + deckName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", empty7='" + empty7 + '\'' +
                ", year='" + year + '\'' +
                ", movement='" + movement + '\'' +
                ", empty10='" + empty10 + '\'' +
                ", empty11='" + empty11 + '\'' +
                ", empty12='" + empty12 + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}