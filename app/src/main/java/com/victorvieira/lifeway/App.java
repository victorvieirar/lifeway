package com.victorvieira.lifeway;

import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Refeicao;
import com.victorvieira.lifeway.dominio.RefeicaoDisponivel;

import java.util.ArrayList;
import java.util.Date;

public class App {

    private ArrayList<RefeicaoDisponivel> refeicoesDisponiveis = new ArrayList<RefeicaoDisponivel>();

    public App() {
        super();
        createRefeicoes();
    }

    private void createRefeicoes() {
        String[] nome = { "Banana", "Feijão", "Arroz" };
        String[] nomeRefeicao = {"Café da Manhã", "Almoço", "Jantar"};
        char[] type = { 'c', 'a', 'j' };
        for(int i=0; i < 3; i++) {
            RefeicaoDisponivel refeicaoDisponivel = new RefeicaoDisponivel(type[i],nomeRefeicao[i]);
            for(int j = 0; j < 5; j++) {
                String sNome = nome[i] + " " + Integer.toString(j);
                refeicaoDisponivel.addAlimento(new Alimento(sNome, type[i]));
            }
            refeicoesDisponiveis.add(refeicaoDisponivel);
        }
    }

    public ArrayList<RefeicaoDisponivel> getRefeicoesDisponiveis() { return refeicoesDisponiveis; }

    public ArrayList<RefeicaoDisponivel> getRefeicoesDisponiveisBySearch(String nomeAlimento) {

        ArrayList<RefeicaoDisponivel> resultSearch = new ArrayList<RefeicaoDisponivel>();

        for(int i = 0; i < refeicoesDisponiveis.size(); i++) {

            RefeicaoDisponivel refeicaoAtual = new RefeicaoDisponivel(refeicoesDisponiveis.get(i).getType(), refeicoesDisponiveis.get(i).getNomeRefeicao());
            ArrayList<Alimento> alimentosAtuais = refeicoesDisponiveis.get(i).getAlimentos();

            for(int j = 0; j < alimentosAtuais.size(); j++) {
                if(alimentosAtuais.get(j).getNome().toLowerCase().contains(nomeAlimento.toLowerCase())) {
                    refeicaoAtual.addAlimento(alimentosAtuais.get(j));
                }
            }

            if(!(refeicaoAtual.getAlimentos().size() <= 0)) {
                resultSearch.add(refeicaoAtual);
            }

        }

        return resultSearch;
    }

    public char getAtualRefeicao() {

        Date tempoAtual = new Date();

        int horaAtual = Integer.parseInt(tempoAtual.toString().substring(11,13));
        if((horaAtual >= 6) && (horaAtual <= 11)) {
            return 'c';
        } else {
            if((horaAtual > 11) && (horaAtual <= 17)) {
                return 'a';
            } else {
                return 'j';
            }
        }

    }

    public String transformDiaDaSemana(String diaDaSemana) {

        String[] semanaI = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        String[] semanaP = { "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"};

        for (int i = 0; i < semanaI.length; i++) {
            if(diaDaSemana.contains(semanaI[i])) {
                diaDaSemana = semanaP[i];
                break;
            }
        }

        return diaDaSemana;
    }

    public String transformMes(String mes) {
        String[] mesI = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String[] mesP = { "janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro" };

        for(int i = 0; i < mesI.length; i++) {
            if(mes.contains(mesI[i])) {
                mes = mesP[i];
                break;
            }
        }

        return mes;
    }

    public int getIndexOfMonth(String mes) {
        String[] mesI = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        for(int i = 0; i < mesI.length; i++) {
            if(mesI[i].contains(mes)) {
                return ++i;
            }
        }

        return 0;

    }

}
