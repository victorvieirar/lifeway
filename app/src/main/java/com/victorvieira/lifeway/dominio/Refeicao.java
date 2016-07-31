package com.victorvieira.lifeway.dominio;

import com.victorvieira.lifeway.MySingleton;

import java.util.ArrayList;
import java.util.Date;

public class Refeicao {

    private int id;
    private char tipo;
    private ArrayList<Alimento> alimentosConsumidos = new ArrayList<Alimento>();
    private ArrayList<Date> horariosDeConsumo = new ArrayList<Date>();

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

    public int getId() { return id; }

    public Alimento getLastAlimento() { return alimentosConsumidos.get(alimentosConsumidos.size()-1); }

    public String getResumoDeAlimentos() {
        String alimentos = "";

        for(int i=0; i<alimentosConsumidos.size(); i++) {
            if(i==alimentosConsumidos.size()-1) {
                alimentos += alimentosConsumidos.get(i).getNome();
            } else {
                if(i == alimentosConsumidos.size() - 2) {
                    alimentos += alimentosConsumidos.get(i).getNome() + " e ";
                } else {
                    alimentos += alimentosConsumidos.get(i).getNome() + ", ";
                }
            }
        }

        return alimentos;
    }
}
