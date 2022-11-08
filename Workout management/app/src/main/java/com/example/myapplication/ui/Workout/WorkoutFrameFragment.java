package com.example.myapplication.ui.Workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.utils.ViewPager2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class WorkoutFrameFragment extends Fragment implements com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    ArrayList<String> titles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.workout_content);
        tabLayout = getView().findViewById(R.id.tab_layout);

        titles = new ArrayList<>();
        titles.add("Program");
        titles.add("My Plans");
        titles.add("Explore");

        setViewPager2();

        new TabLayoutMediator(tabLayout, viewPager2, this).attach();

    }

    public void setViewPager2() {
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this.requireActivity());
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

        fragmentArrayList.add(new ProgramFragment());
        fragmentArrayList.add(new PlanFragment());
        fragmentArrayList.add(new ExploreFragment());

        viewPager2Adapter.setFragmentArrayList(fragmentArrayList);
        viewPager2.setAdapter(viewPager2Adapter);
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));
    }
}