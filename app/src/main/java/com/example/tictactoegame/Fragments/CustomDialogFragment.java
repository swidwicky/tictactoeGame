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

public class CustomDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";

    public static CustomDialogFragment newInstance(String title, String message) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Get the title and message to display from arguments
        String title = getArguments().getString(ARG_TITLE);
        String message = getArguments().getString(ARG_MESSAGE);

        // Inflate the custom layout for the dialog
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_text_message, null);

        // Customize the dialog title and message
        TextView dialogTitleTextView = view.findViewById(R.id.dialogTitleTextView);
        dialogTitleTextView.setText(title);

        TextView dialogMessageTextView = view.findViewById(R.id.dialogMessageTextView);
        dialogMessageTextView.setText(message);

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
