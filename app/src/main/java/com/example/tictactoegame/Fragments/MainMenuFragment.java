package com.example.tictactoegame.Fragments;

import android.os.Bundle;

import androidx.fragment.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.tictactoegame.R;

public class MainMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // Find the Start Game button
        Button startGameButton = view.findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameFragment gameFragment = new GameFragment();

                // Replace the current fragment with the GameFragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, gameFragment) // Use the ID of your fragment container
                        .addToBackStack(null) // Optionally, add to the back stack for back navigation
                        .commit();
            }
        });

        // Find the Gameplay Tracker button
        Button gameTrackerButton = view.findViewById(R.id.GamePlayTrackerButton);
        gameTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        // Find the User Profile button
        Button userProfileButton = view.findViewById(R.id.UserProfileButton);
        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileFragment userProfileFragmentFragment = new UserProfileFragment();

                // Replace the current fragment with the GameFragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, userProfileFragmentFragment) // Use the ID of your fragment container
                        .addToBackStack(null) // Optionally, add to the back stack for back navigation
                        .commit();
            }
        });

        return view;
    }
}
