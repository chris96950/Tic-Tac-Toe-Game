package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerNameController {
	private HelloApplication mainApp; // Reference to main application
	
	@FXML
	private Button btnStartGame;
	@FXML
	private TextField playerXName;
	@FXML
	private TextField playerOName;
	
	//Holds count of default X and O players that have been assigned 
	private static int PXdefCount = 0; 
	private static int POdefCount = 0;
	
    public void setMainApplication(HelloApplication mainApp) {
        this.mainApp = mainApp;
    }
    
	
	/*Going to be a method to handle default names here. Will take note of how many "default" names gets passed
	 * through into the game.
	 */
	//private String handleDefaultPlayerName(int defCount) {
	//	String n = "Player " + defCount; defCount++
	//	return n;
	//}
    @FXML
	private void handleGameStart() {
        String playerX = playerXName.getText().trim();
        String playerO = playerOName.getText().trim();
        if (playerX.equals("")) {
        	playerX = "Player X (" + PXdefCount + ")"; PXdefCount++;
        }
        if (playerO.equals("")) {
        	playerO = "Player O (" + POdefCount + ")"; POdefCount++;
        }

		System.out.println("Starting new game with:");
		System.out.println("Player X: " + playerX);
		System.out.println("Player O: " + playerO);

		if (mainApp == null) {
			System.out.println("ERROR: mainApp is null!");
			return;
		}
        
        //will call handleDefaultPlayerName() to provide default names
//        if (playerX.equals("Player 1")) playerX = handleDefaultPlayerName(defCount);
//        if (playerO.equals("Player 2")) playerO = handleDefaultPlayerName(defCount);
        try {
            mainApp.showNewGuiScene(playerX, playerO);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}


	public void start(Stage primaryStage) {

	}


}
