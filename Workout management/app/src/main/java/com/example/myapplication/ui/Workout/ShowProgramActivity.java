package com.example.myapplication.ui.Workout;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;

public class ShowProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_program);
    }

    TextView time = findViewById(R.id.TV_time);
    TextView level = findViewById(R.id.TV_level);
    TextView weeks = findViewById(R.id.TV_weeks);
    MaterialButton purchase_btn = findViewById(R.id.purchase_btn);



}