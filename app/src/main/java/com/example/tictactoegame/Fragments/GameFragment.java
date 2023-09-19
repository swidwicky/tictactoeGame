package com.example.tictactoegame.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tictactoegame.R;
import com.example.tictactoegame.TicTacToeSquare;

public class GameFragment extends Fragment {

    private TicTacToeSquare[][] buttons = new TicTacToeSquare[3][3];
    private boolean player1Turn = true; // Player 1 is X, Player 2 is O
    private int roundCount = 0;
    private CustomDialogFragment winDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retain this fragment across configuration changes
    }

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
        // Restore game state if available
        if (savedInstanceState != null) {
            player1Turn = savedInstanceState.getBoolean("player1Turn");
            roundCount = savedInstanceState.getInt("roundCount");
            // Restore other game-related data as needed
        }
        // Add logic for game modes (2-player and vs. AI)
        // Add a back button click listener
        Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainMenuFragment();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the game state to the bundle
        outState.putBoolean("player1Turn", player1Turn);
        outState.putInt("roundCount", roundCount);
        // Save other game-related data as needed
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

        // Check for a win after each move
        if (checkWinCondition()) {
            // A player has won, handle the win
            String winningPlayer = player1Turn ? "Player 1 (X)" : "Player 2 (O)";
            playerWins(winningPlayer);
        } else if (roundCount == 9) {
            // It's a draw
            declareDraw();
        } else {
            // Switch turns
            player1Turn = !player1Turn;
        }
    }


    private boolean checkWinCondition() {
        // Check rows, columns, and diagonals for a win

        for (int i = 0; i < 3; i++) {
            // Check rows
            if (buttons[i][0].isFilled() && buttons[i][0].getSymbol() == buttons[i][1].getSymbol() && buttons[i][0].getSymbol() == buttons[i][2].getSymbol()) {
                return true;
            }
            // Check columns
            if (buttons[0][i].isFilled() && buttons[0][i].getSymbol() == buttons[1][i].getSymbol() && buttons[0][i].getSymbol() == buttons[2][i].getSymbol()) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].isFilled() && buttons[0][0].getSymbol() == buttons[1][1].getSymbol() && buttons[0][0].getSymbol() == buttons[2][2].getSymbol()) {
            return true;
        }
        if (buttons[0][2].isFilled() && buttons[0][2].getSymbol() == buttons[1][1].getSymbol() && buttons[0][2].getSymbol() == buttons[2][0].getSymbol()) {
            return true;
        }

        return false; // No winner yet
    }


    private void declareDraw() {
        String drawMessage = "It's a Draw!";
        CustomDialogFragment drawDialog = CustomDialogFragment.newInstance("DRAW", drawMessage);
        drawDialog.show(getFragmentManager(), "draw_dialog");
        resetGame();
    }

    private void playerWins(String player) {
        String winMessage = player + " Wins!";
        CustomDialogFragment winDialog = CustomDialogFragment.newInstance("WON", winMessage);
        winDialog.show(getFragmentManager(), "win_dialog");
        resetGame();
    }

    private void resetGame() {
        // Implement logic to reset the game
        player1Turn = true;
        roundCount = 0;
        //HELLO

        // Clear symbols on all buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].clearCell();
            }
        }
    }

    private void navigateToMainMenuFragment() {
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainMenuFragment);
        transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
        transaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check if the orientation has changed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Handle landscape orientation
            // For example, update UI elements or perform actions specific to landscape mode
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Handle portrait orientation
            // For example, update UI elements or perform actions specific to portrait mode
        }

        // Retain the current fragment without navigating back to the main menu
        // You can use FragmentTransaction to reattach the current fragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.attach(this);
        transaction.commit();

        // Adjust text size and button size based on the orientation
        Button backButton = requireView().findViewById(R.id.backButton);
        float textSizeInSp = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) ? 8 : 20; // Adjust text size as needed
        backButton.setTextSize(textSizeInSp);

        // Update button's layout parameters to maintain its size
        ViewGroup.LayoutParams params = backButton.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        backButton.setLayoutParams(params);
    }


}
