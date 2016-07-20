package com.victorvieira.lifeway;

import com.victorvieira.lifeway.dominio.Alimento;

import java.util.ArrayList;
import java.util.Date;

public class App {

    private ArrayList<Alimento> alimentos = new ArrayList<Alimento>();

    public App() {
        super();
        addAlimentos();
    }

    private void addAlimentos() {
        for(int i=0; i<5; i++) {
            String nome = "Banana " + i;
            alimentos.add(new Alimento(nome,'c'));
        }
        for(int i=0; i<5; i++) {
            String nome = "Feijão " + i;
            alimentos.add(new Alimento(nome,'a'));
        }
        for(int i=0; i<5; i++) {
            String nome = "Arroz " + i;
            alimentos.add(new Alimento(nome,'j'));
        }
    }

    public ArrayList<Alimento> getAlimentosByType(char tipo) {
        ArrayList<Alimento> alimentosList = new ArrayList<Alimento>();
        for (int i=0; i < alimentos.size(); i++) {
            if(alimentos.get(i).getTipo() == tipo) {
                alimentosList.add(alimentos.get(i));
            }
        }
        return alimentosList;
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
            if(diaDaSemana == semanaI[i]) {
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
            if(mes == mesI[i]) {
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
