package com.example.quizapp;

//En la aplicación las preguntas con imagenes, tendrán dos opcciones

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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
    private TextView txtPregunta,txtPuntuacion,txtIdPregunta,txtTiempo,txtResultado;
    private RadioGroup radioGroup;
    private RadioButton rdb1,rdb2,rdb3,rdb4;
    private Button bttSiguiente,bttReiniciar;
    private Pregunta preguntaActual;

    boolean contestada;

    int numPreguntas;
    int contPreguntas=0;
    int puntuacion=0;

    CountDownTimer contTiempo;

    //Variable para cambiar el color del texto, verde si es correcto y rojo si es incorrecto
    ColorStateList dfRbColor;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtPregunta=findViewById(R.id.txtPregunta);
        txtPuntuacion=findViewById(R.id.txtPuntuacionF);
        txtIdPregunta=findViewById(R.id.txtIdPregunta);
        txtTiempo=findViewById(R.id.txtTiempo);
        txtResultado= findViewById(R.id.txtResultado);

        radioGroup= findViewById(R.id.radioGroup);
        rdb1=findViewById(R.id.rdbOpcion1);
        rdb2=findViewById(R.id.rdbOpcion2);
        rdb3=findViewById(R.id.rdbOpcion3);
        rdb4=findViewById(R.id.rdbOpcion4);

        bttSiguiente=findViewById(R.id.bttSiguiente);
        bttReiniciar=findViewById(R.id.bttReiniciar);

        //Declaramos el arrayList para las preguntas
        listaPreguntas = new ArrayList<>();

        dfRbColor = rdb1.getTextColors();

        //Añadimos las preguntas a "ListaPreguntas"
        anadirPreguntas();
        //Actualizamos el valor de numPreguntas con el tamaño de la lista
        numPreguntas= listaPreguntas.size();

        //Mostramos la primera pregunta
        mostrarSiguientePregunta();
    }

    //Pasamos a la siguiente pregunta
    public void siguiente(View view){
        txtResultado.setVisibility(View.INVISIBLE);
        //Si la pregunta no ha sido contestada, comprobamos si se ha seleccionado una opción
        if (!contestada){

            if (rdb1.isChecked() || rdb2.isChecked() || rdb3.isChecked() || rdb4.isChecked()){
                //Si se ha seleccionado alguna opción comprobamos la respuesta y cancelamos la cuenta atrás
                comprobarRespuesta();
                contTiempo.cancel();
            }else{

                //Si queremos pasar de pregunta pero no hemos marcado ninguna opción lanzaremos un Toast
                Toast.makeText(QuizActivity.this,"Seleccione una de las respuestas",Toast.LENGTH_SHORT).show();
            }
        //Si ha sido contestada mostramos la siguiente pregunta
        }else{

            mostrarSiguientePregunta();
        }
    }

    //Boton para reiniciar la activity
    public void reiniciar(View view){
        //Creamos una intent nueva, finalizamos la actual y lanzamos la creada
        Intent Quiz = getIntent();
        finish();
        startActivity(Quiz);

    }

    @SuppressLint("ResourceAsColor")
    private void comprobarRespuesta() {

        contestada=true;
        //Obtengo el radioButton seleccionado
        RadioButton rbdSeleccionado = findViewById(radioGroup.getCheckedRadioButtonId());
        //Veo su index, su posición en el radioGroup + 1 (empieza desde 0)
        int opcionSeleccionada= radioGroup.indexOfChild(rbdSeleccionado)+1;

        if(opcionSeleccionada == preguntaActual.getOpcionCorrecta()){
            puntuacion+=3;

            //Modificamos puntuación y mostramos textView de correcto
            txtPuntuacion.setText("Puntuacion: "+puntuacion);
            //Actualizamos el txt resultado
            txtResultado.setVisibility(View.VISIBLE);
            txtResultado.setText("Respuesta correcta!!");
            txtResultado.setTextColor(Color.WHITE);
            txtResultado.setBackgroundColor(Color.GREEN);

        }else{
            puntuacion-=2;

            //Hacemos aparecer el botón de reiniciar
            bttReiniciar.setVisibility(View.VISIBLE);
            //Modificamos puntuación y mostramos textView de incorrecto
            txtPuntuacion.setText("Puntuacion: "+puntuacion);
            //Actualizamos el txt resultado
            txtResultado.setVisibility(View.VISIBLE);
            txtResultado.setText("Respuesta incorrecta");
            txtResultado.setTextColor(Color.WHITE);
            txtResultado.setBackgroundColor(Color.RED);
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

        //Quitamos todos los checks de los radioButtons y ponemos los colores por defecto "negro"
        radioGroup.clearCheck();
        rdb1.setTextColor(dfRbColor);
        rdb2.setTextColor(dfRbColor);
        rdb3.setTextColor(dfRbColor);
        rdb4.setTextColor(dfRbColor);

        //Seteamos los valores a 0 y quitamos imagenes siempre antes de mostrar una pregunta por si acaso
        resetearValores();

        //Comprobamos si quedan más preguntas
        if(contPreguntas<numPreguntas){
            //Activamos el cronómetro
            cronometro();
            //Actualizamos la pregunta actual
            preguntaActual= listaPreguntas.get(contPreguntas);


            //Comprobamos si es una pregunta sin imagenes, con respuesta tipo imagenes o con pregunta tipo imagen
            //Tipo respuestas imagen
            if(preguntaActual.getTipo()==2){

                txtPregunta.setText(preguntaActual.getPregunta());

                rdb1.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(Integer.parseInt(preguntaActual.getOpcion1())), null, null, null);
                rdb2.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(Integer.parseInt(preguntaActual.getOpcion2())), null, null, null);
                rdb3.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(Integer.parseInt(preguntaActual.getOpcion3())), null, null, null);
                rdb4.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(Integer.parseInt(preguntaActual.getOpcion4())), null, null, null);

                //Tipo respuestas y pregunta sin imagen
            }else if (preguntaActual.getTipo()==0) {

                txtPregunta.setText(preguntaActual.getPregunta());

                //Actualizamos los textos de los radioButtons con las opcciones de las preguntas
                rdb1.setText(preguntaActual.getOpcion1());
                rdb2.setText(preguntaActual.getOpcion2());
                rdb3.setText(preguntaActual.getOpcion3());
                rdb4.setText(preguntaActual.getOpcion4());

                //Tipo pregunta con imagen y respuesta sin imagenes
            }else{

                //Ponemos iamgen de pregunta
                txtPregunta.setText(preguntaActual.getPregunta());
                txtPregunta.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getDrawable(Integer.parseInt(preguntaActual.getImgPregunta())), null);

                //Actualizamos los textos de los radioButtons con las opcciones de las preguntas
                rdb1.setText(preguntaActual.getOpcion1());
                rdb2.setText(preguntaActual.getOpcion2());
                rdb3.setText(preguntaActual.getOpcion3());
                rdb4.setText(preguntaActual.getOpcion4());
            }

            //Aumentamos el contador de la pregunta en la que nos encontramos
            contPreguntas++;
            txtIdPregunta.setText("Pregunta: "+contPreguntas+"/"+numPreguntas);

            bttSiguiente.setText("Contestar");
            contestada=false;

        //Si el contador de preguntas contestadas es igual que el numero de preguntas totales, el juego habrá finalizado
        }else{

            //Lnazamos nueva activity y mostramos la puntuación final, además le mandamos como "extra" la puntuación actual
            Intent PantallaFinal = new Intent(this, ResultadoActivity.class);
            PantallaFinal.putExtra("Puntuación",puntuacion);
            startActivity(PantallaFinal);
        }
    }

    private void resetearValores() {

        //Reseteamos texto
        txtPregunta.setText("");
        rdb1.setText("");
        rdb2.setText("");
        rdb3.setText("");
        rdb4.setText("");
        //Reseteamos imagenes
        txtPregunta.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        rdb1.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        rdb2.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        rdb3.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        rdb4.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }

    //Establecemos una cuentra regresiva desde 30 seg hasta 0, de segundo en segundo
    private void cronometro() {
        // Actualizamos "contTiempo" con un objeto tipo CountDownTimer
        contTiempo= new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTiempo.setText("00:" +millisUntilFinished/1000);

            }

            //Al finalizar el cronómetro, restaremos 2 a la puntuación y mostraremos la siguiente pregunta
            @Override
            public void onFinish() {
                puntuacion-=2;
                txtPuntuacion.setText("Puntuacion: "+puntuacion);
                mostrarSiguientePregunta();

            }
        }.start();
    }

    private void anadirPreguntas(){

        //Cargamos las preguntas en el array de listaPreguntas
        listaPreguntas.add(new Pregunta(null,"¿Cuál es el lugar más frío de la tierra?","La Antártida","Rusia","Alemania","Cánada",0,1));
        listaPreguntas.add(new Pregunta(null,"¿Cómo se llama la capital de Mongolia?","Luanda","Ulan Bator","Berlín","Saint John",0,2));
        listaPreguntas.add(new Pregunta(null,"¿En qué continente está Ecuador?","Europa","África","América","Oceanía",0,3));
        listaPreguntas.add(new Pregunta(null,"¿Cúal marca se fundó en 1947?",Integer.toString(R.drawable.peugeot),Integer.toString(R.drawable.renault),Integer.toString(R.drawable.volvo),Integer.toString(R.drawable.ferrari),2,4));
        listaPreguntas.add(new Pregunta(null,"¿Qué cantidad de huesos en el cuerpo humano?","300","100","209","206",0,4));
        listaPreguntas.add(new Pregunta(Integer.toString(R.drawable.mongolia),"¿De que país es la bandera?","Mongolia","España","Venezuela","Cuba",1,1));

    }
}