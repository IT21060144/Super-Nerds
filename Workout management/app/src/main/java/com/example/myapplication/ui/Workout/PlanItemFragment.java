package com.example.myapplication.ui.Workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class PlanItemFragment extends Fragment {

    MaterialButton addToProgram_btn, remove_plan_btn;
    ArrayList<Integer> selected_freq_list;


    public PlanItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView time = view.findViewById(R.id.TV_time);
        TextView weeks = view.findViewById(R.id.TV_weeks_planitem);
        TextView level = view.findViewById(R.id.TV_exp_level);


        String[] freq_list = {"Everyday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        selected_freq_list = new ArrayList<>();
        addToProgram_btn = view.findViewById(R.id.addToProgram_btn);
        remove_plan_btn = view.findViewById(R.id.remove_btn);
        boolean[] selected_freq = new boolean[freq_list.length];

        remove_plan_btn.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.MyAlertDialogTheme_danger);
            builder.setTitle("Do you want to delete plan?");
            builder.setCancelable(true);

            builder.setPositiveButton("Delete", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

            builder.show();
        });


    }
}