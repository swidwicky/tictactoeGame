package com.example.tictactoegame.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.tictactoegame.R;

import java.util.ArrayList;
import java.util.List;

public class AvatarAdapter extends BaseAdapter {

    private List<Integer> avatarImages;
    private LayoutInflater inflater;
    private int selectedPosition = -1;
    private Context context;

    public AvatarAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.avatarImages = getAvatarResourceList();
    }

    @Override
    public int getCount() {
        return avatarImages.size();
    }

    @Override
    public Object getItem(int position) {
        return avatarImages.get(position);
    }

    public int getAvatarItem(int position) {
        return avatarImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_avatar, null);
            holder = new ViewHolder();
            holder.avatarImageView = convertView.findViewById(R.id.avatarImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int avatarResourceId = avatarImages.get(position);
        holder.avatarImageView.setImageResource(avatarResourceId);

        // Highlight the selected avatar
        if (selectedPosition == position) {
            holder.avatarImageView.setBackgroundResource(R.drawable.grid_divider); // Apply a selected border
        } else {
            holder.avatarImageView.setBackgroundResource(0); // Remove border for unselected avatars
        }

        return convertView;
    }

    public class ViewHolder {
        ImageView avatarImageView;
    }

    private List<Integer> getAvatarResourceList() {
        List<Integer> resourceList = new ArrayList<>();
        resourceList.add(R.drawable.av1);
        resourceList.add(R.drawable.av2);
        resourceList.add(R.drawable.av3);
        resourceList.add(R.drawable.av4);
        // Add more resource IDs for your avatars as needed
        return resourceList;
    }

    public int getSelectedAvatarResourceId() {
        if (selectedPosition >= 0 && selectedPosition < avatarImages.size()) {
            return avatarImages.get(selectedPosition);
        }
        return -1;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }
}