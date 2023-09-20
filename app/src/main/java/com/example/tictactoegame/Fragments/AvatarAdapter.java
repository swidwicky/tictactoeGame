package com.example.tictactoegame.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tictactoegame.R;

import java.util.ArrayList;
import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder> {

    private List<Integer> avatarImages; // Use a list to store avatar resource IDs
    private LayoutInflater inflater;
    private int selectedPosition = -1; // To keep track of the selected avatar position

    public AvatarAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.avatarImages = getAvatarResourceList(); // Initialize the list with avatar resource IDs
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_avatar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int avatarResourceId = avatarImages.get(position);
        holder.avatarImageView.setImageResource(avatarResourceId);

        // Set a click listener to handle avatar selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition(); // Get the clicked position using getAdapterPosition
                setSelectedPosition(clickedPosition); // Set the selected position
                notifyDataSetChanged(); // Refresh the adapter to update the UI
            }
        });

        // Highlight the selected avatar
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.color.selected_avatar_bg); // Apply a selected background
        } else {
            holder.itemView.setBackgroundResource(android.R.color.transparent); // Remove background for unselected avatars
        }
    }


    @Override
    public int getItemCount() {
        return avatarImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
        }
    }

    // Method to get a list of avatar resource IDs
    private List<Integer> getAvatarResourceList() {
        List<Integer> resourceList = new ArrayList<>();
        resourceList.add(R.drawable.av1);
        resourceList.add(R.drawable.av2);
        resourceList.add(R.drawable.av3);
        resourceList.add(R.drawable.av4);
        // Add more resource IDs for your avatars as needed
        return resourceList;
    }

    // Method to get the selected avatar resource ID
    public int getSelectedAvatarResourceId() {
        if (selectedPosition >= 0 && selectedPosition < avatarImages.size()) {
            return avatarImages.get(selectedPosition);
        }
        return -1; // Return -1 if no avatar is selected
    }

    // Method to set the selected position
    private void setSelectedPosition(int position) {
        selectedPosition = position;
    }
}
