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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class NewGuiController {
	@FXML
	private Label Player1_lbl, Player2_lbl, Header_lbl, X_lbl, O_lbl;

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
	private char currentPlayer = ' ';
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
		//scoreBoard_lbl.setStyle("general-lblStyle"); =
		player1Name = Player1_lbl.getText();
		player2Name = Player2_lbl.getText();

		Random random = new Random();
		currentPlayer = (random.nextBoolean()) ? 'X' : 'O'; // Randomly choose 'X' or 'O'
		updateTurnLabel();

		Header_lbl.setText("Tic Tac Toe Game");
		initializeBoard();
		setupButtonActions();
		hideWinningLines();
		updateScoreboardDisplay();
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

				// Set hover effect
				buttons[row][col].setOnMouseEntered(event -> handleMouseEnter(r, c));
				buttons[row][col].setOnMouseExited(event -> handleMouseExit(r, c));
			}
		}

		//reset_btn.setOnAction(event -> resetGame());
	}

	private void handleMouseEnter(int row, int col) {
		Button[][] buttons = {
				{button00, button01, button02},
				{button10, button11, button12},
				{button20, button21, button22}
		};

		// Show the current player's mark if the button is empty
		if (board[row][col] == EMPTY) {
			buttons[row][col].setText(String.valueOf(currentPlayer));  // Show hover mark
			buttons[row][col].setStyle("-fx-text-fill: #a7b088; -fx-background-color: transparent;");
		}
	}

	private void handleMouseExit(int row, int col) {
		Button[][] buttons = {
				{button00, button01, button02},
				{button10, button11, button12},
				{button20, button21, button22}
		};

		// Only remove hover mark if the button is still empty
		if (board[row][col] == EMPTY) {
			buttons[row][col].setText("");  // Remove hover mark
			buttons[row][col].setStyle("-fx-text-fill: #828f55; -fx-background-color: transparent;");
		}
	}

	private void handleButtonClick(int row, int col) {
		if (board[row][col] == EMPTY) {
			// Update the board with the current player's mark (X or O)
			board[row][col] = (currentPlayer == 'X') ? X : O;

			// Update the button text with the correct mark
			updateButtonText(row, col);

			// Increment the number of moves
			moves++;

			// Check if the game has a winner or a draw
			if (isWin((currentPlayer == 'X') ? X : O)) {
				handleWin();
			} else if (moves == 9) {
				handleDraw();
			} else {
				// Switch to the other player
				currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
				updateTurnLabel();
			}
		}
	}

	private void updateTurnLabel() {
		Header_lbl.setText(currentPlayer + "'s Turn");
	}

	private void updateButtonText(int row, int col) {
		Button[][] buttons = {
				{button00, button01, button02},
				{button10, button11, button12},
				{button20, button21, button22}
		};

		// Update the button text to show the current player's mark (X or O)
		buttons[row][col].setText(String.valueOf((board[row][col] == X) ? 'X' : 'O'));
		// Optionally, you can add style for font color as well.
		buttons[row][col].setStyle("-fx-text-fill: #828f55; -fx-background-color: transparent;");
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

	private void updateScoreboardDisplay() { //removed parameter
		TextFlow textFlow = new TextFlow();
		GameEntry[] scores = Scoreboard.getInstance().getScore();

		for (int i = 0; i < Scoreboard.getInstance().getNumEntries(); i++) {
			// Create the rank and separator
			Text rank = new Text((i + 1) + ". ");
			rank.setFont(Font.font("Calibri", FontWeight.SEMI_BOLD, 13));
			textFlow.getChildren().add(rank);

			// Create the bold name
			Text name = new Text(scores[i].getName());
			name.setFont(Font.font("Calibri", FontWeight.BOLD, 13)); // Adjust font and size as needed
			textFlow.getChildren().add(name);

			// Create the score and newline
			Text score = new Text(": " + scores[i].getScore() + "\n");
			score.setFont(Font.font("Calibri", FontWeight.BOLD, 13));
			score.setFill(Paint.valueOf("#565f38"));
			textFlow.getChildren().add(score);
		}

		scoreBoard_lbl.setText(""); // Clear the existing label text
		scoreBoard_lbl.setGraphic(textFlow); // Set the TextFlow as the label's graphic
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
		//Added so only winner gets added to scoreboard
		if (currentPlayer == 'X') {
		    Scoreboard.getInstance().add(new GameEntry(Player1_lbl.getText(), 100));

		} else {
		    Scoreboard.getInstance().add(new GameEntry(Player2_lbl.getText(), 100));
		}

		// Update the scoreboard label
		updateScoreboardDisplay();


		//Player win message
		if (currentPlayer == 'X') {
			TextFlow winMessagex = new TextFlow();
			Text winText = new Text(Player1_lbl.getText() + " wins!");
			winText.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
			winText.setFill(Paint.valueOf("#FF9D23"));
			winMessagex.getChildren().add(winText);

			// Add the win message to the scoreboard label
			scoreBoard_lbl.setGraphic(new VBox(scoreBoard_lbl.getGraphic(), winMessagex));
		} else {
			TextFlow winMessageo = new TextFlow();
			Text winText = new Text(Player2_lbl.getText() + " wins!");
			winText.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
			winText.setFill(Paint.valueOf("#FF9D23"));
			winMessageo.getChildren().add(winText);

			// Add the win message to the scoreboard label
			scoreBoard_lbl.setGraphic(new VBox(scoreBoard_lbl.getGraphic(), winMessageo));
		}

		disableAllButtons();
		drawWinningLine();
		PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
		pause.setOnFinished(event -> resetBoard());
		pause.play();
	}


	private void handleDraw() {
		// Update the scoreboard using TextFlow
		updateScoreboardDisplay();

		// Create and display the draw message separately
		TextFlow drawMessage = new TextFlow();
		Text tieText = new Text("It's a tie!");
		tieText.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		tieText.setFill(Paint.valueOf("#FF9D23"));
		drawMessage.getChildren().add(tieText);

		// Add the draw message to the scoreboard label.
		scoreBoard_lbl.setGraphic(new VBox(scoreBoard_lbl.getGraphic(), drawMessage));

		PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
		pause.setOnFinished(event -> resetBoard());
		pause.play();
	}
	private void resetBoard() {
		initializeBoard();
		clearButtonTexts();

		if (moves == 9) { // Draw
			Random random = new Random();
			currentPlayer = (random.nextBoolean()) ? 'X' : 'O';
		} else if (currentPlayer == 'X') {
			currentPlayer = 'O'; // O lost, so O starts
		} else {
			currentPlayer = 'X'; // X lost, so X starts
		}

		moves = 0;
		enableAllButtons();
		hideWinningLines();
		updateTurnLabel();
		updateScoreboardDisplay();
	}

	private void disableAllButtons() {
		Button[][] buttons = {
				{button00, button01, button02},
				{button10, button11, button12},
				{button20, button21, button22}
		};

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (board[row][col] == EMPTY) { // Check if the button is empty
					buttons[row][col].setDisable(true); // Disable the empty button
				}
			}
		}
	}

	private void enableAllButtons() {
		Button[][] buttons = {
				{button00, button01, button02},
				{button10, button11, button12},
				{button20, button21, button22}
		};

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				buttons[row][col].setDisable(false); // Enable the button
			}
		}
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


	@FXML
	private void switchToNew(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tictactoe/PlayerName.fxml"));
		Parent root = loader.load();

		Scene newScene = new Scene(root);

		PlayerNameController controller = loader.getController();
		controller.setMainApplication(HelloApplication.getInstance());

		currentStage.setScene(newScene);
		currentStage.setTitle("Enter Player Names");
		currentStage.show();
	}




}

