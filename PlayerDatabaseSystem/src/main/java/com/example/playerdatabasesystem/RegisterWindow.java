package com.example.playerdatabasesystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterWindow {
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML PasswordField confirmPassword;
    @FXML
    protected void onRegisterButtonClicked() throws IOException {
        textFieldToTitleCase(username);
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Username and password cannot be empty.");
            errorAlert.showAndWait();
        } else if (LoginWindow.userMap.containsKey(username.getText())) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Username is already registered.");
            errorAlert.showAndWait();
        } else if (!password.getText().equals(confirmPassword.getText())) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Passwords do not match.");
            errorAlert.showAndWait();
        } else {
            LoginWindow.userMap.put(username.getText(), password.getText());
            LoginWindow.writeUserData();
            username.setText("");
            password.setText("");
            confirmPassword.setText("");
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Registration successful.");
            successAlert.showAndWait();
            HelloApplication.stage.show();
            LoginWindow.registerStage.close();
        }
    }
    // helper methods below
    private void textFieldToTitleCase(TextField textField) {
        int caretPosition = textField.getCaretPosition();
        textField.setText(Main.toTitleCase(textField.getText().toLowerCase()));
        textField.positionCaret(caretPosition);
    }
}
