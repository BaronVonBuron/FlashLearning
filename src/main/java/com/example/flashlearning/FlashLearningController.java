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
            logic.importCollection();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }






    public void selectDeckOptionSelected(ActionEvent actionEvent) throws IOException {
        logic.selectDeck();
        this.selectedDeck = logic.getSelectedDeck();
        if (!flashcardIsShowing){
            flashcardIsShowing = true;
        }
        imageViewShowImage();
    }

    public void selectUserSelected(ActionEvent actionEvent) {
        logic.selectUser();
    }

    public void editUserSelected(ActionEvent actionEvent) {
        logic.editUser();
    }
}