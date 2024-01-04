package com.example.flashlearning;

import javafx.scene.image.Image;

public class Flashcard {

    private Image image;
    private String category, answer;
    private int difficulty;

    public Flashcard(Image image, String category, String answer) {
        this.image = image;
        this.category = category;
        this.answer = answer;
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
