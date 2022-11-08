package com.example.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Workouts;
import com.example.myapplication.ui.Workout.ItemInfoActivity;
import com.example.myapplication.ui.Workout.WorkoutFrameFragment;

import java.util.ArrayList;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Workouts> planArrayList;
    WorkoutFrameFragment workoutFrameFragment;

    public WorkoutsAdapter(Context context, ArrayList<Workouts> planArrayList) {
        this.context = context;
        this.planArrayList = planArrayList;
        this.workoutFrameFragment = new WorkoutFrameFragment();
    }

    @NonNull
    @Override
    public WorkoutsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.woekout_recyclerview_item, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull WorkoutsAdapter.MyViewHolder holder, int position) {

        Workouts w = planArrayList.get(position);
        holder.title.setText(w.name);
        if(TextUtils.isEmpty(w.rest) || w.rest == null) {
            w.rest = "1";
        }if(TextUtils.isEmpty(w.sets) || w.sets == null) {
            w.sets = "3";
        }if(TextUtils.isEmpty(w.reps) || w.reps == null) {
            w.reps = "12";
        }if(TextUtils.isEmpty(w.variant) || w.variant == null) {
            w.variant = "Basic";
        }

        holder.rest.setText(w.rest + " min rest");
        holder.sets.setText(w.sets + " sets");
        holder.reps.setText(w.reps + " reps");
        holder.variant.setText(w.variant);

        switch(w.level) {
            case "beginner" : {
                holder.level.setText("🟢");
                break;
            }
            case "intermediate" : {
                holder.level.setText("🟠");
                break;
            }
            case "expert" : {
                holder.level.setText("🔴");
                break;
            }
            default : {
                holder.level.setText("");
                break;
            }
        }



        holder.itemView.setOnClickListener(view -> {

            AppCompatActivity activity = (AppCompatActivity)view.getContext();
            Intent intent = new Intent(activity, ItemInfoActivity.class );
            Log.i("details", "w : " + w);
            intent.putExtra("w", w);
            activity.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return planArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, time, sets, reps, variant, level, rest;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rest = itemView.findViewById(R.id.rest);
            sets = itemView.findViewById(R.id.sets);
            reps = itemView.findViewById(R.id.reps);
            variant = itemView.findViewById(R.id.variant);
            level = itemView.findViewById(R.id.level);
        }
    }

}
