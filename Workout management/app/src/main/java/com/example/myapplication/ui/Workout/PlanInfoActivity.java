package com.example.myapplication.ui.Workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.WorkoutPlan;
import com.example.myapplication.model.Workouts;
import com.example.myapplication.utils.WorkoutsAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class PlanInfoActivity extends AppCompatActivity {

    MaterialButton purchase_btn;
    TextView time, level, weeks;
    ArrayList<Workouts> workoutsList;
    WorkoutsAdapter workoutsAdapter;
    RecyclerView recyclerView;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_info);

        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        WorkoutPlan wp = (WorkoutPlan) intent.getSerializableExtra("wp");

        Button delete_btn = findViewById(R.id.delete_btn);
        TextView titletxt = findViewById(R.id.TV_title);
        titletxt.setText(wp.title);

        workoutsList = new ArrayList<>();
        workoutsAdapter = new WorkoutsAdapter(this, workoutsList);

        recyclerView = findViewById(R.id.workoutsView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(workoutsAdapter);

        ArrayList<Map<String, String>> workouts = new ArrayList<>();

        if (wp.workouts != null) {
            for (int i = 0; i < wp.workouts.size(); i++) {

                workouts.add(wp.workouts.get(i));

            }
        }


        for (Map<String, String> w : workouts) {
            Workouts ws = new Workouts();
            ws.name = w.get("name");
            ws.time = w.get("time");
            ws.level = w.get("level");

            workoutsList.add(ws);

            workoutsAdapter.notifyDataSetChanged();

        }

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("MyWorkoutPlans").document("Kr8fvwPoXPX9A4iBPVck").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PlanInfoActivity.this, "Plan Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}