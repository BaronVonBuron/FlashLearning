package com.example.flashlearning;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class FlashLearningController {

    private final Logic logic = new Logic();
    private final DataCleaner dataCleaner = new DataCleaner();
    public ImageView MainImageView;
    private Image bgImage;
    public AnchorPane LearningPane;
    public Button ShowAnswerButton, IrrelevantButton, EasyButton, WrongButton, HardButton, MediumButton;
    public TextArea ImageTextArea;
    public MenuBar MenuBar;
    public MenuItem menuFileImportOption;



    public void initialize(){
        backgroundStartup();
        imageViewStartup();
    }

    private void backgroundStartup() {
        this.bgImage = new Image(getClass().getResourceAsStream("/BackgroundFlashcardLearning.png"));
        BackgroundImage bg = new BackgroundImage(bgImage,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        this.LearningPane.setBackground(new Background(bg));
    }

    private void imageViewStartup() {
        Image img = new Image(getClass().getResourceAsStream("/greatartists/2014-08-19_035837.jpg"));
        if (img != null) {
            MainImageView.setImage(img);
            MainImageView.setFitWidth(800);
            MainImageView.setFitHeight(607);
            MainImageView.setPreserveRatio(true);

            //Nedenstående centrerer billedet på x aksen.
            double wc = img.getWidth() / MainImageView.getFitWidth();
            double hc = img.getHeight() / MainImageView.getFitHeight();
            if (wc >= hc){
                MainImageView.setX((LearningPane.getPrefWidth()/2) - (MainImageView.getFitWidth())/2);
            } else {
                MainImageView.setX((LearningPane.getPrefWidth()/2) - (img.getWidth()*(hc+1))/2);
            }
            MainImageView.setY(45);
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
            srcFileChooser();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public void srcFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select text file from collection");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File initialDir = new File("src/main/resources");
        if (initialDir.exists()) {
            fileChooser.setInitialDirectory(initialDir);
        }
        File srcFile = fileChooser.showOpenDialog(null);

        if (srcFile != null) {
            if (!srcFile.getName().toLowerCase().endsWith(".txt")) {//  Ib >:-C
                System.out.println("Error: Please select a .txt file.");
            } else {
                System.out.println("File selected: " + srcFile.getAbsolutePath());
                ArrayList<String> cleanData = dataCleaner.extractData(srcFile.getPath());
                if (!cleanData.isEmpty()) {
                    logic.addDeck(cleanData);
                }
            }
        } else {
            // No file was selected, handle this case as needed
            System.out.println("File selection cancelled.");
        }
    }

    public void selectDeckOptionSelected(ActionEvent actionEvent) {
        logic.selectDeck();
        //TODO make pop up window with all the decks available from the DB.
    }
}