package com.example.flashlearning;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class AddEditFlashcardMenuController {


    public AnchorPane AddEditFlashcardWindow;
    public ImageView AddEditImageView;
    public Button ChooseImageButton;
    public TextField QuestionTextField;
    public TextField AnswerTextField;
    public TextArea BonusInfoTextArea;
    public Button SaveButton;
    public Button CancelButton;
    public TextField DecknameTextField;
    public TextField ImageNameTextField;
    private Logic logic;
    private File file;

    public AddEditFlashcardMenuController(Logic logic) {//Constructor til at lave nyt kort
        this.logic = logic;
    }

    public AddEditFlashcardMenuController(Logic logic, Flashcard flashcard) {//Constructor til at redigere i kort
        this.logic = logic;
    }


    public void initialize(){
        ImageNameTextField.setEditable(false);

    }

    public void ChooseImageFileButtonPressed(ActionEvent actionEvent) throws Exception {
        FileChooser filechooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(".jpg", ".jpeg", ".png");
        filechooser.setSelectedExtensionFilter(filter);
        try {
            File chosenfile = filechooser.showOpenDialog(null);

            if (chosenfile != null && isImageFile(chosenfile.getName())) {
                this.file = chosenfile;
                setImage();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Vælg venligst en billedefil");
                alert.setTitle("Fejl");
                alert.showAndWait();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    public void setImage(){
        Image img = new Image(this.file.getAbsolutePath());
        if (img != null) {
            AddEditImageView.setImage(img);
            ImageNameTextField.setText(file.getName());
        }
    }

    public void SaveFlashcardButtonPressed(ActionEvent actionEvent) throws IOException {
        String deckname = DecknameTextField.getText();
        String question = QuestionTextField.getText();
        String answer = AnswerTextField.getText();
        String bonusinfo = BonusInfoTextArea.getText();
        byte[] fileContent = Files.readAllBytes(file.toPath());

        if (bonusinfo.isEmpty()){
            bonusinfo = " ";
        }

        if (!deckname.isEmpty() && !question.isEmpty() && !answer.isEmpty() && fileContent.length > 0) {
            logic.addFlashcard(new Flashcard(UUID.randomUUID().toString(), deckname, question, answer, bonusinfo, fileContent));
            closeWindow();
        }
    }


    public void CancelButtonPressed(ActionEvent actionEvent) {
        closeWindow();
    }

    private boolean isImageFile(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        return lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".png")
                || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".bmp");
    }

    public void closeWindow(){
        Stage stage = (Stage) AddEditFlashcardWindow.getScene().getWindow();
        stage.close();
    }
}
