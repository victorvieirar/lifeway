package com.victorvieira.lifeway.dominio;

import com.victorvieira.lifeway.MySingleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Consumo {

    private ArrayList<Refeicao> refeicoes = null;
    private int count = 0;

    public Consumo() { super(); }

    public void addRefeicao(Alimento alimento, Date horario) {
        if(refeicoes == null) {
            refeicoes = new ArrayList<Refeicao>();
            refeicoes.add(new Refeicao(alimento.getTipo(), count++));
            refeicoes.get(refeicoes.size()-1).addAlimento(alimento, horario);
        } else {
            if(mesmoDia(horario)) {
                int refeicaoReferente = refeicaoReferente(alimento.getTipo());
                if(refeicaoReferente == -1) {
                    refeicoes.add(new Refeicao(alimento.getTipo(), count++));
                    refeicoes.get(refeicoes.size()-1).addAlimento(alimento, horario);
                } else {
                    refeicoes.get(refeicaoReferente).addAlimento(alimento, horario);
                }
            } else {
                refeicoes.add(new Refeicao(alimento.getTipo(), count++));
                refeicoes.get(refeicoes.size()-1).addAlimento(alimento, horario);
            }
        }
    }

    public int refeicaoReferente(char type) {

        for(int i = (refeicoes.size()-1); i >= 0; i--) {
            if(refeicoes.get(i).getTipo() == type) {
                return i;
            }
        }

        return -1;
    }

    public boolean mesmoDia(Date horario) {
        GregorianCalendar gcA = new GregorianCalendar();
        gcA.setTime(horario);
        int diaA = gcA.get(gcA.DAY_OF_MONTH);
        int mesA = gcA.get(gcA.MONTH);
        int anoA = gcA.get(gcA.YEAR);

        Date horarioU = refeicoes.get(refeicoes.size()-1).getLastHorario();
        GregorianCalendar gcU = new GregorianCalendar();
        gcU.setTime(horarioU);

        int dia = gcU.get(gcU.DAY_OF_MONTH);
        int mes = gcU.get(gcU.MONTH);
        int ano = gcU.get(gcU.YEAR);

        if(diaA == dia && mesA == mes && anoA == ano) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<Refeicao> getRefeicoes() { return refeicoes; }

    public ArrayList<Refeicao> getRefeicoesInverse(ArrayList<Refeicao> refeicoes) {
        ArrayList<Refeicao> refeicoesInverse = new ArrayList<Refeicao>();

        for (int i = (refeicoes.size()-1); i >= 0; i--) {
            refeicoesInverse.add(refeicoes.get(i));
        }

        return refeicoesInverse;
    }

    public Refeicao getLastRefeicao() { return refeicoes.get(refeicoes.size()-1); }

    public ArrayList<Refeicao> getRefeicoesOrdered(ArrayList<Refeicao> refeicoes) {
        ArrayList<Refeicao> refeicoesArray = refeicoes;
        boolean bContinue = true;
        int count = 0;
        int count2 = 0;
        char[] tipos = { 'c', 'a', 'j' };
        while(bContinue) {
            try {
                if(tipos[count] == refeicoes.get(count2).getTipo()) {
                    refeicoesArray.set(count, refeicoes.get(count2));
                    count++;
                    count2 = 0;
                } else {
                    count2++;
                }
            } catch(Exception e) {
                bContinue = false;
            }
        }

        return refeicoesArray;
    }

    public ArrayList<Refeicao> getRefeicoesByDay(int dia, int mes, int ano) {
        ArrayList<Refeicao> refeicoesArray = new ArrayList<Refeicao>();

        for(int i = 0; i < refeicoes.size(); i++) {
            Date horarioR = refeicoes.get(i).getLastHorario();
            int diaR = Integer.parseInt(horarioR.toString().substring(8,10));
            int mesR = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(horarioR.toString().substring(4,7));
            int anoR = Integer.parseInt(horarioR.toString().substring(24));

            if(dia == diaR && mes == mesR && ano == anoR) {
                refeicoesArray.add(refeicoes.get(i));
            }
        }

        return refeicoesArray;
    }

    public ArrayList<Date> getHorarios() {
        int dia = Integer.parseInt(refeicoes.get(0).getLastHorario().toString().substring(8,10));

        ArrayList<Date> horarios = new ArrayList<Date>();
        horarios.add(refeicoes.get(0).getLastHorario());

        for(int i = 0; i < refeicoes.size(); i++) {
            if(!(dia == Integer.parseInt(refeicoes.get(i).getLastHorario().toString().substring(8,10)))) {
                dia = Integer.parseInt(refeicoes.get(i).getLastHorario().toString().substring(8,10));
                horarios.add(refeicoes.get(i).getLastHorario());
            }
        }

        return horarios;
    }

    public ArrayList<Refeicao> getRefeicoesByDayInOrder(Date data) {
        int dia = Integer.parseInt(data.toString().substring(8,10));
        int mes = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(data.toString().substring(4,7));
        int ano = Integer.parseInt(data.toString().substring(24));

        ArrayList<Refeicao> refeicoesArray = getRefeicoesByDay(dia, mes, ano);
        refeicoesArray = getRefeicoesOrdered(refeicoesArray);
        refeicoesArray = getRefeicoesInverse(refeicoesArray);

        return refeicoesArray;

    }

    public Refeicao getRefeicaoById(int id) {
        for(Refeicao refeicao:refeicoes) {
            if(refeicao.getId() == id) {
                return refeicao;
            }
        }

        return null;
    }



}

