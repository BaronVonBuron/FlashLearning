package com.example.flashlearning;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AddEditFlashcardMenuController {


    public AnchorPane AddEditFlashcardWindow;
    public ImageView AddEditImageView;
    public Button ChooseImageButton;
    public TextField QuestionTextField;
    public TextField AnswerTextField;
    public TextField BonusinfoTextField;
    public Button SaveButton;
    public Button CancelButton;
    public TextField DecknameTextField;
    public TextField ImageNameTextField;
    private Logic logic;

    public AddEditFlashcardMenuController(Logic logic) {
        this.logic = logic;
    }

    public AddEditFlashcardMenuController(Logic logic, Flashcard flashcard) {
        this.logic = logic;
    }


    public void initialize(){
        //TODO if flashcard edit - get card data into things.
        //TODO if new card, just make new card and send to logic.
    }

    public void ChooseImageFileButtonPressed(ActionEvent actionEvent) {

    }

    public void SaveFlashcardButtonPressed(ActionEvent actionEvent) {

    }

    public void CancelButtonPressed(ActionEvent actionEvent) {

    }
}
