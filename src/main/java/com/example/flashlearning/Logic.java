package com.example.flashlearning;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logic {

    private DataAccesObject dao;

    public Logic() {
        this.dao = new DataAccesObject();
    }


    public void deckImported(File srcFile, File noteFile) {
        try {
            Scanner s = new Scanner(srcFile);
            String imageName;
            ArrayList<String> imageNames = new ArrayList<>();
            while (s.hasNext()) {
                String[] tokens = s.nextLine().split("\\t");
                for (String token : tokens) {
                    String pattern = "src=\"\"([^\"]*)\"\"";
                    Pattern r = Pattern.compile(pattern);
                    Matcher m = r.matcher(token);

                    if (m.find()) {
                        imageName = m.group(1);
                        imageNames.add(imageName);
                    }
                }
            }
            System.out.println(imageNames);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
