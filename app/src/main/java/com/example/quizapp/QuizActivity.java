package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private List<Pregunta> listaPreguntas;
    private TextView txtPregunta,txtPuntuacion,txtIdPregunta,txtTiempo;
    private RadioGroup radioGroup;
    private RadioButton rdb1,rdb2,rdb3,rdb4;
    private Button bttSiguiente,bttReiniciar;

    private Pregunta preguntaActual;

    int numPreguntas;
    int contPreguntas=0;
    int puntuacion=0;

    CountDownTimer contTiempo;

    ColorStateList dfRbColor;
    boolean contestada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtPregunta=findViewById(R.id.txtPregunta);
        txtPuntuacion=findViewById(R.id.txtPuntuacionF);
        txtIdPregunta=findViewById(R.id.txtIdPregunta);
        txtTiempo=findViewById(R.id.txtTiempo);

        radioGroup= findViewById(R.id.radioGroup);
        rdb1=findViewById(R.id.rdbOpcion1);
        rdb2=findViewById(R.id.rdbOpcion2);
        rdb3=findViewById(R.id.rdbOpcion3);
        rdb4=findViewById(R.id.rdbOpcion4);
        bttSiguiente=findViewById(R.id.bttSiguiente);
        bttReiniciar=findViewById(R.id.bttReiniciar);

        listaPreguntas = new ArrayList<>();

        dfRbColor = rdb1.getTextColors();

        anadirPreguntas();
        numPreguntas= listaPreguntas.size();

        mostrarSiguientePregunta();
    }

    //Pasamos a la siguiente pregunta
    public void siguiente(View view){
        if (contestada==false){
            if (rdb1.isChecked() || rdb2.isChecked() || rdb3.isChecked() || rdb4.isChecked()){
                comprobarRespuesta();
                contTiempo.cancel();
            }else{
                Toast.makeText(QuizActivity.this,"Seleccione una de las respuestas",Toast.LENGTH_SHORT).show();
            }
        }else{
            mostrarSiguientePregunta();
        }
    }

    //Boton para lanzar reiniciar la activity
    public void reiniciar(View view){
        Intent Quiz = getIntent();
        finish();
        startActivity(Quiz);


    }

    private void comprobarRespuesta() {

        contestada=true;
        //Obtengo el radioButton seleccionado
        RadioButton rbdSeleccionado = findViewById(radioGroup.getCheckedRadioButtonId());
        //Veo su index, su posición en el radioGroup + 1 (empieza desde 0)
        int opcionSeleccionada= radioGroup.indexOfChild(rbdSeleccionado)+1;

        if(opcionSeleccionada == preguntaActual.getOpcionCorrecta()){
            puntuacion+=3;
            txtPuntuacion.setText("Puntuacion: "+puntuacion);
        }else{
            puntuacion-=2;
            txtPuntuacion.setText("Puntuacion: "+puntuacion);
            bttReiniciar.setVisibility(View.VISIBLE);
        }

        /*Si la respuesta es correcta el radioButton correspondiente se resaltará en verde, en caso contrario
          se resaltará en rojo*/
        mostrarColor();

        //Comprobamos si nos encontramos en la ultima pregunta

        if(contPreguntas<numPreguntas){
            bttSiguiente.setText("Siguiente");
        }else{
            bttSiguiente.setText("Terminar");
        }
    }

    private void mostrarColor() {

        rdb1.setTextColor(Color.RED);
        rdb2.setTextColor(Color.RED);
        rdb3.setTextColor(Color.RED);
        rdb4.setTextColor(Color.RED);

        switch (preguntaActual.getOpcionCorrecta()){
            case 1:
                rdb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rdb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rdb3.setTextColor(Color.GREEN);
                break;
            case 4:
                rdb4.setTextColor(Color.GREEN);
                break;
        }
    }

    private void mostrarSiguientePregunta() {

        radioGroup.clearCheck();
        rdb1.setTextColor(dfRbColor);
        rdb2.setTextColor(dfRbColor);
        rdb3.setTextColor(dfRbColor);
        rdb4.setTextColor(dfRbColor);


        if(contPreguntas<numPreguntas){
            cronometro();
            preguntaActual= listaPreguntas.get(contPreguntas);
            txtPregunta.setText(preguntaActual.getPregunta());
            rdb1.setText(preguntaActual.getOpcion1());
            rdb2.setText(preguntaActual.getOpcion2());
            rdb3.setText(preguntaActual.getOpcion3());
            rdb4.setText(preguntaActual.getOpcion4());

            contPreguntas++;

            bttSiguiente.setText("Contestar");
            txtIdPregunta.setText("Pregunta: "+contPreguntas+"/"+numPreguntas);
            contestada=false;
        }else{

            //Lnazamos nueva activity y mostramos la puntuación final
            Intent PantallaFinal = new Intent(this, ResultadoActivity.class);
            PantallaFinal.putExtra("Puntuación",puntuacion);
            startActivity(PantallaFinal);
        }
    }

    //Establecemos una cuentra regresiva desde 20 seg hasta 0 de segundo en segundo
    private void cronometro() {
        contTiempo= new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTiempo.setText("00:" +millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                puntuacion-=2;
                txtPuntuacion.setText("Puntuacion: "+puntuacion);
                mostrarSiguientePregunta();

            }
        }.start();
    }

    private void anadirPreguntas(){
        listaPreguntas.add(new Pregunta("¿A?","a","b","c","d",1));
        listaPreguntas.add(new Pregunta("¿B?","a","b","c","d",2));
        listaPreguntas.add(new Pregunta("¿C?","a","b","c","d",3));
        listaPreguntas.add(new Pregunta("¿D?","a","b","c","d",4));
        listaPreguntas.add(new Pregunta("¿E?","a","e","c","d",2));
    }
}