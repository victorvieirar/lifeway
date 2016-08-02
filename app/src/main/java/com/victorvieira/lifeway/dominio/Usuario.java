package com.victorvieira.lifeway.dominio;

import java.util.Date;

public class Usuario {

    private String nome;
    private Date dataNascimento;
    private double peso;
    private double metaDePeso = 0;
    private double altura;
    private Consumo consumo;

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

}
