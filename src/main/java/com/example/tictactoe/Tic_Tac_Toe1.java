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

	private static Scoreboard instance = null;
    private int numEntries=0;
    private GameEntry[] score;
    private Scoreboard(int Capacity){
        score = new GameEntry[Capacity];
    }
    public static Scoreboard getInstance() {
        if (instance == null) {
            instance = new Scoreboard(10); // Adjust capacity as needed
        }
        return instance;
    }



    public void add(GameEntry e){
        String playerName = e.getName();
        int newScore = e.getScore();

        // Check if the player already exists in the scoreboard
        for (int i = 0; i < numEntries; i++) {
            if (score[i] != null && score[i].getName().equals(playerName)) {
                // Update the existing player's score
                score[i].setScore(score[i].getScore() + newScore);
                sortPlayer(i); // Sort leaderboard after update
                return; // Exit after updating
            }
        }

        // If player does not exist, add them to the scoreboard
        if (numEntries < score.length) {
            score[numEntries] = new GameEntry(playerName, newScore);  
            numEntries++;
            sortPlayer(numEntries - 1);
        }
    }

    public void sortPlayer(int index){ // (NEW) Makes it so it sorts the highest score
        while (index > 0 && score[index].getScore() > score[index-1].getScore()){
            GameEntry temp = score[index];
            score[index]=score[index-1];
            score[index-1]=temp;
            index--;
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
    public int player, step;
    private Scoreboard scoreboard; //=====NEW======

    public Tic_Tac_Toe1() {
        clearBoard();
        //scoreboard = new Scoreboard(2); //=====NEW=====
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
    	Scoreboard.getInstance().add(new GameEntry(name, score)); 
    }

    
    public GameEntry[] getScoreboard(){
    	return Scoreboard.getInstance().getScore();
    
    }
    public int getNumScores(){
    	return Scoreboard.getInstance().getNumEntries();
    
    }
    public void displayScoreboard(){
        GameEntry[] scores = getScoreboard();
        for (int i = 0; i < getNumScores(); i++){
            System.out.println((i+1) + ". " + scores[i].getName() + ": " + scores[i].getScore() + "\n");
        }
    }
    
    public abstract void start(Stage primaryStage);
}