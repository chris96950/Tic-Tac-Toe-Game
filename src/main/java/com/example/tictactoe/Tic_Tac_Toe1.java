package com.example.tictactoe;

import javafx.stage.Stage;

abstract class Tic_Tac_Toe1 {
    public static final int X = 1, O = -1;
    public static final int EMPTY = 0;
    private int[][] board = new int[3][3];
    public int player;

    public Tic_Tac_Toe1() {
        clearBoard();
    }

    public void clearBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = EMPTY;
        player = X;
    }

    public void putMark(int i, int j) throws IllegalArgumentException {
        if (i < 0 || i > 2 || j < 0 || j > 2)
            throw new IllegalArgumentException("Invalid board position");
        if (board[i][j] != EMPTY)
            throw new IllegalArgumentException("Board position occupied");
        board[i][j] = player;
        player = -player;
    }

    public boolean isWin(int mark) {
        return ((board[0][0] + board[0][1] + board[0][2] == mark * 3) ||
                (board[1][0] + board[1][1] + board[1][2] == mark * 3) ||
                (board[2][0] + board[2][1] + board[2][2] == mark * 3) ||
                (board[0][0] + board[1][0] + board[2][0] == mark * 3) ||
                (board[0][1] + board[1][1] + board[2][1] == mark * 3) ||
                (board[0][2] + board[1][2] + board[2][2] == mark * 3) ||
                (board[0][0] + board[1][1] + board[2][2] == mark * 3) ||
                (board[2][0] + board[1][1] + board[0][2] == mark * 3));
    }

    public int winner() {
        if (isWin(X)) return X;
        if (isWin(O)) return O;
        return 0;
    }

    public abstract void start(Stage primaryStage);
}