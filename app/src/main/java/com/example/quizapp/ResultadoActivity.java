package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    private TextView txtPuntuacionF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        txtPuntuacionF=findViewById(R.id.txtPuntuacionF);

        int puntuacion= getIntent().getExtras().getInt("Puntuación");
        txtPuntuacionF.setText("Puntuación final: "+ puntuacion);
    }


    public void Volver(View view){

        //Lanzamos una nueva activity MainActivity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    public void Reiniciar(View view){
        Intent Quiz = new Intent(this, QuizActivity.class);
        startActivity(Quiz);
    }
}