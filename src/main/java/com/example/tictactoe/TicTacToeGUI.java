package com.example.tictactoe;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToeGUI extends Tic_Tac_Toe1 {

    // Create buttons for each cell of the grid
    Button[][] buttons = new Button[3][3];

    @Override
    public void start(Stage primaryStage) {
         //Create a new grid for the Tic-Tac-Toe board
        GridPane grid = new GridPane();


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(" ");
                button.setMinSize(100, 100);
                int row = i, col = j;
                button.setOnAction(e -> handleMove(button, row, col));
                buttons[i][j] = button;
                grid.add(button, j, i);
            }
        }

        // Set up the primary stage (window)
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(new Scene(grid, 300, 300));
        primaryStage.show();
    }

    public void resetButtons(){
         clearBoard();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(" ");
            }
    }


    private void handleMove(Button button, int row, int col) {
        if (button.getText().equals(" ")) {
            putMark(row, col);
            button.setText(player == X ? "O" : "X");  // Display O or X on the button
            int winner = winner();  // Check if there's a winner

            //================MODIFIED========================

            if (winner == X) {
              //Displays winner
                System.out.println("Player " + (winner == X ? "X" : "O") + " wins!\n");
                addScore("Player X", 100);
                System.out.println("=====SCOREBOARD=====");
                displayScoreboard();
                resetButtons();


                }else if (winner == O) {
                System.out.println("Player " + (winner == O ? "O" : "X") + " wins\n");
                addScore("Player O", 100);
                System.out.println("=====SCOREBOARD=====");
                displayScoreboard();
                resetButtons();

            }


        }
    }
}