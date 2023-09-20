package com.example.tictactoegame.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tictactoegame.R;
import com.example.tictactoegame.Activities.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    private EditText userNameEditText;
    private ImageView avatar1, avatar2, avatar3, avatar4;
    private Button saveButton;
    private Button backButton;
    private TextView profileDataTextView;
    private List<UserProfile> profileList = new ArrayList<>();

    private int selectedAvatarResourceId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        userNameEditText = view.findViewById(R.id.userNameEditText);
        avatar1 = view.findViewById(R.id.avatar1);
        avatar2 = view.findViewById(R.id.avatar2);
        avatar3 = view.findViewById(R.id.avatar3);
        avatar4 = view.findViewById(R.id.avatar4);
        saveButton = view.findViewById(R.id.saveButton);
        backButton = view.findViewById(R.id.backButton);
        profileDataTextView = view.findViewById(R.id.profileDataTextView);

        // Handle avatar selection from ImageView
        avatar1.setOnClickListener(v -> selectAvatar(R.drawable.av1));
        avatar2.setOnClickListener(v -> selectAvatar(R.drawable.av2));
        avatar3.setOnClickListener(v -> selectAvatar(R.drawable.av3));
        avatar4.setOnClickListener(v -> selectAvatar(R.drawable.av4));

        // Handle Save button click
        saveButton.setOnClickListener(v -> {
            String username = userNameEditText.getText().toString();

            if (username.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid username", Toast.LENGTH_SHORT).show();
            } else if (selectedAvatarResourceId == -1) {
                Toast.makeText(getContext(), "Please select an avatar", Toast.LENGTH_SHORT).show();
            } else {
                saveUserProfileData(username, selectedAvatarResourceId);
                UserProfile userProfile = new UserProfile(username, selectedAvatarResourceId);
                profileList.add(userProfile);
                displayUserProfileData();
                saveButton.setVisibility(View.GONE);
                navigateToProfileListFragment(profileList);
            }
        });

        // Handle Back button click
        backButton.setOnClickListener(v -> {
            navigateBack(); // Custom method to handle fragment navigation
        });

        return view;
    }

    private void selectAvatar(int resourceId) {
        selectedAvatarResourceId = resourceId;

        // Clear any previous selections and reset background colors
        avatar1.setSelected(false);
        avatar2.setSelected(false);
        avatar3.setSelected(false);
        avatar4.setSelected(false);

        // Reset background colors to the default
        avatar1.setBackgroundColor(Color.TRANSPARENT);
        avatar2.setBackgroundColor(Color.TRANSPARENT);
        avatar3.setBackgroundColor(Color.TRANSPARENT);
        avatar4.setBackgroundColor(Color.TRANSPARENT);

        // Highlight the selected avatar and change its background color
        if (resourceId == R.drawable.av1) {
            avatar1.setSelected(true);
            avatar1.setBackgroundColor(Color.GREEN); // You can change the color to your preference
        } else if (resourceId == R.drawable.av2) {
            avatar2.setSelected(true);
            avatar2.setBackgroundColor(Color.GREEN);
        } else if (resourceId == R.drawable.av3) {
            avatar3.setSelected(true);
            avatar3.setBackgroundColor(Color.GREEN);
        } else if (resourceId == R.drawable.av4) {
            avatar4.setSelected(true);
            avatar4.setBackgroundColor(Color.GREEN);
        }
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

    // Custom method to navigate back
    private void navigateBack() {
        // Replace the current fragment with the main menu fragment
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainMenuFragment);
        transaction.commit();
    }

    // Method to navigate to ProfileListFragment with the profileList
    private void navigateToProfileListFragment(List<UserProfile> profileList) {
        ProfileListFragment profileListFragment = new ProfileListFragment();

        // Pass the profileList as an argument to ProfileListFragment
        Bundle args = new Bundle();
        args.putParcelableArrayList("profileList", new ArrayList<>(profileList));
        profileListFragment.setArguments(args);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, profileListFragment);
        transaction.addToBackStack(null); // Add to back stack for proper navigation
        transaction.commit();
    }

    // Method to select an avatar and update the UI


}
