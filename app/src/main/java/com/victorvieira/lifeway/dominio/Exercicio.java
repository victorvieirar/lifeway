package com.victorvieira.lifeway.dominio;

import java.util.Date;
import java.util.GregorianCalendar;

public class Exercicio {

    private int id;
    private double tempo;
    private Date data;

    public Exercicio() {
        tempo = 0;
    }

    public Exercicio(Date data) {
        this.data = data;
        tempo = 0;
    }

    public Exercicio(double tempo, Date data) {
        this.tempo = tempo;
        this.data = data;
    }

    public void setDataBD(String time) {
        long timeInMillis = Long.parseLong(time);

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(timeInMillis);

        setData(gc.getTime());
    }

    public String getDataBD() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);

        return ""+gc.getTimeInMillis();
    }

    public void addTempo(double tempo) {
        this.tempo += tempo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getTempo() {
        return tempo;
    }
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
}
