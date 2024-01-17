package com.example.flashlearning;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataCleaner {


    public ArrayList<String> extractData(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            // Reading the file content
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

            // Regex pattern to match <img src="image.jpg" />
            String pattern = "src=\"\"([^\"]*)\"\"";
            Pattern imgSrcPattern = Pattern.compile(pattern);

            // Splitting the content into lines
            String[] rows = new String[13];
            rows = content.split("\n");

            for (String row : rows) {
                if (row.contains("'")){
                    row = row.replace("'", "''");
                }
                if (!row.startsWith("#")) { // Skip comment lines
                    // Splitting each line into columns
                    String[] columns = row.split("\t"); //TODO make it clean up the fucking html tags in the stupid file. fuck.

                    if (columns.length > 3) {
                        Matcher matcher = imgSrcPattern.matcher(columns[3]);
                        String imgSrc = "";
                        if (matcher.find()) {
                            imgSrc = matcher.group(1);
                        }

                        columns[3] = imgSrc;
                    }

                    // Joining the columns back into a single string and adding it to the list
                    lines.add(String.join("\t", columns));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            //Jsoup.clean(line, Safelist.none());
        }
        return lines;
    }





}
