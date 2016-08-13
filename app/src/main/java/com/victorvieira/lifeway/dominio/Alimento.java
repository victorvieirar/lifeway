package com.victorvieira.lifeway.dominio;

public class Alimento {

    private String nome;
    private char tipo;

    public Alimento() { super(); }

    public Alimento(String nome, char tipo) {
        super();
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public char getTipo() { return tipo; }
    public void setTipo(char tipo) { this.tipo = tipo; }


}
