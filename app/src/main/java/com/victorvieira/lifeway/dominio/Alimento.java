package com.victorvieira.lifeway.dominio;

public class Alimento {

    private int id;
    private int indicacao; // 1, 2 ou 3
    private double valor_calorico;
    private String nome;
    private char tipo;

    public Alimento() { super(); }

    public Alimento(int id, String nome, char tipo, int indicacao, double valor_calorico) {
        super();
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.indicacao = indicacao;
        this.valor_calorico = valor_calorico;
    }

    public int getIndicacao() {
        return indicacao;
    }
    public void setIndicacao(int indicacao) {
        this.indicacao = indicacao;
    }
    public double getValor_calorico() {
        return valor_calorico;
    }
    public void setValor_calorico(double valor_calorico) {
        this.valor_calorico = valor_calorico;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public char getTipo() { return tipo; }
    public void setTipo(char tipo) { this.tipo = tipo; }
}
