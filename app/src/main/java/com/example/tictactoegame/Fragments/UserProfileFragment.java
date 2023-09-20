package com.example.tictactoegame.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoegame.R;

public class UserProfileFragment extends Fragment {

    private EditText userNameEditText;
    private RecyclerView avatarSelectionRecyclerView;
    private Button saveButton;
    private Button backButton;
    private TextView profileDataTextView;
    private int selectedAvatarResourceId = -1; // Added to store selected avatar resource ID

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        userNameEditText = view.findViewById(R.id.userNameEditText);
        avatarSelectionRecyclerView = view.findViewById(R.id.avatarSelectionRecyclerView);
        saveButton = view.findViewById(R.id.saveButton);
        backButton = view.findViewById(R.id.backButton);
        profileDataTextView = view.findViewById(R.id.profileDataTextView);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        avatarSelectionRecyclerView.setLayoutManager(layoutManager);

        // Create and set the adapter for the RecyclerView
        AvatarAdapter avatarAdapter = new AvatarAdapter(requireContext());
        avatarSelectionRecyclerView.setAdapter(avatarAdapter);

        // Handle Save button click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input (username)
                String username = userNameEditText.getText().toString();
                selectedAvatarResourceId = avatarAdapter.getSelectedAvatarResourceId();

                if (username.isEmpty()) {
                    // Show an error message or toast to inform the user
                    Toast.makeText(getContext(), "Please enter a valid username", Toast.LENGTH_SHORT).show();
                } else if (selectedAvatarResourceId == -1) {
                    // Show an error message or toast to inform the user that they need to select an avatar
                    Toast.makeText(getContext(), "Please select an avatar", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the user profile data to SharedPreferences
                    saveUserProfileData(username, selectedAvatarResourceId);
                    // Display the saved data in profileDataTextView
                    displayUserProfileData();
                    // Hide the save button
                    saveButton.setVisibility(View.GONE);
                }
            }
        });

        // Handle Back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if there is a previous fragment in the back stack
                if (getParentFragmentManager() != null && getParentFragmentManager().getBackStackEntryCount() > 0) {
                    getParentFragmentManager().popBackStack(); // Pop the back stack to go back to the previous fragment
                } else {
                    // If there's no previous fragment, you can handle it as per your app's logic
                    // For example, you might want to navigate to a different screen or exit the app.
                }
            }
        });

        return view;
    }

    // Method to save user profile data to SharedPreferences
    private void saveUserProfileData(String username, int selectedAvatarResourceId) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putInt("avatarResourceId", selectedAvatarResourceId);
        editor.apply();
    }

    // Method to display saved user profile data in profileDataTextView
    private void displayUserProfileData() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        int avatarResourceId = sharedPreferences.getInt("avatarResourceId", -1);

        // Display the saved data in profileDataTextView
        String userProfileText = "Username: " + username + "\nAvatar Resource ID: " + avatarResourceId;
        profileDataTextView.setText(userProfileText);
    }
}
