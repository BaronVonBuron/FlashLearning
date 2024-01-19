package com.example.flashlearning;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataCleaner {


    public ArrayList<String> extractData(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            // Reading the file content
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            String pattern = "src=\"\"([^\"]*)\"\""; // et mønster der finder billedenavnet iblandt alle specialtegnene.
            Pattern imgSrcPattern = Pattern.compile(pattern);

            String[] rows;
            rows = content.split("\n");

            for (String row : rows) {
                if (row.contains("'")){
                    row = row.replace("'", "''");
                }
                if (!row.startsWith("#")) { // Skipper de første kommentar linjer
                    String[] columns = row.split("\t");

                    if (columns.length > 3) {
                        Matcher matcher = imgSrcPattern.matcher(columns[3]);
                        String imgSrc = "";
                        if (matcher.find()) {
                            imgSrc = matcher.group(1);
                        }
                        columns[3] = imgSrc;
                    }
                    lines.add(String.join("\t", columns));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            Jsoup.clean(line, Safelist.none());
        }
        return lines;
    }





}
