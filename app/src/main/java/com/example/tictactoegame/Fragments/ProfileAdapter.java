package com.example.tictactoegame.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoegame.Activities.UserProfile;
import com.example.tictactoegame.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private List<UserProfile> userProfileList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(UserProfile userProfile);
    }

    public ProfileAdapter(List<UserProfile> userProfileList, OnItemClickListener listener) {
        this.userProfileList = userProfileList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_profile_list1, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        UserProfile userProfile = userProfileList.get(position);
        holder.bind(userProfile, listener);
    }

    @Override
    public int getItemCount() {
        return userProfileList.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatarImageView;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        UserProfile userProfile = userProfileList.get(position);
                        listener.onItemClick(userProfile);
                    }
                }
            });
        }

        public void bind(UserProfile userProfile, OnItemClickListener listener) {
            // Set the avatar image
            avatarImageView.setImageResource(userProfile.getAvatarResourceId());
        }
    }
}
