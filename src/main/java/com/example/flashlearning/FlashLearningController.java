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



    public void initialize(){
        backgroundStartup();
        imageViewShowImage();
    }

    private void backgroundStartup() {
        this.bgImage = new Image(getClass().getResourceAsStream("/BackgroundFlashcardLearning.png"));
        BackgroundImage bg = new BackgroundImage(bgImage,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        this.LearningPane.setBackground(new Background(bg));
    }

    private void imageViewShowImage() {
        if (flashcardIsShowing && selectedDeck != null) {
            Image img = new Image(getClass().getResourceAsStream("/greatartists/"+selectedDeck.getFlashcards().getFirst().getImagePath()));
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
            //srcFileChooser();
            srcFolderChooser();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    public void srcFolderChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedDir = directoryChooser.showDialog(null);

        if (selectedDir != null) {
            File[] filesInDir = selectedDir.listFiles();
            HashMap<String, String> imageDetails = new HashMap<>();
            HashMap<String, byte[]> imageData = new HashMap<>(); // To store image data as byte arrays

            if (filesInDir != null) {
                for (File file : filesInDir) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                        // Process .txt file
                        ArrayList<String> cleanedData = dataCleaner.extractData(file.getPath());
                        for (String line : cleanedData) {
                            String[] columns = line.split("\t");
                            if (columns.length > 3) {
                                String imageName = columns[3];
                                imageDetails.put(imageName, line);
                            }
                        }
                    }
                }

                for (File file : filesInDir) {
                    if (file.isFile() && isImageFile(file.getName())) {
                        if (imageDetails.containsKey(file.getName())) {
                            // Read image file as byte array
                            try {
                                byte[] fileContent = Files.readAllBytes(file.toPath());
                                imageData.put(file.getName(), fileContent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                // Now, call the addDeck method
                logic.addDeck(imageDetails, imageData, selectedDir.getAbsolutePath());
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

    public void selectDeckOptionSelected(ActionEvent actionEvent) {
        logic.selectDeck();
        this.selectedDeck = logic.getSelectedDeck();
        if (!flashcardIsShowing){
            flashcardIsShowing = true;
        }
        imageViewShowImage();
    }
}