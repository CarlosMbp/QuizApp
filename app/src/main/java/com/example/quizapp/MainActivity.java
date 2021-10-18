package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Pasamos al siguiente Intent
    public void Comenzar(View view){
        Intent Quiz = new Intent(this, QuizActivity.class);
        startActivity(Quiz);
    }
}