package com.example.tictactoegame.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.example.tictactoegame.Fragments.MainMenuFragment;
import com.example.tictactoegame.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the MainMenuFragment into the container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MainMenuFragment())
                .commit();
    }
}
