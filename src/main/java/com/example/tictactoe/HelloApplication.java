package com.example.tictactoe;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        new TicTacToeGUI().start(primaryStage); // Launch TicTacToeGUI
    }
}