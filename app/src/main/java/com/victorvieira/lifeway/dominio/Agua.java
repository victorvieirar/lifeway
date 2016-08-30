package com.victorvieira.lifeway.dominio;

import java.util.Date;
import java.util.GregorianCalendar;

public class Agua {

    private int id;
    private double quantidade;
    private Date data;

    public Agua() {

    }

    public Agua(Date data) {
        this.data = data;
    }

    public Agua(double quantidade, Date data) {
        this.data = data;
        this.quantidade = quantidade;
    }

    public void addQuantidade(double quantidade) {
        this.quantidade += quantidade;
    }

    public void removerQuantidade(double quantidade) {
        if((this.quantidade-quantidade)>= 0) {
            this.quantidade -= quantidade;
        }
    }

    public String getDataBD() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(getData());

        String data = gc.getTimeInMillis()+"";
        return data;
    }

    public void setDataBD(String data) {
        long time = Long.parseLong(data);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time);

        setData(gc.getTime());
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
}
