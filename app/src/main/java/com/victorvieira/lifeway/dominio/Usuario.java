package com.victorvieira.lifeway.dominio;

import android.database.Cursor;

import java.util.Date;

public class Usuario {

    private String nome;
    private Date dataNascimento;
    private double peso;
    private double metaDePeso = 0;
    private double altura;
    private double imc;
    private Consumo consumo;
    public String setDataNascimento;

    /*** Possível Solução de Rayanne */
    public String set_id;
    public String _id;


    public Usuario(String nome, Date dataNascimento, double peso, double altura, double metaDePeso){
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.altura = altura;
        this.metaDePeso = metaDePeso;
        consumo = null;
    }



    public void addRefeicao(Alimento alimento) {
        if(consumo == null) {
            consumo = new Consumo();
        }

        Date horario = new Date();
        consumo.addRefeicao(alimento, horario);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public void setConsumo(Consumo consumo) {
        this.consumo = consumo;
    }

    public void addRefeicao(Alimento alimento, Date horario) { consumo.addRefeicao(alimento, horario); }

    public Consumo getConsumo() {
        return consumo;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public double getMetaDePeso() { return metaDePeso; }

    public void setMetaDePeso(double metaDePeso) {
       this.metaDePeso = metaDePeso;
    }

    public double getAltura() {
        return altura;
    }


    /*** Possíveis Soluções de Rayanne */
    public void set_id() {
        this._id = _id;
    }
    public String get_id() {
        return _id;
    }



}
