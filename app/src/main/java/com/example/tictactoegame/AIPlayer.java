package com.example.tictactoegame;

import java.util.Random;

public class AIPlayer {
    private Random random;

    public AIPlayer() {
        random = new Random();
    }

    public void makeMove(TicTacToeSquare[][] board) {
        // Find an empty cell and place the AI's marker (O) in it
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col].isFilled());

        board[row][col].setOSymbol();
    }
}
