package com.example.tictactoegame.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tictactoegame.AIPlayer;
import com.example.tictactoegame.R;
import com.example.tictactoegame.TicTacToeSquare;

import java.util.Locale;

public class GameFragment extends Fragment {

    private TicTacToeSquare[][] buttons = new TicTacToeSquare[3][3];
    private boolean player1Turn = true; // Player 1 is X, Player 2 is O
    private int roundCount = 0;
    private CustomDialogFragment winDialog;
    private AIPlayer aiPlayer;
    private boolean isAIMode = false; // Flag to indicate AI mode

    // Timer variables
    private CountDownTimer timer;
    private TextView timerTextView;

    private int secondsRemaining = 60; // Initial timer duration in seconds

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retain this fragment across configuration changes
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        timerTextView = view.findViewById(R.id.timerTextView);
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

        // Find the TextView for displaying the game mode
        TextView modeTextView = view.findViewById(R.id.modeTextView);

        // Find the reset button and add a click listener to it
        Button resetButton = view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        // Add logic for game modes (2-player and vs. AI)
        Button switchModeButton = view.findViewById(R.id.switchModeButton);
        switchModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchGameMode();
                // Update the game mode text when switching modes
                updateGameModeText(modeTextView);
            }
        });

        // Initialize the AI player
        aiPlayer = new AIPlayer();

        // Set the initial game mode text
        updateGameModeText(modeTextView);

        // Add a back button click listener
        Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainMenuFragment();
            }
        });

        // Find the timerTextView for displaying the timer
        final TextView timerTextView = view.findViewById(R.id.timerTextView);

        // Add a button to adjust the timer duration
        Button adjustTimerButton = view.findViewById(R.id.adjustTimerButton);
        adjustTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimerAdjustmentDialog(timerTextView);
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

            // If in AI mode and it's AI's turn
            if (isAIMode && !player1Turn) {
                aiPlayer.makeMove(buttons);
                roundCount++;
                player1Turn = true; // Switch back to human player's turn

                // Check for a win after AI's move
                if (checkWinCondition()) {
                    String winningPlayer = "AI (O)";
                    playerWins(winningPlayer);
                } else if (roundCount == 9) {
                    declareDraw();
                }
            }
        }

        // Reset the timer after a move
        startTimer();
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

        // Clear symbols on all buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].clearCell();
            }
        }

        // Reset the timer
        startTimer();
    }

    private void navigateToMainMenuFragment() {
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainMenuFragment);
        transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
        transaction.commit();
    }

    private void switchGameMode() {
        // Switch between 2-player and AI mode
        isAIMode = !isAIMode;
        resetGame();
    }

    // Add this method to update the game mode text
    private void updateGameModeText(TextView modeTextView) {
        if (isAIMode) {
            modeTextView.setText("AI vs. Player Mode");
        } else {
            modeTextView.setText("Player vs. Player Mode");
        }
    }

    private void openTimerAdjustmentDialog(final TextView timerTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Adjust Timer");
        builder.setMessage("Enter the new timer duration (in seconds):");

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText = input.getText().toString();
                if (!inputText.isEmpty()) {
                    int newTimeInSeconds = Integer.parseInt(inputText);
                    // Handle the new time (e.g., update the timer duration)
                    if (newTimeInSeconds > 0) {
                        secondsRemaining = newTimeInSeconds;
                        updateTimerDisplay(timerTextView, secondsRemaining * 1000); // Update the displayed timer
                    } else {
                        // Handle invalid input (e.g., show an error message)
                        Toast.makeText(requireContext(), "Invalid input. Timer not updated.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle empty input (e.g., show an error message)
                    Toast.makeText(requireContext(), "Please enter a valid value.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // Close the dialog
            }
        });

        builder.show();
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel(); // Cancel the previous timer
        }

        // Create a new timer with the specified duration
        timer = new CountDownTimer(secondsRemaining * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                secondsRemaining = (int) (millisUntilFinished / 1000);
                updateTimerDisplay(timerTextView, millisUntilFinished); // Update the timer display
            }

            public void onFinish() {
                // Timer has finished, handle the timeout
                handleTimeout();
            }
        }.start();
    }

    private void updateTimerDisplay(TextView timerTextView, long timeLeftInMillis) {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeString = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeString);
    }

    private void handleTimeout() {
        // Handle the timer timeout (e.g., declare a draw or end the game)
        // You can implement your logic here
        // In this example, we'll declare a draw
        declareDraw();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel(); // Cancel the timer when the fragment is destroyed
        }
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
