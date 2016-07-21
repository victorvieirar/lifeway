package com.victorvieira.lifeway.dominio;

import com.victorvieira.lifeway.MySingleton;

import java.util.ArrayList;
import java.util.Date;

public class Consumo {

    private ArrayList<Refeicao> refeicoes = null;
    private int count = 0;

    public Consumo() { super(); }

    public void addRefeicao(Alimento alimento, Date horario) {
        if(refeicoes == null) {
            refeicoes = new ArrayList<Refeicao>();
            refeicoes.add(new Refeicao(alimento.getTipo(), ++count));
            refeicoes.get(refeicoes.size()-1).addAlimento(alimento, horario);
        } else {
            if(mesmaRefeicao(horario, alimento.getTipo())) {
                refeicoes.get(refeicoes.size()-1).addAlimento(alimento, horario);
            } else {
                refeicoes.add(new Refeicao(alimento.getTipo(), ++count));
                refeicoes.get(refeicoes.size()-1).addAlimento(alimento, horario);
            }
        }
    }

    public boolean mesmaRefeicao(Date horario, char type) {
        int diaA = Integer.parseInt(horario.toString().substring(8,10));
        int mesA = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(horario.toString().substring(4,7));
        int anoA = Integer.parseInt(horario.toString().substring(24));

        Date horarioU = refeicoes.get(refeicoes.size()-1).getLastHorario();
        char typeU = refeicoes.get(refeicoes.size()-1).getLastAlimento().getTipo();

        int dia = Integer.parseInt(horarioU.toString().substring(8,10));
        int mes = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(horarioU.toString().substring(4,7));
        int ano = Integer.parseInt(horarioU.toString().substring(24));

        if(diaA == dia && mesA == mes && anoA == ano && typeU == type) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<Refeicao> getRefeicoes() { return refeicoes; }

    public ArrayList<Refeicao> getRefeicoesInverse() {
        ArrayList<Refeicao> refeicoesInverse = new ArrayList<Refeicao>();

        for (int i = (refeicoes.size()-1); i >= 0; i--) {
            refeicoesInverse.add(refeicoes.get(i));
        }

        return refeicoesInverse;
    }

    public Refeicao getLastRefeicao() { return refeicoes.get(refeicoes.size()-1); }
}
