package com.example.flashlearning;

import javafx.scene.image.Image;

import java.util.UUID;

public class Flashcard {

    private Image image;
    private String category, answer;
    private int difficulty;
    private UUID uuid;

    public Flashcard(Image image, String category, String answer) {//image, arraylist<string>, ikke answer
        this.image = image;
        this.category = category;
        this.answer = answer;
        uuid = UUID.randomUUID();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "image=" + image +
                ", category='" + category + '\'' +
                ", answer='" + answer + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }
}
