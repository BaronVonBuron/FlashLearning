package com.example.flashlearning;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class FlashLearningController {

    private final Logic logic = new Logic();
    private final DataCleaner dataCleaner = new DataCleaner();
    private boolean flashcardIsShowing;
    private Deck selectedDeck;
    public ImageView MainImageView;
    private Image bgImage;
    public AnchorPane LearningPane;
    public Button ShowAnswerButton, IrrelevantButton, EasyButton, WrongButton, HardButton, MediumButton;
    public TextArea ImageTextArea;
    public MenuBar MenuBar;
    public MenuItem menuFileImportOption;



    public void initialize() throws IOException {
        backgroundStartup();
        imageViewShowImage();
    }

    private void backgroundStartup() {
        this.bgImage = new Image(getClass().getResourceAsStream("/BackgroundFlashcardLearning.png"));
        BackgroundImage bg = new BackgroundImage(bgImage,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        this.LearningPane.setBackground(new Background(bg));
    }

    private void imageViewShowImage() throws IOException {
        if (flashcardIsShowing && selectedDeck != null) {



            Image img = ImageConverter.byteArrayToFXImage(selectedDeck.getFlashcards().getFirst().getImageData());
            if (img != null) {
                MainImageView.setImage(img);
                MainImageView.setFitWidth(800);
                MainImageView.setFitHeight(607);
                MainImageView.setPreserveRatio(true);

                //Nedenstående centrerer billedet på x aksen.
                double wc = img.getWidth() / MainImageView.getFitWidth();
                double hc = img.getHeight() / MainImageView.getFitHeight();
                if (wc >= hc) {
                    MainImageView.setX((LearningPane.getPrefWidth() / 2) - (MainImageView.getFitWidth()) / 2);
                } else {
                    MainImageView.setX((LearningPane.getPrefWidth() / 2) - (img.getWidth() * (hc + 1)) / 2);
                }
                MainImageView.setY(45);
            }
        }
    }




    public void showAnswerButtonPressed(ActionEvent actionEvent) {
        //TODO get the text from the card.
    }

    public void mediumButtonPressed(ActionEvent actionEvent) {
        //TODO give the card a difficulty score of 2
    }

    public void hardButtonPressed(ActionEvent actionEvent) {
        //TODO give the card a difficulty score of 3

    }

    public void wrongButtonPressed(ActionEvent actionEvent) {
        //TODO give the card a difficulty score of 4

    }

    public void easyButtonPressed(ActionEvent actionEvent) {
        //TODO give the card a difficulty score of 1

    }

    public void irrelevantButtonPressed(ActionEvent actionEvent) {
        //TODO make the card irrelevant for the user

    }


    public void importOptionSelected() throws Exception {
        try {
            srcFolderChooser();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    public void srcFolderChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        //File initialDirectory = new File("src/main/java/resources/");//åbner resources mappen først.
        //directoryChooser.setInitialDirectory(initialDirectory);//TODO giver fejl: java.lang.IllegalArgumentException: Folder parameter must be a valid folder
        File selectedDir = directoryChooser.showDialog(null);//directory chooser åbner en hel mappe.

        if (selectedDir != null) {
            File[] filesInDir = selectedDir.listFiles();
            HashMap<String, String> imageDetails = new HashMap<>();//hashmap til at kæde billedstier sammen med billeddata.
            HashMap<String, byte[]> imageData = new HashMap<>(); //hashmap til at kæde billedstier sammen med billeder i bytearray.
            String deckName = "";
            if (filesInDir != null) {//ser om der er filer i mappen
                for (File file : filesInDir) {//kører igennem hver fil i mappen
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {//tjekker om den nuværende fil i løkken er en txt fil.
                        ArrayList<String> cleanedData = dataCleaner.extractData(file.getPath());//kører filen igennem datacleaner class
                        for (String line : cleanedData) {//kører hver linje igennem i txt filen
                            String[] columns = line.split("\t");//splitter hver linje ind i kolonner, adskilt med tab.
                            if (columns.length > 3) {//random check der bare sørger for at den ikke kan lave en nullpointer, skulle der være en halv linje
                                String imageName = columns[3];//laver en streng fra 4. kolonne, der indeholder billedenavnet.
                                imageDetails.put(imageName, line);//kæder billedenavnet sammen med resten af linjen i et hashmap.
                                deckName = columns[2];//Sætter navnet på deck'et.
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
                logic.addDeck(imageDetails, imageData, selectedDir.getAbsolutePath(), deckName);//caller addDeck i logic.
            }
        } else {
            System.out.println("Folder selection cancelled.");
        }
    }

    private boolean isImageFile(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        return lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".png")
                || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".bmp")
                || lowerCaseFileName.endsWith(".gif");
    }

    public void selectDeckOptionSelected(ActionEvent actionEvent) throws IOException {
        logic.selectDeck();
        this.selectedDeck = logic.getSelectedDeck();
        if (!flashcardIsShowing){
            flashcardIsShowing = true;
        }
        imageViewShowImage();
    }
}