package com.example.tictactoegame.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tictactoegame.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserProfileFragment extends Fragment {

    private RecyclerView avatarRecyclerView;
    private EditText userNameEditText;
    private Button saveButton;
    private Button backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Initialize UI elements
        userNameEditText = view.findViewById(R.id.userNameEditText);
        avatarRecyclerView = view.findViewById(R.id.avatarSelectionRecyclerView);
        saveButton = view.findViewById(R.id.saveButton);
        backButton = view.findViewById(R.id.backButton);

        // Set up RecyclerView
        AvatarAdapter adapter = new AvatarAdapter(getContext(), avatarImages); // Replace avatarImages with your avatar data source.
        avatarRecyclerView.setAdapter(adapter);
        avatarRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3)); // 3 columns

        // Set click listeners for buttons
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle save button click
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                Fragment mainMenuFragment = new MainMenuFragment(); // Replace with the actual name of your main_menu fragment class.
                fragmentManager.beginTransaction()
                        .replace(R.id.UserProfileContainer, mainMenuFragment)
                        .commit();
            }
        });


        return view;
    }
}
