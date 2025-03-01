package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NewGuiController {
	@FXML
	private Label Player1_lbl, Player2_lbl;

	public void setPlayerNames(String playerX, String playerO) {
		Player1_lbl.setText(playerX); Player2_lbl.setText(playerO);
	}
}
