package com.example.playerdatabasesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Player Database System - 2205043");
        HelloApplication.stage = stage;
        stage.setScene(scene);
        stage.show();
        // stage.setOnCloseRequest(
        //         event -> {
        //             try {
        //                 Main.writePlayerData();
        //             } catch (Exception e) {
        //                 throw new RuntimeException(e);
        //             }
        //         }
        // );
    }

    public static void main(String[] args) {
        try {
            SocketWrapper socketWrapper = new SocketWrapper("127.0.0.1", 22222);
            socketWrapper.write("user");
            socketWrapper.write(new Message("getAllPlayers"));
            Main.playerList = (PlayerList) socketWrapper.read();
            socketWrapper.write(new Message("exit"));
            // Main.loadPlayers();
            launch();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}