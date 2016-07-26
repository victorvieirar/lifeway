package com.victorvieira.lifeway;

import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Refeicao;
import com.victorvieira.lifeway.dominio.RefeicaoDisponivel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class App {

    private final GregorianCalendar DATA_CRIACAO = new GregorianCalendar();

    private ArrayList<RefeicaoDisponivel> refeicoesDisponiveis = new ArrayList<RefeicaoDisponivel>();

    public App() {
        super();
        DATA_CRIACAO.setTime(new Date());
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

    public String transformDiaDaSemana(String diaDaSemana) {

        String[] semanaI = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        String[] semanaP = { "domingo", "segunda-feira", "terça-feira", "quarta-feira", "quinta-feira", "sexta-feira", "sábado"};

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

    public String getStringOfHour(GregorianCalendar reference, GregorianCalendar selected) {

        String sData = "";

        int diaDoMesS = selected.get(selected.DAY_OF_MONTH);
        int mesS = selected.get(selected.MONTH);
        int anoS = selected.get(selected.YEAR);

        int diaDoMesR = reference.get(reference.DAY_OF_MONTH);
        int mesR = reference.get(reference.MONTH);
        int anoR = reference.get(reference.YEAR);

        if(diaDoMesS == diaDoMesR && mesS == mesR && anoS == anoR) {
            sData = "hoje";
        } else {
            reference.add(reference.DAY_OF_MONTH, -1);

            diaDoMesR = reference.get(reference.DAY_OF_MONTH);
            mesR = reference.get(reference.MONTH);
            anoR = reference.get(reference.YEAR);

            if(diaDoMesS == diaDoMesR && mesS == mesR && anoS == anoR) {
                sData = "ontem";
            } else {
                reference.add(reference.DAY_OF_MONTH, 1);

                diaDoMesR = reference.get(reference.DAY_OF_MONTH);
                mesR = reference.get(reference.MONTH);
                anoR = reference.get(reference.YEAR);

                sData = getStringOfDayByIndex(selected.get(selected.DAY_OF_WEEK)) + ", "
                        + selected.get(selected.DAY_OF_MONTH) + " de "
                        + getStringOfMonthByIndex(selected.get(selected.MONTH)) + " de "
                        + selected.get(selected.YEAR);
            }
        }

        return sData;

    }

    public String getStringOfDayByIndex(int index) {
        String[] semana = { "domingo", "segunda-feira", "terça-feira", "quarta-feira", "quinta-feira", "sexta-feira", "sábado"};
        return semana[index];
    }
    public String getStringOfMonthByIndex(int index) {
        String[] mes = { "janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro" };
        return mes[index];
    }
}
