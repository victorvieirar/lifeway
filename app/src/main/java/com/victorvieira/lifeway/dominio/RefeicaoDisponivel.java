package com.victorvieira.lifeway.dominio;

import java.util.ArrayList;

public class RefeicaoDisponivel {

    private ArrayList<Alimento> alimentos;
    private String nomeRefeicao;
    private char type;
    public RefeicaoDisponivel (char type, String nomeRefeicao) {
        alimentos = new ArrayList<Alimento>();
        this.nomeRefeicao = nomeRefeicao;
        this.type = type;
    }

    public void addAlimento(Alimento alimento) {
        alimentos.add(alimento);
    }

    public String getNomeRefeicao() { return nomeRefeicao; }
    public char getType() { return type; }
    public ArrayList<Alimento> getAlimentos() { return alimentos; }
}
