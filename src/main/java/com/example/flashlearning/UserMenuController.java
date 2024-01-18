package com.example.flashlearning;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UserMenuController {
    public AnchorPane UserMenuPane;
    public ListView UserListView;
    public Button SelectSelectedUserButton;
    public Button DeleteSelectedUserButton;
    public TextField UserNameInputField;
    public Button NewUserButton;

    private Logic logic;

    public UserMenuController(Logic logic) {
        this.logic = logic;
    }

    public void initialize(){
        //TODO get users loaded into list
        //TODO make fields and return values to logic, to CRUD user.
        //TODO make it update all the time.
    }

    public void SelectSelectedUserButtonPressed(ActionEvent actionEvent) {

    }

    public void DeleteSelectedUserButtonPressed(ActionEvent actionEvent) {


    }

    public void NewUserButtonPressed(ActionEvent actionEvent) {

    }


}
