package com.example.quizapp;

public class Pregunta {
    //Este primer atributo será distinto de null si y sólo sí la pregunta tiene cómo pregunta una Imagen
    //De está manera podemos asignar la pregunta texto con la pregunta imagen
    private String imgPregunta;
    private String pregunta;
    private String opcion1,opcion2,opcion3,opcion4;
    private int tipo;
    private int opcionCorrecta;

    public Pregunta(String imgPregunta, String pregunta, String opcion1, String opcion2, String opcion3, String opcion4, int tipo, int opcionCorrecta) {
        this.imgPregunta = imgPregunta;
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.tipo = tipo;
        this.opcionCorrecta = opcionCorrecta;
    }

    public String getImgPregunta() {
        return imgPregunta;
    }

    public void setImgPregunta(String imgPregunta) {
        this.imgPregunta = imgPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getOpcionCorrecta() {
        return opcionCorrecta;
    }

    public void setOpcionCorrecta(int opcionCorrecta) {
        this.opcionCorrecta = opcionCorrecta;
    }
}
