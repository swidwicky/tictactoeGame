package com.example.tictactoegame.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoegame.R;

public class avatarAdapter extends RecyclerView.Adapter<avatarAdapter.ViewHolder> {

    private int[] avatarImages = {
            R.drawable.av1,
            R.drawable.av2,
            R.drawable.av3,
            R.drawable.av4,
            R.drawable.av5,
            R.drawable.av6,
            R.drawable.av7,
            R.drawable.av8,
            // Add more resource IDs for your avatar images as needed
    };

    private LayoutInflater inflater;

    public avatarAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_avatar, parent, false); // Use item_avatar.xml here
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int avatarResourceId = avatarImages[position];
        holder.avatarImageView.setImageResource(avatarResourceId);
    }

    @Override
    public int getItemCount() {
        return avatarImages.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
        }
    }
}
