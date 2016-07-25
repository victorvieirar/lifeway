package com.victorvieira.lifeway;

import com.victorvieira.lifeway.dominio.Alimento;
import com.victorvieira.lifeway.dominio.Refeicao;
import com.victorvieira.lifeway.dominio.RefeicaoDisponivel;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public boolean hasBeforeDate(Date dataA, ArrayList<Date> datasExistentes) {

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(dataA);
        gregorianCalendar.add(gregorianCalendar.DAY_OF_MONTH, -1);

        int diaGC = Integer.parseInt(gregorianCalendar.getTime().toString().substring(8,10));
        int mesGC = getIndexOfMonth(gregorianCalendar.getTime().toString().substring(4,7));
        int anoGC = Integer.parseInt(gregorianCalendar.getTime().toString().substring(24));

        for(int i = 0; i < datasExistentes.size(); i++) {
            int dia = Integer.parseInt(datasExistentes.get(i).toString().substring(8,10));
            int mes = getIndexOfMonth(datasExistentes.get(i).toString().substring(4,7));
            int ano = Integer.parseInt(datasExistentes.get(i).toString().substring(24));

            if(diaGC == dia && mesGC == mes && anoGC == ano) {
                return true;
            }

        }
        return false;
    }

    public boolean hasAfterDate(Date dataA, ArrayList<Date> datasExistentes) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(dataA);
        gregorianCalendar.add(gregorianCalendar.DAY_OF_MONTH, 1);

        int diaGC = Integer.parseInt(gregorianCalendar.getTime().toString().substring(8,10));
        int mesGC = getIndexOfMonth(gregorianCalendar.getTime().toString().substring(4,7));
        int anoGC = Integer.parseInt(gregorianCalendar.getTime().toString().substring(24));

        for(int i = 0; i < datasExistentes.size(); i++) {
            int dia = Integer.parseInt(datasExistentes.get(i).toString().substring(8,10));
            int mes = getIndexOfMonth(datasExistentes.get(i).toString().substring(4,7));
            int ano = Integer.parseInt(datasExistentes.get(i).toString().substring(24));

            if(diaGC == dia && mesGC == mes && anoGC == ano) {
                return true;
            }

        }
        return false;
    }

    public boolean isSingleDay(Date data1, Date data2){
        int dia1 = Integer.parseInt(data1.toString().substring(8,10));
        int mes1 = getIndexOfMonth(data1.toString().substring(4,7));
        int ano1 = Integer.parseInt(data1.toString().substring(24));

        int dia2 = Integer.parseInt(data2.toString().substring(8,10));
        int mes2 = getIndexOfMonth(data2.toString().substring(4,7));
        int ano2 = Integer.parseInt(data2.toString().substring(24));

        if(dia1 == dia2 && mes1 == mes2 && ano1 == ano2) {
            return true;
        } else {
            return false;
        }

    }

    public String getStringOfHour(Date data1, Date data2) {

        String sData = "";

        int diaDoMes = Integer.parseInt(data2.toString().substring(8, 10));
        int mes = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(data2.toString().substring(4,7));
        int ano = Integer.parseInt(data2.toString().substring(24));

        int diaDoMesA = Integer.parseInt(data1.toString().substring(8, 10));
        int mesA = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(data1.toString().substring(4,7));
        int anoA = Integer.parseInt(data1.toString().substring(24));

        if(diaDoMes == diaDoMesA) {
            if(mes == mesA && ano == anoA) {
                sData = "hoje";
            } else {
                String diaDaSemana = data2.toString().substring(0,3);
                String sMes = data2.toString().substring(4,7);
                sMes = MySingleton.getBancoDeDados().getApp().transformMes(sMes);
                sData = MySingleton.getBancoDeDados().getApp().transformDiaDaSemana(diaDaSemana) + ", " + diaDoMes + " de " + sMes + " de " + ano;
            }

        } else {
            if(diaDoMes == (diaDoMesA-1)) {
                if(mes == mesA && ano == anoA) {
                    sData = "ontem";
                } else {
                    String diaDaSemana = data2.toString().substring(0,3);
                    String sMes = data2.toString().substring(4,7);
                    sMes = MySingleton.getBancoDeDados().getApp().transformMes(sMes);
                    sData = MySingleton.getBancoDeDados().getApp().transformDiaDaSemana(diaDaSemana) + ", " + diaDoMes + " de " + sMes + " de " + ano;
                }

            } else {
                String diaDaSemana = data2.toString().substring(0,3);
                String sMes = data2.toString().substring(4,7);
                sMes = MySingleton.getBancoDeDados().getApp().transformMes(sMes);
                sData = MySingleton.getBancoDeDados().getApp().transformDiaDaSemana(diaDaSemana) + ", " + diaDoMes + " de " + sMes + " de " + ano;
            }
        }

        return sData;

    }
}
