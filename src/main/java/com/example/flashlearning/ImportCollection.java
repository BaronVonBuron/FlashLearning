package com.example.flashlearning;

import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImportCollection {
    private Deck deck;

    DataCleaner datacleaner = new DataCleaner();


    public void srcFolderChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedDir = directoryChooser.showDialog(null);//directory chooser åbner en hel mappe.

        if (selectedDir != null) {
            File[] filesInDir = selectedDir.listFiles();
            HashMap<String, String> imageDetails = new HashMap<>();//hashmap til at kæde billedstier sammen med billeddata.
            HashMap<String, byte[]> imageData = new HashMap<>(); //hashmap til at kæde billedstier sammen med billeder i bytearray.
            String deck = "";
            if (filesInDir != null) {//ser om der er filer i mappen
                for (File file : filesInDir) {//kører igennem hver fil i mappen
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {//tjekker om den nuværende fil i løkken er en txt fil.
                        ArrayList<String> cleanedData = datacleaner.extractData(file.getPath());//kører filen igennem datacleaner class
                        for (String line : cleanedData) {//kører hver linje igennem i txt filen
                            String[] columns = line.split("\t");//splitter hver linje ind i kolonner, adskilt med tab.
                            if (columns.length > 3) {// check der bare sørger for at den ikke kan lave en nullpointer, skulle der være en halv linje
                                String imageName = columns[3];//laver en streng fra 4. kolonne, der indeholder billedenavnet.
                                String[] deckAndQuestion = columns[2].split("::");
                                String id = columns[0];
                                String question = deckAndQuestion[1]+"?";
                                deck = deckAndQuestion[0];
                                String answer = columns[4];
                                answer = answer.replace("<div>", "");
                                answer = answer.replace("</div>", "");
                                answer = answer.replace("&nbsp;", "");
                                String bonusInfo = "";
                                for (int i = 5; i < columns.length; i++) {
                                    bonusInfo = bonusInfo.concat("\t");
                                    bonusInfo = bonusInfo.concat(columns[i]);
                                }
                                String newLine = id+"\t"+deck+"\t"+question+"\t"+answer+bonusInfo;
                                imageDetails.put(imageName, newLine);//kæder billedenavnet sammen med resten af linjen i et hashmap.
                            }
                        }
                    }
                }

                for (File file : filesInDir) {//looper alle filerne igennem
                    if (file.isFile() && isImageFile(file.getName())) {//ser om filen er en billedefil.
                        if (imageDetails.containsKey(file.getName())) {//ser om det hashmap der blev lavet før, indeholder en nøgle der passer med navnet på billedefilen.
                            try {
                                byte[] fileContent = Files.readAllBytes(file.toPath());//laver billedefilen om til et bytearray
                                imageData.put(file.getName(), fileContent);//kæder bytearray sammen med billedenavn.
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                addDeck(imageDetails, imageData, selectedDir.getAbsolutePath(), deck);
            }
        } else {
            System.out.println("Folder selection cancelled.");
        }
    }

    private void addDeck(HashMap<String, String> imageDetails, HashMap<String, byte[]> imageData, String absolutePath, String deckName) {
        this.deck = new Deck(deckName);
        for (Map.Entry<String, String> entry : imageDetails.entrySet()) {
            String imageFileName = entry.getKey();
            String cardData = entry.getValue();
            String[] cd = cardData.split("\t");

            if (cd.length < 4) continue;//hvis der er mindst 4 kolonner i dataen til billedet, så fortsæt.
            String ID = cd[0];//billede ID
            String deckNAME = cd[1];
            String question = cd[2];
            String answer = cd[3];
            StringBuilder note = new StringBuilder();//bygger de adskilte værdier sammen igen.

            for (int i = 4; i < cd.length; i++) {
                note.append(cd[i]);
                if (i < cd.length - 1) {
                    note.append(", ");
                }
            }
            byte[] imageBytes = imageData.get(imageFileName);
            this.deck.addCard(ID, deckNAME, question, answer, note.toString(), imageBytes);
        }
    }

    public Deck getDeck() {
        return deck;
    }

    private boolean isImageFile(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        return lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".png")
                || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".bmp")
                || lowerCaseFileName.endsWith(".gif");
    }
}
