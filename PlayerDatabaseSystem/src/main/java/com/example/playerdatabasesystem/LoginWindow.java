package com.example.playerdatabasesystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginWindow {
    static Map<String,String> userMap = new HashMap<>();
    public LoginWindow() throws IOException {
        loadUserData();
    }

    @FXML TextField username;
    @FXML PasswordField password;

    static SocketWrapper socketWrapper;
    @FXML
    protected void onLoginButtonClicked() {
        if (username.getText().isEmpty() || password.getText().isEmpty() || !userMap.containsKey(username.getText()) || !userMap.get(username.getText()).equals(password.getText())) {
            String errorMessage = "";
            if (username.getText().isEmpty()) {
                errorMessage = errorMessage + "- Username is empty.\n";
            }
            if (password.getText().isEmpty()) {
                errorMessage = errorMessage + "- Password is empty.\n";
            }
            if (!username.getText().isEmpty() && !userMap.containsKey(username.getText())) {
                errorMessage = errorMessage + "- Username is not registered.\n";
            }
            if (!username.getText().isEmpty() && !password.getText().isEmpty() && userMap.containsKey(username.getText()) && !userMap.get(username.getText()).equals(password.getText())) {
                errorMessage = errorMessage + "- Password is incorrect.\n";
            }
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid Credentials");
            errorAlert.setContentText(errorMessage);
            errorAlert.showAndWait();
            return;
        }
        textFieldToTitleCase(username);
        String usernameText = username.getText().trim();
        try {
            socketWrapper = new SocketWrapper("127.0.0.1", 22222);
            socketWrapper.write(usernameText);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("client-window.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = HelloApplication.stage;
            stage.setTitle(usernameText);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            stage.setOnCloseRequest(
                    event -> {
                        try {
                            socketWrapper.write(new Message("exit"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static Stage registerStage;
    @FXML protected void onRegisterButtonClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        registerStage = new Stage();
        registerStage.setTitle("Register");
        registerStage.setScene(scene);
        HelloApplication.stage.hide();
        registerStage.show();
        registerStage.setOnCloseRequest(
                event -> {
                    try {
                        HelloApplication.stage.show();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    // helper methods below
    static void loadUserData() throws IOException {
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader("src/main/java/com/example/playerdatabasesystem/registered-clubs.txt"));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) break;
            line = line.trim();
            String[] splitted = line.split(",");
            userMap.put(splitted[0], splitted[1]);
        }
        bufferedReader.close();
    }
    static void writeUserData() throws IOException {
        java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter("src/main/java/com/example/playerdatabasesystem/registered-clubs.txt"));
        for (Map.Entry<String, String> entry : userMap.entrySet()) {
            bufferedWriter.write(entry.getKey() + "," + entry.getValue());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
    @FXML
    protected void usernameToTitleCase() {
        textFieldToTitleCase(username);
    }
    private void textFieldToTitleCase(TextField textField) {
        int caretPosition = textField.getCaretPosition();
        textField.setText(Main.toTitleCase(textField.getText().toLowerCase()));
        textField.positionCaret(caretPosition);
    }
}
