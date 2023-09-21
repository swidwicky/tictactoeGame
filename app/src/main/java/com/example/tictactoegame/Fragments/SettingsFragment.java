package com.example.tictactoegame.Fragments; // Replace with your actual package name

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tictactoegame.R;

public class SettingsFragment extends Fragment {

    // Define the View elements
    private TextView settingsTextView;
    private Button gameBoardSettButton;
    private Button winConditionsButton;
    private Button playerMarkerButton;
    private Button backButton;
    private Button homeButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize the View elements
        settingsTextView = view.findViewById(R.id.SettingsTextView);
        gameBoardSettButton = view.findViewById(R.id.GameBoardSettingsButton);
        winConditionsButton = view.findViewById(R.id.WinConditionsButton);
        playerMarkerButton = view.findViewById(R.id.playerMarkerButton);
        backButton = view.findViewById(R.id.backButton);
        homeButton = view.findViewById(R.id.homeButton);
        gameBoardSettButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               // Handle Win Conditions button click
               // You can navigate to the WinConditionsFragment or perform the desired action here
               // Create an instance of the WinConditionsFragment
               GameFragment gameFragment = new GameFragment();

               FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

               // Replace the current fragment with WinConditionsFragment
               transaction.replace(R.id.fragment_container, gameFragment);
               transaction.addToBackStack(null);
               // Commit the transaction
               transaction.commit();
           }
       });

        winConditionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Win Conditions button click
                // You can navigate to the WinConditionsFragment or perform the desired action here
                // Create an instance of the WinConditionsFragment
                WinConditionsFragment winConditionsFragment = new WinConditionsFragment();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace the current fragment with WinConditionsFragment
                transaction.replace(R.id.fragment_container, winConditionsFragment);
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        });


        playerMarkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle PLAYER MARKER Conditions button click
                // Create an instance of the WinConditionsFragment
                PlayerMarkerFragment playerMarkerFrag = new PlayerMarkerFragment();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace the current fragment with WinConditionsFragment
                transaction.replace(R.id.fragment_container, playerMarkerFrag);
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        });

        backButton.setOnClickListener(v -> {
            navigateBack(); // Custom method to handle fragment navigation
        });

        homeButton.setOnClickListener( v -> {
            navigateBack();
        });

        return view;
    }

    private void navigateBack() {
        // Replace the current fragment with the main menu fragment
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainMenuFragment);
        transaction.commit();
    }
}
