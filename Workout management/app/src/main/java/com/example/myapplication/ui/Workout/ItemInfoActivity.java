package com.example.myapplication.ui.Workout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.WorkoutPlan;
import com.example.myapplication.model.Workouts;
import com.example.myapplication.utils.WorkoutsAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Map;

public class ItemInfoActivity extends AppCompatActivity {

    MaterialButton purchase_btn;
    TextView time, level, weeks;
    ArrayList<Workouts> workoutsList;
    WorkoutsAdapter workoutsAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        Intent intent = getIntent();
        WorkoutPlan wp = (WorkoutPlan) intent.getSerializableExtra("wp");

        purchase_btn = findViewById(R.id.purchase_btn);
        time = findViewById(R.id.TV_time);
        level = findViewById(R.id.TV_exp_level);
        weeks = findViewById(R.id.TV_weeks);
        final TextView desc = findViewById(R.id.desc);

        time.setText(String.valueOf(wp.totalTime) + " mins");
        level.setText((wp.level));
        weeks.setText(String.valueOf(wp.weeks) + " weeks");
        desc.setText(wp.desc);
        String purchase_btn_txt = "Purchase - " + wp.getPriceText();
        purchase_btn.setText(purchase_btn_txt);

        workoutsList = new ArrayList<>();
        workoutsAdapter = new WorkoutsAdapter(this, workoutsList);

        recyclerView = findViewById(R.id.workoutsView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(workoutsAdapter);


        ArrayList<Map<String, String>> workouts = new ArrayList<>();

        for(int i = 0; i< wp.workouts.size(); i++) {

            workouts.add(wp.workouts.get(i));

        }

        for(Map<String, String> w : workouts) {
            Workouts ws = new Workouts();
            ws.name = w.get("name");
            ws.rest = w.get("rest");
            ws.reps = w.get("reps");
            ws.sets= w.get("sets");
            ws.variant = w.get("variant");
            ws.level= w.get("level");

            workoutsList.add(ws);
            workoutsAdapter.notifyDataSetChanged();

        }


        purchase_btn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);

            builder.setTitle("Confirm Purchase of plan?");
            builder.setCancelable(true);

            builder.setPositiveButton("Purchase", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // purchase item
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.show();

        });
    }
}