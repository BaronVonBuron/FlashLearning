package com.example.flashlearning;

public class Flashcard {

    private String ID, category, deckName, imagePath, artist, title, year, movement, note;
    private byte[] imageData; // Field to store the image data as a byte array

    // Constructor with imageData
    public Flashcard(String ID, String deckName, String imagePath, String note, byte[] imageData) {
        this.ID = ID;
        this.deckName = deckName;
        this.imagePath = imagePath;
        this.note = note;
        this.imageData = imageData; // Assigning the imageData
    }

    // Getters and setters for the fields
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // New getter for imageData
    public byte[] getImageData() {
        return imageData;
    }

    // New setter for imageData
    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
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
                ", year='" + year + '\'' +
                ", movement='" + movement + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}