package com.example.tictactoegame.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.tictactoegame.R;
import com.example.tictactoegame.TicTacToeSquare;

public class GameFragment extends Fragment {

    private TicTacToeSquare[][] buttons = new TicTacToeSquare[3][3];
    private boolean player1Turn = true; // Player 1 is X, Player 2 is O
    private int roundCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        // Initialize buttons and add click listeners
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "square_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
                buttons[i][j] = view.findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClick((TicTacToeSquare) v);
                    }
                });
            }
        }

        // Add logic for game modes (2-player and vs. AI)

        return view;
    }

    private void onButtonClick(TicTacToeSquare button) {
        if (button.isFilled()) {
            // Cell is already filled, do nothing
            return;
        }

        if (player1Turn) {
            // Player 1's turn (X)
            button.setXSymbol();
        } else {
            // Player 2's turn (O)
            button.setOSymbol();
        }

        roundCount++;

        if (checkForWin()) {
            // Player X or O has won, handle the win
            if (player1Turn) {
                playerWins("Player X"); // Player X wins
            } else {
                playerWins("Player O"); // Player O wins
            }
        } else if (roundCount == 9) {
            // It's a draw
            declareDraw();
        } else {
            // Switch turns
            player1Turn = !player1Turn;
        }
    }


    private boolean checkForWin() {
        // Check rows, columns, and diagonals for a win

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].isXSymbol() && buttons[i][1].isXSymbol() && buttons[i][2].isXSymbol()) {
                // Player 1 (X) wins
                playerWins("Player 1");
                return true;
            }
            if (buttons[i][0].isOSymbol() && buttons[i][1].isOSymbol() && buttons[i][2].isOSymbol()) {
                // Player 2 (O) wins
                playerWins("Player 2");
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].isXSymbol() && buttons[1][i].isXSymbol() && buttons[2][i].isXSymbol()) {
                // Player 1 (X) wins
                playerWins("Player 1");
                return true;
            }
            if (buttons[0][i].isOSymbol() && buttons[1][i].isOSymbol() && buttons[2][i].isOSymbol()) {
                // Player 2 (O) wins
                playerWins("Player 2");
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].isXSymbol() && buttons[1][1].isXSymbol() && buttons[2][2].isXSymbol()) {
            // Player 1 (X) wins
            playerWins("Player 1");
            return true;
        }
        if (buttons[0][2].isXSymbol() && buttons[1][1].isXSymbol() && buttons[2][0].isXSymbol()) {
            // Player 1 (X) wins
            playerWins("Player 1");
            return true;
        }
        if (buttons[0][0].isOSymbol() && buttons[1][1].isOSymbol() && buttons[2][2].isOSymbol()) {
            // Player 2 (O) wins
            playerWins("Player 2");
            return true;
        }
        if (buttons[0][2].isOSymbol() && buttons[1][1].isOSymbol() && buttons[2][0].isOSymbol()) {
            // Player 2 (O) wins
            playerWins("Player 2");
            return true;
        }

        // Check for a draw
        if (roundCount == 9) {
            declareDraw();
            return true;
        }

        return false; // No winner yet
    }


    // Inside your GameFragment.java
    private void playerWins(String player) {
        // Create and show the win message dialog
        WinDialogFragment winDialogFragment = new WinDialogFragment();
        winDialogFragment.show(getFragmentManager(), "WinDialog");
        resetGame();
    }


    private void declareDraw() {
        // Implement logic for a draw
        Toast.makeText(getActivity(), "It's a draw!", Toast.LENGTH_SHORT).show();
        resetGame();
    }

    private void resetGame() {
        // Implement logic to reset the game
        player1Turn = true;
        roundCount = 0;

        // Clear symbols on all buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].clearCell();
            }
        }
    }
}
