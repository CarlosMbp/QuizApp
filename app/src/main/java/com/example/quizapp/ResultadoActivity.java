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

        /*
        Al crearse la activity sabemos, que se le ha enviado un "extra" Puntuación, el cúal contiene la puntuación final
        "Cogemos" la puntuación y la mostramos por pantalla
        */
        int puntuacion= getIntent().getExtras().getInt("Puntuación");
        txtPuntuacionF.setText("Puntuación final: "+ puntuacion);
    }


    public void Volver(View view){

        //Lanzamos una nueva activity MainActivity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //Finalizamos la activity actual
        finish();
        startActivity(intent);
    }


    public void Reiniciar(View view){

        //Lanzamos una nueva activity QuizActivity
        Intent Quiz = new Intent(this, QuizActivity.class);
        //Finalizamos la activity actual
        finish();
        startActivity(Quiz);
    }
}