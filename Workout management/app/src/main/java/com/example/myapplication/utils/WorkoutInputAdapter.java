package com.example.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Workouts;
import com.example.myapplication.ui.Workout.WorkoutFrameFragment;

import java.util.ArrayList;

public class WorkoutInputAdapter extends RecyclerView.Adapter<WorkoutInputAdapter.MyViewHolder> {

    Context context;
    ArrayList<Workouts> planArrayList;
    WorkoutFrameFragment workoutFrameFragment;

    public WorkoutInputAdapter(Context context, ArrayList<Workouts> planArrayList) {
        this.context = context;
        this.planArrayList = planArrayList;
        this.workoutFrameFragment = new WorkoutFrameFragment();
    }

    @NonNull
    @Override
    public WorkoutInputAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.input_workout_list_item, parent, false);



        return new MyViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull WorkoutInputAdapter.MyViewHolder holder, int position) {


        Workouts w = planArrayList.get(position);

        switch(w.level) {
            case "beginner" : {
                holder.name.setText("🟢  " + w.name);
                break;
            }
            case "intermediate" : {
                holder.name.setText("🟠  " + w.name);
                break;
            }
            case "expert" : {
                holder.name.setText("🔴  " + w.name);
                break;
            }
            default : {
                holder.name.setText("" + w.name);
                break;
            }
        }

        holder.time.setText(w.time + " mins");

    }

    @Override
    public int getItemCount() {
        return planArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, time, level;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            level = itemView.findViewById(R.id.level);
        }
    }

}
