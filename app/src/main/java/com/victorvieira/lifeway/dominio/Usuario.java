package com.victorvieira.lifeway.dominio;

import com.victorvieira.lifeway.MySingleton;

import java.util.Date;
import java.util.GregorianCalendar;

public class Usuario {

    private int id;

    private String nome;
    private Date dataNascimento;
    private double peso;
    private double metaDePeso = 0;
    private double altura;
    private double imc;

    private int kcal_diaria;
    private int agua_diaria;

    public Usuario() {

    }

    public Usuario(int id, String nome, Date dataNascimento, double peso, double altura, double metaDePeso, int kcal_diaria, int agua_diaria){
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.altura = altura;
        this.metaDePeso = metaDePeso;
        this.kcal_diaria = kcal_diaria;
        this.agua_diaria = agua_diaria;
    }

    public Date getDateByString(String sDate) {
        int dia = Integer.parseInt(sDate.substring(8,10));
        int mes = 1 - (MySingleton.getApp().getIndexOfMonth(sDate.substring(4,7)));
        int ano = Integer.parseInt(sDate.substring(25));

        GregorianCalendar gregorianCalendar = new GregorianCalendar(ano, mes, dia);
        return gregorianCalendar.getTime();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getImc() {
        imc = peso/Math.pow(altura, 2);
        return imc;
    }
    public void setImc(double imc) {
        this.imc = imc;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDataNascimentoBD() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dataNascimento);
        String sTime = ""+gc.getTimeInMillis();
        return sTime;
    }
    public Date getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimentoBD(String sTime) {
        long time = Long.parseLong(sTime);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time);
        this.dataNascimento = gc.getTime();
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public double getMetaDePeso() {
        return metaDePeso;
    }
    public void setMetaDePeso(double metaDePeso) {
        this.metaDePeso = metaDePeso;
    }
    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }
    public int getKcal_diaria() {
        return kcal_diaria;
    }
    public void setKcal_diaria(int kcal_diaria) {
        this.kcal_diaria = kcal_diaria;
    }
    public int getAgua_diaria() {
        return agua_diaria;
    }
    public void setAgua_diaria(int agua_diaria) {
        this.agua_diaria = agua_diaria;
    }
}
