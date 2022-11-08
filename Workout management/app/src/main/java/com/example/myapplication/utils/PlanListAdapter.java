package com.example.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.WorkoutPlan;
import com.example.myapplication.ui.Workout.PlanInfoActivity;
import com.example.myapplication.ui.Workout.WorkoutFrameFragment;

import java.util.ArrayList;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.MyViewHolder> {

    Context context;
    ArrayList<WorkoutPlan> planArrayList;
    WorkoutFrameFragment workoutFrameFragment;

    public PlanListAdapter(Context context, ArrayList<WorkoutPlan> planArrayList) {
        this.context = context;
        this.planArrayList = planArrayList;
        this.workoutFrameFragment = new WorkoutFrameFragment();
    }

    @NonNull
    @Override
    public PlanListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.plans_item, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull PlanListAdapter.MyViewHolder holder, int position) {


        WorkoutPlan wp = planArrayList.get(position);
        holder.title.setText(wp.title);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                Intent intent = new Intent(activity, PlanInfoActivity.class );
                Log.i("details", "wp : " + wp);
                intent.putExtra("wp", wp);
                activity.startActivity(intent);

            }
        });

        holder.itemView.findViewById(R.id.edit_btn).setOnClickListener(view1 -> {
            AppCompatActivity activity = (AppCompatActivity) view1.getContext();
            Log.i("edit_btn", "Clicked favorite button");
        });
    }

    @Override
    public int getItemCount() {
        return planArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.TV_title);
        }
    }

}
