package com.example.tictactoe;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class NewGuiController {
	@FXML
	private Label Player1_lbl, Player2_lbl;

	@FXML
	private Button reset_btn;

	@FXML
	private GridPane gridPane;

	@FXML
	private Label scoreBoard_lbl;

	@FXML
	private Line VLeft_line, VMiddle_line, VRight_line, HTop_line, HMiddle_line, HBottom_line, DLeft_line, DRight_line;

	@FXML
	private Button button00, button10, button20, button01, button11, button21, button02, button12, button22;

	public void setPlayerNames(String playerX, String playerO) {
		Player1_lbl.setText(playerX);
		Player2_lbl.setText(playerO);
	}

	// Game Logic Variables
	private char currentPlayer = 'X';
	private int[][] board = new int[3][3]; // Use int array for isWin()
	private int moves = 0;
	private int player1Score = 0;
	private int player2Score = 0;
	private String player1Name = "";
	private String player2Name = "";

	public static final int X = 1, O = -1;
	public static final int EMPTY = 0;

	@FXML
	public void initialize() throws IOException {
		player1Name = Player1_lbl.getText();
		player2Name = Player2_lbl.getText();

		initializeBoard();
		setupButtonActions();
		hideWinningLines();
	}

	private void hideWinningLines() {
		if (VLeft_line != null) VLeft_line.setVisible(false);
		if (VMiddle_line != null) VMiddle_line.setVisible(false);
		if (VRight_line != null) VRight_line.setVisible(false);
		if (HTop_line != null) HTop_line.setVisible(false);
		if (HMiddle_line != null) HMiddle_line.setVisible(false);
		if (HBottom_line != null) HBottom_line.setVisible(false);
		if (DLeft_line != null) DLeft_line.setVisible(false);
		if (DRight_line != null) DRight_line.setVisible(false);
	}

	private void initializeBoard() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				board[row][col] = EMPTY; // Initialize with EMPTY
			}
		}
	}

	private void setupButtonActions() {
		Button[][] buttons = {
				{button00, button01, button02},
				{button10, button11, button12},
				{button20, button21, button22}
		};

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				final int r = row;
				final int c = col;
				buttons[row][col].setOnAction(event -> handleButtonClick(r, c));
			}
		}

		reset_btn.setOnAction(event -> resetGame());
	}

	private void handleButtonClick(int row, int col) {
		if (board[row][col] == EMPTY) {
			board[row][col] = (currentPlayer == 'X') ? X : O; // Update board with X or O
			updateButtonText(row, col);
			moves++;

			if (isWin((currentPlayer == 'X') ? X : O)) { // Use isWin()
				handleWin();
			} else if (moves == 9) {
				handleDraw();
			} else {
				currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
			}
		}
	}

	private void updateButtonText(int row, int col) {
		Button[][] buttons = {
				{button00, button01, button02},
				{button10, button11, button12},
				{button20, button21, button22}
		};
		buttons[row][col].setText(String.valueOf((board[row][col] == X) ? 'X' : (board[row][col] == O) ? 'O' : ' ')); // Update button text
	}

	private boolean isWin(int mark) {
		return ((board[0][0] + board[0][1] + board[0][2] == mark * 3) ||
				(board[1][0] + board[1][1] + board[1][2] == mark * 3) ||
				(board[2][0] + board[2][1] + board[2][2] == mark * 3) ||
				(board[0][0] + board[1][0] + board[2][0] == mark * 3) ||
				(board[0][1] + board[1][1] + board[2][1] == mark * 3) ||
				(board[0][2] + board[1][2] + board[2][2] == mark * 3) ||
				(board[0][0] + board[1][1] + board[2][2] == mark * 3) ||
				(board[2][0] + board[1][1] + board[0][2] == mark * 3));
	}

	private void updateScoreboardDisplay(Tic_Tac_Toe1 game) {
		StringBuilder scoreboardText = new StringBuilder("Scores:\n");
		GameEntry[] scores = game.getScoreboard();

		for (int i = 0; i < game.getNumScores(); i++) {
			scoreboardText.append((i + 1)).append(". ")
					.append(scores[i].getName()).append(": ")
					.append(scores[i].getScore()).append("\n");
		}

		scoreBoard_lbl.setText(scoreboardText.toString());
	}

	private void handleWin() {
		if (currentPlayer == 'X') {
			player1Score += 100; // Increments score
		} else {
			player2Score += 100;
		}

		// Add the scores to the scoreboard
		Tic_Tac_Toe1 game = new Tic_Tac_Toe1() {
			@Override
			public void start(Stage primaryStage) {

			}
		};
		game.addScore(Player1_lbl.getText(), player1Score);
		game.addScore(Player2_lbl.getText(), player2Score);

		// Update the scoreboard label
		updateScoreboardDisplay(game);

		drawWinningLine();
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> resetBoard());
		pause.play();
	}


	private void handleDraw() {
		// Display tie message with the current score
		scoreBoard_lbl.setText("It's a tie!\n" +
				Player1_lbl.getText() + ": " + player1Score + " | " +
				Player2_lbl.getText() + ": " + player2Score);

		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> resetBoard());
		pause.play();
	}
	private void resetBoard() {
		initializeBoard();
		clearButtonTexts();
		moves = 0;
		currentPlayer = 'X';
		hideWinningLines();
	}



	private void clearButtonTexts() {
		Button[][] buttons = {
				{button00, button01, button02},
				{button10, button11, button12},
				{button20, button21, button22}
		};

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				buttons[row][col].setText("");
			}
		}
	}

	private void resetGame() {
		player1Score = 0;
		player2Score = 0;
		resetBoard();
	}

	private void drawWinningLine() {
		int mark = (currentPlayer == 'X') ? X : O;

		if ((board[0][0] + board[0][1] + board[0][2] == mark * 3)) {
			VLeft_line.setVisible(true); // Vertical left
		} else if ((board[1][0] + board[1][1] + board[1][2] == mark * 3)) {
			VMiddle_line.setVisible(true); // Vertical Middle
		} else if ((board[2][0] + board[2][1] + board[2][2] == mark * 3)) {
			VRight_line.setVisible(true); // Vertical Right
		} else if ((board[0][0] + board[1][0] + board[2][0] == mark * 3)) {
			HTop_line.setVisible(true); // Horizontal Top
		} else if ((board[0][1] + board[1][1] + board[2][1] == mark * 3)) {
			HMiddle_line.setVisible(true); // Horizontal middle
		} else if ((board[0][2] + board[1][2] + board[2][2] == mark * 3)) {
			HBottom_line.setVisible(true); // Horizontal right
		} else if ((board[0][0] + board[1][1] + board[2][2] == mark * 3)) {
			DLeft_line.setVisible(true); // Diagonal left-to-right
		} else if ((board[2][0] + board[1][1] + board[0][2] == mark * 3)) {
			DRight_line.setVisible(true); // Diagonal right-to-left
		} else {
			return;
		}
	}
}

