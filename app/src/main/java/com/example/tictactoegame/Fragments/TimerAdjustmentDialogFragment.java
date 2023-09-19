package com.example.tictactoegame.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class TimerAdjustmentDialogFragment extends DialogFragment {

    private OnTimerAdjustedListener listener;

    public interface OnTimerAdjustedListener {
        void onTimerAdjusted(int newTimeInSeconds);
    }

    public void setOnTimerAdjustedListener(OnTimerAdjustedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Adjust Timer");
        builder.setMessage("Enter the new timer duration in seconds:");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int newTimeInSeconds = Integer.parseInt(input.getText().toString());
                    if (listener != null) {
                        listener.onTimerAdjusted(newTimeInSeconds);
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

    // Show the dialog using a method like this in your GameFragment
    public void showTimerAdjustmentDialog(FragmentManager fragmentManager) {
        TimerAdjustmentDialogFragment dialogFragment = new TimerAdjustmentDialogFragment();
        dialogFragment.show(fragmentManager, "timer_adjustment_dialog");
    }
}
