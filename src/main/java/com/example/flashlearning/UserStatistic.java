package com.example.flashlearning;

import java.util.List;

public class UserStatistic {

    private int wrong, hard, easy, correct, irrelevant;
    private User user;
    private Deck deck;
    private Logic logic;

    public UserStatistic(Logic logic) {
        this.logic = logic;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getHard() {
        return hard;
    }

    public void setHard(int hard) {
        this.hard = hard;
    }

    public int getEasy() {
        return easy;
    }

    public void setEasy(int easy) {
        this.easy = easy;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIrrelevant() {
        return irrelevant;
    }

    public void setIrrelevant(int irrelevant) {
        this.irrelevant = irrelevant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void clearData(){
        this.correct = 0;
        this.easy = 0;
        this.hard = 0;
        this.wrong = 0;
        this.irrelevant = 0;
    }

    public void setStats(List<Integer> stats) {
        if (!stats.isEmpty()){
            clearData();
            for (Integer stat : stats) {
                switch (stat){
                    case 1:
                        correct++;
                        break;
                    case 2:
                        easy++;
                        break;
                    case 3:
                        hard++;
                        break;
                    case 4:
                        wrong++;
                        break;
                    case 5:
                        irrelevant++;
                        break;
                }
            }
        } else {
            clearData();
        }
    }
}
