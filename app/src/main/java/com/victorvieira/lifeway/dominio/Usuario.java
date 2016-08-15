package com.victorvieira.lifeway.dominio;

import java.util.Date;

public class Usuario {

    private String nome;
    private Date dataNascimento;
    private double peso;
    private double metaDePeso = 0;
    private double altura;
    private Consumo consumo;

    private int kcal_diaria;
    private int agua_diaria;

    public Usuario(String nome, Date dataNascimento, double peso, double altura, double metaDePeso, int kcal_diaria, int agua_diaria){
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.altura = altura;
        this.metaDePeso = metaDePeso;
        this.kcal_diaria = kcal_diaria;
        this.agua_diaria = agua_diaria;
        consumo = null;
    }

    public void addRefeicao(Alimento alimento) {
        if(consumo == null) {
            consumo = new Consumo();
        }

        Date horario = new Date();
        consumo.addRefeicao(alimento, horario);
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Date getDataNascimento() {
        return dataNascimento;
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
    public Consumo getConsumo() {
        return consumo;
    }
    public void setConsumo(Consumo consumo) {
        this.consumo = consumo;
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
