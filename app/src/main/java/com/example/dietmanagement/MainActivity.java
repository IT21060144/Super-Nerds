package com.example.dietmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    EditText InputName;
    EditText InputEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        InputName = findViewById(R.id.editTextTextPersonName3);
        InputEmail = findViewById(R.id.editTextTextPersonName4);

        String msg = InputName.getText().toString();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        String msg1 = InputEmail.getText().toString();
        Toast.makeText(this, msg1, Toast.LENGTH_SHORT).show();
    }

        public void clickToViewToast1(View view){

            String msg = InputName.getText().toString();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();


    }

    public void clickToViewToast2(View view){


        String msg1 = InputEmail.getText().toString();
        Toast.makeText(this, msg1, Toast.LENGTH_SHORT).show();
    }

    public void onButtonClick1( View v){
        if(v.getId() == R.id.btnProfile)
        {
           Intent i = new Intent(MainActivity.this,profile.class);
           startActivity(i) ;
        }

    }

    public void onButtonClick2(View v){
        if(v.getId() == R.id.btnGoal)
        {
            Intent i = new Intent(MainActivity.this,goal.class);
            startActivity(i) ;
        }

    }

    public void onButtonClick3( View v){
        if(v.getId() == R.id.btnMenu)
        {
            Intent i = new Intent(MainActivity.this,menu.class);
            startActivity(i) ;
        }

    }

    public void onButtonClick4( View v){
        if(v.getId() == R.id.btnHealth)
        {
            Intent i = new Intent(MainActivity.this,health.class);
            startActivity(i) ;
        }

    }
}