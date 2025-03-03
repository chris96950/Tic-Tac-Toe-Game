package com.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Load PlayerName.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tictactoe/PlayerName.fxml"));
        Parent root = loader.load();

        // Get Controller and Pass Main Application Reference
        PlayerNameController controller = loader.getController();
        controller.setMainApplication(this);

        primaryStage.setTitle("Enter Player Names");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    //Method to Open New GUI Scene
    public void showNewGuiScene(String playerX, String playerO) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tictactoe/TicTacToe.fxml"));
        Parent root = loader.load();

        // Handle empty player names, setting defaults
        if (playerX == null || playerX.trim().isEmpty()) {
            playerX = "Player X"; // Default name for Player X
        }
        if (playerO == null || playerO.trim().isEmpty()) {
            playerO = "Player O"; // Default name for Player O
        };
        
        NewGuiController controller = loader.getController();
        controller.setPlayerNames(playerX, playerO);

        primaryStage.setTitle("Tic Tac Toe - " + playerX + " vs " + playerO);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private static HelloApplication instance;

    public HelloApplication() {
        instance = this;
    }

    public static HelloApplication getInstance() {
        return instance;
    }




    public static void main(String[] args) {
        launch(args);
    }
}