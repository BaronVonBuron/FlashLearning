package com.example.flashlearning;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class UserMenuController {
    public AnchorPane UserMenuPane;
    public ListView UserListView;
    public Button SelectSelectedUserButton;
    public Button DeleteSelectedUserButton;
    public TextField UserNameInputField;
    public Button NewUserButton;
    public Button EditSelectedUserButton;
    private String username;

    private Logic logic;

    public UserMenuController(Logic logic) {
        UserListView = new ListView<>();
        this.logic = logic;
    }

    public void initialize(){
        update();
    }

    public void update(){
        UserListView.getItems().clear();
        UserListView.getItems().addAll(logic.getUsers());
    }

    public void closeWindow(){
        Stage stage = (Stage) UserMenuPane.getScene().getWindow();
        stage.close();
    }

    public void SelectSelectedUserButtonPressed(ActionEvent actionEvent) {
        if (UserListView.getSelectionModel().getSelectedItem() != null){
            logic.setSelectedUser((User) UserListView.getSelectionModel().getSelectedItem());
            closeWindow();
        }
    }

    public void DeleteSelectedUserButtonPressed() {
        if (UserListView.getSelectionModel().getSelectedItem() != null){
            logic.deleteUser((User) UserListView.getSelectionModel().getSelectedItem());
            update();
        }
    }

    public void NewUserButtonPressed() {
        if (!UserNameInputField.getText().isEmpty()) {
            boolean exists = false;
            for (User user : logic.getUsers()) {
                if (Objects.equals(user.getUserName(), UserNameInputField.getText())){
                    exists = true;
                }
            }
            if (!exists) {
                logic.createUser(UserNameInputField.getText());
                update();
                UserNameInputField.clear();
            } else UserNameInputField.setText("Brugernavn findes allerede");
        }
    }



    public void EditSelectedUserButtonPressed(ActionEvent actionEvent) {
        if (UserListView.getSelectionModel().getSelectedItem() != null && !UserNameInputField.getText().isEmpty()){
            logic.editUser((User) UserListView.getSelectionModel().getSelectedItem(), UserNameInputField.getText());
            update();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
