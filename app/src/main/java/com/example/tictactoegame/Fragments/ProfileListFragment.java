package com.example.tictactoegame.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoegame.Activities.UserProfile;
import com.example.tictactoegame.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileListFragment extends Fragment implements ProfileAdapter.OnItemClickListener {

    private Button backButton;
    private List<UserProfile> userProfileList = new ArrayList<>();
    private ProfileAdapter profileAdapter;
    private ImageView avatarImageView;
    private TextView usernameTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_list1, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.profileRecyclerView);
        backButton = view.findViewById(R.id.backButton);
        avatarImageView = view.findViewById(R.id.avatarImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize and set the adapter for the RecyclerView
        profileAdapter = new ProfileAdapter(userProfileList, this);
        recyclerView.setAdapter(profileAdapter);

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

        // Retrieve saved user profiles from SharedPreferences and add them to userProfileList
        loadUserProfilesFromSharedPreferences();

        return view;
    }

    private void loadUserProfilesFromSharedPreferences() {
        // Retrieve user profiles from SharedPreferences and add them to userProfileList
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);

        // Retrieve the number of user profiles saved (you might have stored this count)
        int profileCount = sharedPreferences.getInt("profileCount", 0);

        for (int i = 0; i < profileCount; i++) {
            // Retrieve each user profile's data by appending an index to the keys
            String username = sharedPreferences.getString("username_" + i, "");
            int avatarResourceId = sharedPreferences.getInt("avatarResourceId_" + i, -1);

            // Create a UserProfile object and add it to the list
            UserProfile userProfile = new UserProfile(username, avatarResourceId);
            userProfileList.add(userProfile);
        }

        // Notify the adapter that the data has changed
        profileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(UserProfile userProfile) {
        // Handle item click and update UI with selected user's data (username and avatar)
        avatarImageView.setImageResource(userProfile.getAvatarResourceId());
        usernameTextView.setText(userProfile.getUsername());
    }
}
