package com.example.tictactoegame.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;
import com.example.tictactoegame.R;
import com.google.android.material.button.MaterialButton;

public class GameFragment extends Fragment {

    private GridLayout ticTacToeBoard;
    private MaterialButton[][] squares;
    private boolean xTurn = true; // True for X, False for O
    private boolean gameActive = true; // Indicates if the game is still active

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        // Initialize the game board
        ticTacToeBoard = view.findViewById(R.id.ticTacToeBoard);
        initializeBoard();

        return view;
    }

    // Initialize the game board and set up click listeners for each square
    private void initializeBoard() {
        squares = new MaterialButton[3][3];
        Log.d("MainActivity", "This is a debug message");
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int resourceId = getResources().getIdentifier("square_" + row + col, "id", getActivity().getPackageName());
                squares[row][col] = ticTacToeBoard.findViewById(resourceId);

                // Create a click listener for the current square
                squares[row][col].setOnClickListener(createSquareClickListener(row, col));
            }
        }
    }

    // Create a click listener for a square at the given row and column
    private View.OnClickListener createSquareClickListener(final int row, final int col) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSquareClick(squares[row][col]);
            }
        };
    }

    // Handle square click event
    private void onSquareClick(MaterialButton square) {
        if (!gameActive) {
            return; // Game is over, do nothing
        }

        // Check if the square is empty
        if (square.getText() == null || square.getText().toString().isEmpty()) {
            // Set X or O based on the current turn
            if (xTurn) {
                square.setText("X");
            } else {
                square.setText("O");
            }

            // Check for a win or draw
            if (checkWin()) {
                gameActive = false;
                showWinMessage(xTurn ? "X" : "O");
            } else if (checkDraw()) {
                gameActive = false;
                showDrawMessage();
            } else {
                xTurn = !xTurn; // Switch to the other player's turn
                Log.d("MainActivity", "Going to switch");

            }
        }
    }

    // Check for a win
    private boolean checkWin() {
        // Check rows, columns, and diagonals for a win

        // Check rows
        for (int row = 0; row < 3; row++) {
            if (checkThree(squares[row][0], squares[row][1], squares[row][2])) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (checkThree(squares[0][col], squares[1][col], squares[2][col])) {
                return true;
            }
        }

        // Check diagonals
        if (checkThree(squares[0][0], squares[1][1], squares[2][2]) ||
                checkThree(squares[0][2], squares[1][1], squares[2][0])) {
            return true;
        }

        return false;
    }

    // Helper method to check if three MaterialButton elements have the same text
    private boolean checkThree(MaterialButton square1, MaterialButton square2, MaterialButton square3) {
        return square1.getText() != null &&
                square1.getText().toString().equals(square2.getText().toString()) &&
                square1.getText().toString().equals(square3.getText().toString());
    }

    // Check for a draw
    private boolean checkDraw() {
        // Check if all squares are filled (no empty squares)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (squares[row][col].getText() == null || squares[row][col].getText().toString().isEmpty()) {
                    return false; // At least one square is empty
                }
            }
        }
        return true; // All squares are filled, indicating a draw
    }

    // Display a win message
    private void showWinMessage(String winner) {
        Toast.makeText(getActivity(), "Player " + winner + " wins!", Toast.LENGTH_SHORT).show();
    }

    // Display a draw message
    private void showDrawMessage() {
        Toast.makeText(getActivity(), "It's a draw!", Toast.LENGTH_SHORT).show();
    }
}
