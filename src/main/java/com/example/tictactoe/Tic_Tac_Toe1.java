package com.example.tictactoe;



import javafx.stage.Stage;



class GameEntry{

    private String name;

    private int score;



    public GameEntry(String name, int score){

        this.name = name;

        this.score = score;

    }



    public String getName(){

        return name;

    }

    public int getScore(){

        return score;

    }

    public void setScore(int score) {

    	this.score = score;

    }

    public String toString(){

        return "(" + name + ", " + score + ")";

    }

}



class Scoreboard {

    private int numEntries=0;

    private GameEntry[] score;



    public Scoreboard(int Capacity){

        score = new GameEntry[Capacity];

    }



    public void add(GameEntry e){

        String playerName = e.getName(); boolean playerExists = false; int indexPlayer = 0, scr = 0;

        for (int i = 0; i < numEntries; i++) {

        	if (playerName.equals(score[i].getName())) {

        		playerExists = true;

        		indexPlayer = i;

        		scr = score[indexPlayer].getScore();

        	}

        	if (playerExists) {

        		score[indexPlayer].setScore(scr+100);

        		/*

        		 * Will do a method here, something like compareScore or compareSwap(score[indexPlayer], score[indexPlayer-1], indexPlayer)

        		 * Maybe make bubble sort for 0 < i < indexPlayer 

        		 */

        		return; //If this playerExists = true, kill method immediately so code under doesn't run

        	}

        }

        

        

        int newScore = e.getScore();

        for (int i = 0; i < numEntries; i++) {

            if (playerName.equals(score[i].getName())) {

                newScore += 100;

                score[i] = new GameEntry(playerName, newScore);

            }

        }

        if (numEntries < score.length || newScore > score[numEntries-1].getScore()){

            if (numEntries < score.length)

                numEntries++;

            int j = numEntries - 1;

            while (j > 0 && score[j-1].getScore() < newScore){

                score[j] = score[j-1];

                j--;

            }

            score[j] = e;

        }

    }



    public GameEntry[] getScore(){

        return score;

    }

    public int getNumEntries(){

        return numEntries;

    }

}



//======================================================================= vvvv ORIGINAL



abstract class Tic_Tac_Toe1 {

    public static final int X = 1, O = -1;

    public static final int EMPTY = 0;

    private int[][] board = new int[3][3];

    public int player;

    private Scoreboard scoreboard; //=====NEW======



    public Tic_Tac_Toe1() {

        clearBoard();

        scoreboard = new Scoreboard(2); //=====NEW=====

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



    //======================================================================= ^^^ ORIGINAL



    public void addScore(String name, int score){

        GameEntry e = new GameEntry(name, score);

        scoreboard.add(e);

    }



    public GameEntry[] getScoreboard(){

        return scoreboard.getScore();

    }

    public int getNumScores(){

        return scoreboard.getNumEntries();

    }



    public void displayScoreboard(){

        GameEntry[] scores = getScoreboard();

        for (int i = 0; i < getNumScores(); i++){

            System.out.println((i+1) + ". " + scores[i].getName() + ": " + scores[i].getScore() + "\n");

        }

    }



    public abstract void start(Stage primaryStage);

}