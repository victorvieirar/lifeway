package com.victorvieira.lifeway.dominio;

import com.victorvieira.lifeway.MySingleton;

import java.util.ArrayList;
import java.util.Date;

public class Consumo {

    private ArrayList<Date> horariosConsumo = new ArrayList<Date>();
    private ArrayList<Alimento> alimentosConsumidos = new ArrayList<Alimento>();

    public Consumo() { super(); }

    public void addRefeicao(Alimento alimento, Date horario) {
        horariosConsumo.add(horario);
        alimentosConsumidos.add(alimento);
    }

    public ArrayList<Alimento> getAlimentos() {
        ArrayList<Alimento> alimentosArray = new ArrayList<Alimento>();
        if(alimentosConsumidos.size() > 0) {
            for(int i = (alimentosConsumidos.size() - 1); i >= 0; i--) {
                alimentosArray.add(alimentosConsumidos.get(i));
            }
            return alimentosConsumidos;
        }
        return null;
    }

    public ArrayList<Alimento> getAlimentosAfterLast() {
        ArrayList<Alimento> alimentosArray = new ArrayList<Alimento>();
        ArrayList<Alimento> alimentosLastArray = getLastAlimentos();
        int k = alimentosLastArray.size()-1;
        int position = 0;
        for(int i = alimentosConsumidos.size()-1; i >= 0; i++) {
            if(alimentosConsumidos.get(i) == alimentosLastArray.get(k)) {
                k--;
                if(k<0) {
                    position = i-1;
                    break;
                }
            }
        }

        try {
            for(int i = position; i >= 0; i--) {
                alimentosArray.add(alimentosConsumidos.get(i));
            }
        } catch(Exception e) {
            //do nothing
        }

        return alimentosArray;
    }

    public ArrayList<Date> getHorariosAfterLast() {
        ArrayList<Date> horariosArray = new ArrayList<Date>();
        ArrayList<Alimento> alimentosLastArray = getLastAlimentos();
        int k = alimentosLastArray.size()-1;
        int position = 0;
        for(int i = alimentosConsumidos.size()-1; i >= 0; i++) {
            if(alimentosConsumidos.get(i) == alimentosLastArray.get(k)) {
                k--;
                if(k<0) {
                    position = i-1;
                    break;
                }
            }
        }

        try {
            for(int i = position; i >= 0; i--) {
                horariosArray.add(horariosConsumo.get(i));
            }
        } catch(Exception e) {
            //do nothing
        }

        return horariosArray;
    }

    public int getCountFirstOfLastFood() {
        int indexFirstOfLastFood = 0;
        if(alimentosConsumidos.size() > 0) {
            char lastType;
            try {
                lastType = alimentosConsumidos.get(alimentosConsumidos.size() - 1).getTipo();
            } catch (NullPointerException e) {
                lastType = alimentosConsumidos.get(alimentosConsumidos.size() - 2).getTipo();
            }
            for(int i = (alimentosConsumidos.size()-1); i >= 0; i--) {
                if(alimentosConsumidos.get(i) != null) {
                    if (alimentosConsumidos.get(i).getTipo() == lastType) {
                        indexFirstOfLastFood = i - 1;
                    } else {
                        break;
                    }
                }
            }
            return indexFirstOfLastFood;
        }
        return -1;
    }

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
                    if(mes == mesA) {
                        if(ano == anoA) {
                            sData = "hoje";
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
                } else {
                    if(diaDoMes == (diaDoMesA-1)) {
                        if(mes == mesA) {
                            if(ano == anoA) {
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
        for(int i = (alimentosConsumidos.size() - 1); i >= 0; i--) {
            if(horariosConsumo.get(i) == horario) {
                Date data = getHorarioByIndex(i);
                Date dataAtual = new Date();

                int diaDoMes = Integer.parseInt(data.toString().substring(8, 10));
                int mes = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(data.toString().substring(4,7));
                int ano = Integer.parseInt(data.toString().substring(24));

                int diaDoMesA = Integer.parseInt(dataAtual.toString().substring(8, 10));
                int mesA = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(dataAtual.toString().substring(4,7));
                int anoA = Integer.parseInt(dataAtual.toString().substring(24));

                if(diaDoMes == diaDoMesA) {
                    if(mes == mesA) {
                        if(ano == anoA) {
                            sData = "hoje";
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
                } else {
                    if(diaDoMes == (diaDoMesA-1)) {
                        if(mes == mesA) {
                            if(ano == anoA) {
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

    public ArrayList<Alimento> getLastAlimentos() {
        ArrayList<Alimento> alimentosArray = new ArrayList<Alimento>();

        if(alimentosConsumidos.size() > 0) {
            char type = alimentosConsumidos.get(alimentosConsumidos.size()-1).getTipo();

            for(int i = alimentosConsumidos.size() - 1; i >= 0; i--) {
                if(alimentosConsumidos.get(i) != null) {
                    if(alimentosConsumidos.get(i).getTipo() == type) {
                        alimentosArray.add(alimentosConsumidos.get(i));
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            return alimentosArray;
        }
        return null;
    }

    public ArrayList<Date> getLastHorarios() {
        ArrayList<Date> horariosArray = new ArrayList<Date>();

        if(alimentosConsumidos.size() > 0) {
            char type = alimentosConsumidos.get(alimentosConsumidos.size()-1).getTipo();

            for(int i = alimentosConsumidos.size() - 1; i >= 0; i--) {
                if(alimentosConsumidos.get(i) != null) {
                    if(alimentosConsumidos.get(i).getTipo() == type) {
                        horariosArray.add(horariosConsumo.get(i));
                    } else {
                        break;
                    }
                }
            }

            return horariosArray;
        }
        return null;
    }

    public ArrayList<Alimento> getAlimentosByType(char type) {
        ArrayList<Alimento> alimentosArray = new ArrayList<Alimento>();
        if(alimentosConsumidos.size() > 0) {
            for(int i = alimentosConsumidos.size() - 1; i >= 0; i--) {
                if(alimentosConsumidos.get(i).getTipo() == type) {
                    alimentosArray.add(alimentosConsumidos.get(i));
                }
            }
            return alimentosArray;
        }
        return null;
    }

    public ArrayList<Date> getHorarios() { return horariosConsumo; }

    public Alimento getAlimentoByIndex(int index) { return alimentosConsumidos.get(index); }

    public Date getHorarioByIndex(int index) { return horariosConsumo.get(index); }

    public Date getHorarioByOrder(int order) {
        int k = 0;
        int position = 0;
        for(int i = horariosConsumo.size()-1; i >= 0; i--) {
            k++;
            if(k == order) {
                position = i;
                break;
            }
        }

        return horariosConsumo.get(position);
    }

    public ArrayList<Alimento> getAlimentosByNome(String nome) {
        ArrayList<Alimento> alimentosList = new ArrayList<Alimento>();
        for(int i = 0; i < alimentosConsumidos.size(); i++) {
            if (alimentosConsumidos.get(i).getNome() == nome) {
                alimentosList.add(alimentosConsumidos.get(i));
            }
        }
        return alimentosList;
    }

    public Alimento getAlimentoByHorario(Date horario) {
        for (int i = 0; i < alimentosConsumidos.size(); i++) {
            if(horariosConsumo.get(i) == horario) {
                return alimentosConsumidos.get(i);
            }
        }
        return null;
    }


}
