package com.victorvieira.lifeway.dominio;

import com.victorvieira.lifeway.MySingleton;

import java.util.ArrayList;
import java.util.Date;

public class Refeicao {

    private int id;
    private char tipo;
    private ArrayList<Alimento> alimentosConsumidos = new ArrayList<Alimento>();
    private ArrayList<Date> horariosDeConsumo = new ArrayList<Date>();

    public Refeicao() { super(); }

    public Refeicao(char tipo, int id) {
        this.tipo = tipo;
        this.id = id;
    }

    public void addAlimento(Alimento alimento, Date horario) {
        this.alimentosConsumidos.add(alimento);
        this.horariosDeConsumo.add(horario);
    }

    public ArrayList<Alimento> getAlimentos() { return alimentosConsumidos; }
    public ArrayList<Date> getHorarios() { return horariosDeConsumo; }

    public char getTipo() { return tipo; }

    public Date getHorarioByIndex(int index) { return horariosDeConsumo.get(index); }

    public Date getLastHorario() { return horariosDeConsumo.get(horariosDeConsumo.size()-1); }

    public String getStringOfHourByAlimento(Alimento alimento) {
        String sData = "";
        for(int i = (alimentosConsumidos.size() - 1); i >= 0; i--) {
            if(alimentosConsumidos.get(i) == alimento) {
                Date data = getHorarioByIndex(i);
                Date dataAtual = new Date();

                int diaDoMes = Integer.parseInt(data.toString().substring(8, 10));
                int mes = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(data.toString().substring(4,7));
                int ano = Integer.parseInt(data.toString().substring(24));

                int diaDoMesA = Integer.parseInt(dataAtual.toString().substring(8, 10));
                int mesA = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(dataAtual.toString().substring(4,7));
                int anoA = Integer.parseInt(dataAtual.toString().substring(24));

                if(diaDoMes == diaDoMesA) {
                    if(mes == mesA && ano == anoA) {
                            sData = "hoje";
                    } else {
                        String diaDaSemana = data.toString().substring(0,3);
                        diaDaSemana = MySingleton.getBancoDeDados().getApp().transformDiaDaSemana(diaDaSemana);
                        String sMes = data.toString().substring(4,7);
                        sMes = MySingleton.getBancoDeDados().getApp().transformMes(sMes);
                        sData = diaDaSemana + ", " + diaDoMes + " de " + sMes + " de " + ano;
                    }
                } else {
                    if(diaDoMes == (diaDoMesA-1)) {
                        if(mes == mesA && ano == anoA) {
                                sData = "ontem";
                        } else {
                            String diaDaSemana = data.toString().substring(0,3);
                            diaDaSemana = MySingleton.getBancoDeDados().getApp().transformDiaDaSemana(diaDaSemana);
                            String sMes = data.toString().substring(4,7);
                            sMes = MySingleton.getBancoDeDados().getApp().transformMes(sMes);
                            sData = diaDaSemana + ", " + diaDoMes + " de " + sMes + " de " + ano;
                        }
                    } else {
                        String diaDaSemana = data.toString().substring(0,3);
                        diaDaSemana = MySingleton.getBancoDeDados().getApp().transformDiaDaSemana(diaDaSemana);
                        String sMes = data.toString().substring(4,7);
                        sMes = MySingleton.getBancoDeDados().getApp().transformMes(sMes);
                        sData = diaDaSemana + ", " + diaDoMes + " de " + sMes + " de " + ano;
                    }
                }

                break;
            }
        }
        sData = "de " + sData;
        return sData;
    }

    public String getStringOfHourByHorario(Date horario) {
        String sData = "";

        Date data = horariosDeConsumo.get(horariosDeConsumo.size()-1);
        Date dataAtual = new Date();

        int diaDoMes = Integer.parseInt(data.toString().substring(8, 10));
        int mes = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(data.toString().substring(4,7));
        int ano = Integer.parseInt(data.toString().substring(24));

        int diaDoMesA = Integer.parseInt(dataAtual.toString().substring(8, 10));
        int mesA = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(dataAtual.toString().substring(4,7));
        int anoA = Integer.parseInt(dataAtual.toString().substring(24));

        if(diaDoMes == diaDoMesA) {
            if(mes == mesA && ano == anoA) {
                sData = "hoje";
            } else {
                String diaDaSemana = data.toString().substring(0,3);
                String sMes = data.toString().substring(4,7);
                sMes = MySingleton.getBancoDeDados().getApp().transformMes(sMes);
                sData = MySingleton.getBancoDeDados().getApp().transformDiaDaSemana(diaDaSemana) + ", " + diaDoMes + " de " + sMes + " de " + ano;
            }

        } else {
            if(diaDoMes == (diaDoMesA-1)) {
                if(mes == mesA && ano == anoA) {
                    sData = "ontem";
                } else {
                    String diaDaSemana = data.toString().substring(0,3);
                    String sMes = data.toString().substring(4,7);
                    sMes = MySingleton.getBancoDeDados().getApp().transformMes(sMes);
                    sData = MySingleton.getBancoDeDados().getApp().transformDiaDaSemana(diaDaSemana) + ", " + diaDoMes + " de " + sMes + " de " + ano;
                }

            } else {
                String diaDaSemana = data.toString().substring(0,3);
                String sMes = data.toString().substring(4,7);
                sMes = MySingleton.getBancoDeDados().getApp().transformMes(sMes);
                sData = MySingleton.getBancoDeDados().getApp().transformDiaDaSemana(diaDaSemana) + ", " + diaDoMes + " de " + sMes + " de " + ano;
            }
        }

        return sData;
    }

    public Alimento getLastAlimento() { return alimentosConsumidos.get(alimentosConsumidos.size()-1); }

}
