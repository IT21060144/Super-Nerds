package com.example.myapplication.ui.Workout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.WorkoutPlan;
import com.example.myapplication.model.Workouts;
import com.example.myapplication.utils.WorkoutInputAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CreateWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<Workouts> workoutsList = new ArrayList<>();

        final WorkoutInputAdapter workoutInputAdapter = new WorkoutInputAdapter(this, workoutsList);

        final RecyclerView recyclerView = findViewById(R.id.input_workout_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(workoutInputAdapter);

        Button addWorkout = findViewById(R.id.add_workout);

        addWorkout.setOnClickListener(view -> {
            TextInputEditText name = findViewById(R.id.wname);
            TextInputEditText level = findViewById(R.id.wlevel);
            TextInputEditText time = findViewById(R.id.wtime);

            Workouts newWorkout = new Workouts();
            newWorkout.name = name.getText().toString();
            newWorkout.time = time.getText().toString();
            newWorkout.level = level.getText().toString();

            workoutsList.add(newWorkout);
            workoutInputAdapter.notifyDataSetChanged();

        });

        String[] freq_list = {"Everyday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayList<Integer> selected_freq_list = new ArrayList<>();
        final TextView addToProgram_btn = findViewById(R.id.select_freq);
        boolean[] selected_freq = new boolean[freq_list.length];

        addToProgram_btn.setOnClickListener(view12 -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
            builder.setTitle("Select Workout-plan frequency");
            builder.setCancelable(true);

            builder.setMultiChoiceItems(freq_list, selected_freq, (dialogInterface, i, b) -> {
                if (b) {
                    if(i == 0) {
                        for(int x = 1; x < freq_list.length; x++) {
                            selected_freq[x] = false;
                            ((AlertDialog)dialogInterface).getListView().setItemChecked(x, false);
                            selected_freq_list.remove(Integer.valueOf(x));
                        }
                        selected_freq_list.add(0);

                    } else if(selected_freq[0]) {
                        selected_freq[0] = false;
                        ((AlertDialog)dialogInterface).getListView().setItemChecked(0, false);
                        selected_freq_list.add(i);
                        selected_freq_list.remove(0);

                    }else {
                        selected_freq_list.add(i);
                    }

                    Collections.sort(selected_freq_list);

                } else {
                    selected_freq_list.remove(Integer.valueOf(i));
                }
            });

            builder.setPositiveButton("Add to Program", (dialogInterface, i) -> dialogInterface.dismiss());

            builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

            builder.show();
        });


        final MaterialButton add_workoutplan = findViewById(R.id.add_workoutplan);

        add_workoutplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText title = findViewById(R.id.wtitle);
                WorkoutPlan wp = new WorkoutPlan();
                wp.title = title.getText().toString();

                for(Workouts w : workoutsList) {
                    HashMap<String, String> wmap = new HashMap<String, String>();
                    wmap.put("name", w.name);
                    wmap.put("time", w.time);
                    wmap.put("level", w.level);

                    Log.i("TAG", "onClick: " + wp.workouts);
                    wp.workouts.add(wmap);

                }


                if(TextUtils.isEmpty(title.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter a title", Toast.LENGTH_SHORT).show();
                } else {

                    db.collection("MyWorkoutPlans").document().set(wp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CreateWorkoutActivity.this, "Plan Created successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}