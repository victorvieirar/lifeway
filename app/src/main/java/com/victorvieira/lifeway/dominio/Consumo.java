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
    private int id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Consumo() { super(); }

    public void addRefeicao(Refeicao refeicao) {
        if(refeicoes == null) {
            refeicoes = new ArrayList<Refeicao>();
        }
        this.refeicoes.add(refeicao);
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
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(refeicoes.get(i).getLastHorario());

            int diaR = gc.get(gc.DAY_OF_MONTH);
            int mesR = gc.get(gc.MONTH);
            int anoR = gc.get(gc.YEAR);

            if(dia == diaR && mes == mesR && ano == anoR) {
                refeicoesArray.add(refeicoes.get(i));
            }
        }

        return refeicoesArray;
    }

    public ArrayList<Date> getHorarios() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(refeicoes.get(0).getLastHorario());

        int dia = gc.get(gc.DAY_OF_MONTH);

        ArrayList<Date> horarios = new ArrayList<Date>();
        horarios.add(refeicoes.get(0).getLastHorario());

        for(int i = 0; i < refeicoes.size(); i++) {
            gc.setTime(refeicoes.get(i).getLastHorario());
            if(!(dia == gc.get(gc.DAY_OF_MONTH))) {
                dia = gc.get(gc.DAY_OF_MONTH);
                horarios.add(refeicoes.get(i).getLastHorario());
            }
        }

        return horarios;
    }

    public ArrayList<Refeicao> getRefeicoesByDayInOrder(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);

        int dia = gc.get(gc.DAY_OF_MONTH);
        int mes = gc.get(gc.MONTH);
        int ano = gc.get(gc.YEAR);

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

