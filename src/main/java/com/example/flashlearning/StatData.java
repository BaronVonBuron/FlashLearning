package com.example.flashlearning;
//Denne klasse er rent og skært til at smide data i statisti-tableview'et.
public class StatData {
    private String c1;
    private int c2;

    public StatData(String c1, int c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public int getC2() {
        return c2;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }
}