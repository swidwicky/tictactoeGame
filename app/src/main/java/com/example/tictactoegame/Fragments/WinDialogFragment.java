package com.example.tictactoegame.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tictactoegame.R;

public class WinDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the custom layout for the win message
        View view = LayoutInflater.from(getContext()).inflate(R.layout.win_dialog, null);

        // Customize the message (e.g., "Player X Wins!")
        TextView winMessageTextView = view.findViewById(R.id.winMessageTextView);
        winMessageTextView.setText("Player X Wins!"); // Customize this message as needed

        // Create and configure the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the "Close" button click if needed
                    }
                });

        return builder.create();
    }
}
