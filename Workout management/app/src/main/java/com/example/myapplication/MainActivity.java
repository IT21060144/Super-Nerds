package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.ui.Diet.DietFrameFragment;
import com.example.myapplication.ui.Home.HomeFrameFragment;
import com.example.myapplication.ui.Profile.ProfileFrameFragment;
import com.example.myapplication.ui.Workout.WorkoutFrameFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    String appBarTitle = "Home";
    FragmentTransaction fragmentTransaction1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Fragment homeFragment = new HomeFrameFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, homeFragment);
        fragmentTransaction.commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;

            switch(item.getItemId()) {

                case R.id.nav_workout:
                    appBarTitle = "Workout";
                    fragment = new WorkoutFrameFragment();
                    break;

                case R.id.nav_diet:
                    appBarTitle = "Diet";
                    fragment = new DietFrameFragment();
                    break;

                case R.id.nav_profile:
                    appBarTitle = "Profile";
                    fragment = new ProfileFrameFragment();
                    break;

                case R.id.nav_home:
                default:
                    appBarTitle = "Home";
                    fragment = new HomeFrameFragment();
                    break;

            }

            fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.content, fragment);
            fragmentTransaction1.commit();

            return true;
        });
    }
}