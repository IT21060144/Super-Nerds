package com.example.myapplication.ui.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class HomeFrameFragment extends Fragment {

    public HomeFrameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = getView().findViewById(R.id.tab_layout);

        getParentFragmentManager().beginTransaction().replace(R.id.home_content, new TodayFragment()).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch(tab.getPosition()) {

                    case 2:
                        getParentFragmentManager().beginTransaction().replace(R.id.home_content, new MealsFragment()).commit();
                        break;

                    case 1:
                        getParentFragmentManager().beginTransaction().replace(R.id.home_content, new ExerciseFragment()).commit();
                        break;

                    case 0:
                    default:
                        getParentFragmentManager().beginTransaction().replace(R.id.home_content, new TodayFragment()).commit();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}