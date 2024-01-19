package com.example.flashlearning;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.nio.file.Files;

public class FlashLearningController {

    private final Logic logic = new Logic();
    public TextField QuestionTextField;
    public MenuItem MenuSelectDeck;
    public MenuItem SelectUserMenu;
    public MenuItem EditUserMenu;
    public ProgressBar TrainingProgressBar; //TODO make it show how far along the training in that deck is.
    public TableView StatisticsTableView;
    private boolean trainingStarted, isAnswerShowing;
    private Deck selectedDeck;
    public ImageView MainImageView;
    private Image bgImage;
    public AnchorPane LearningPane;
    public Button ShowAnswerButton, IrrelevantButton, CorrectButton, WrongButton, HardButton, MediumButton;
    public TextArea ImageTextArea;
    public MenuBar MenuBar;
    public MenuItem menuFileImportOption;
    private User selectedUser;



    public void initialize() throws IOException {
        backgroundStartup();
        selectUserSelected();
        selectDeckOptionSelected();
        trainingStarted = false;
        isAnswerShowing = false;
        ShowAnswerButton.setText("Start");
        QuestionTextField.setEditable(false);
        QuestionTextField.setAlignment(Pos.CENTER);
    }

    private void backgroundStartup() {
        this.bgImage = new Image(getClass().getResourceAsStream("/BackgroundFlashcardLearning.png"));
        BackgroundImage bg = new BackgroundImage(bgImage,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        this.LearningPane.setBackground(new Background(bg));
    }

    private void imageViewShowImage(byte[] imageData) throws IOException {
        Image img = ImageConverter.byteArrayToFXImage(imageData);
        if (img != null) {
            MainImageView.setImage(img);
            MainImageView.setPreserveRatio(true);//Så det ikke skaleres dumt.
            //Nedenstående centrerer billedet på x aksen.
            double wc = MainImageView.getFitWidth() / img.getWidth();//Breddekoefficient
            double hc = MainImageView.getFitHeight() / img.getHeight();//Højdekoefficient.
            if (wc <= hc) {
                MainImageView.setX((LearningPane.getPrefWidth() / 2) - (MainImageView.getFitWidth()) / 2);
            } else {
                MainImageView.setX((LearningPane.getPrefWidth() / 2) - (img.getWidth() * hc) / 2);
            }
        }
    }


    public void showAnswerButtonPressed() throws IOException {
        if (!trainingStarted && selectedUser != null){
            if (selectedUser.getNextCard() != null) {
                imageViewShowImage(selectedUser.getNextCard().getImageData());
                QuestionTextField.setText(selectedUser.getNextCard().getQuestion());
                ShowAnswerButton.setText("Vis Svar");
                trainingStarted = true;
            } else noImages();
        } else if (trainingStarted && !selectedUser.isLastCard()) {
            ImageTextArea.setText(selectedUser.getNextCard().getAnswer()+"\n"+selectedUser.getNextCard().getBonusinfo());
            isAnswerShowing = true;
        } else {
            ImageTextArea.setText("Vælg venligst bruger og deck for at begynde");
        }
    }

    public void nextImage() throws IOException {
        if (isAnswerShowing && !selectedUser.isLastCard()) {//Kan kun gå til næste billede når svaret er vist.
            selectedUser.setNextCard();
            if (!selectedUser.isLastCard()) {
                ImageTextArea.clear();
                imageViewShowImage(selectedUser.getNextCard().getImageData());
                QuestionTextField.setText(selectedUser.getNextCard().getQuestion());
                isAnswerShowing = false;
            } else {
                lastImage();
            }
        }
    }

    public void lastImage(){
        if (selectedUser.isLastCard()){
            Image img = new Image(this.getClass().getResourceAsStream("/lastcardflashlearning.jpg"));
            MainImageView.setImage(img);
            MainImageView.setX(250);
            ImageTextArea.setText("Træning Slut");
            isAnswerShowing = false;
            ShowAnswerButton.setText("Start");
            trainingStarted = false;
        }
    }

    public void noImages(){
        Image img = new Image(this.getClass().getResourceAsStream("/noimagesflashlearning.jpg"));
        MainImageView.setImage(img);
        MainImageView.setX(250);
        ImageTextArea.setText("Importér eller vælg et sæt for at kunne starte træningen");
    }

    public void mediumButtonPressed(ActionEvent actionEvent) throws IOException {//næsten
        if (isAnswerShowing) {
            logic.userAnswer(2, this.selectedUser, this.selectedUser.getNextCard());
            nextImage();
        }
    }

    public void hardButtonPressed(ActionEvent actionEvent) throws IOException {//svær
        if (isAnswerShowing) {
            logic.userAnswer(3, this.selectedUser, this.selectedUser.getNextCard());
            nextImage();
        }
    }

    public void wrongButtonPressed(ActionEvent actionEvent) throws IOException {//forkert
        if (isAnswerShowing) {
            logic.userAnswer(4, this.selectedUser, this.selectedUser.getNextCard());
            nextImage();
        }
    }

    public void correctButtonPressed(ActionEvent actionEvent) throws IOException {//korrekt
        if (isAnswerShowing) {
            logic.userAnswer(1, this.selectedUser, this.selectedUser.getNextCard());
            nextImage();
        }
    }

    public void irrelevantButtonPressed(ActionEvent actionEvent) throws IOException {
        if (isAnswerShowing) {
            logic.userAnswer(5, this.selectedUser, this.selectedUser.getNextCard());
            nextImage();
        }
    }


    public void importOptionSelected() throws Exception {
        try {
            logic.importCollection();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }



    public void selectDeckOptionSelected() throws IOException {
        if (!logic.getDecks().isEmpty()) {
            logic.selectDeck();
        } else {
            try {
                importOptionSelected();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void selectUserSelected() {
        logic.selectUser();
        this.selectedUser = logic.getSelectedUser();
    }

    public void editUserSelected(ActionEvent actionEvent) {
        logic.selectUser();
    }
}