package com.example.flashlearning;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logic {

    private DataAccesObject dao;

    public Logic() {
        this.dao = new DataAccesObject();
    }



    public void addCard(String cleanDatum) {
        String[] cardData = cleanDatum.split("\t");
        String ID = cardData[0];
        String deckName = cardData[2];
        String filepath = cardData[3];
        String note = " ";
        for (int i = 4; i < cardData.length; i++) {
            note = note.concat(cardData[i]);
            note = note.concat(", ");
        }
        dao.addCard(new Flashcard(ID, deckName, filepath, note));

    }
}


        /*
        col. 1 = id
        col. 2 = Category
        col. 3 = Deckname
        col. 4 = img name
        col. 5 = Artist
        col. 6 = title
        col. 7 = n/a
        col. 8 = year
        col. 9 = movement
        col. 10 = n/a
        col. 11 = n/a
        col. 12 = n/a
        col. 13 = note/comment
        */
