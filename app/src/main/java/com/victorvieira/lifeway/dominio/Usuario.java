package com.victorvieira.lifeway.dominio;

import android.widget.EditText;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.dominio.excecoes.AlturaException;
import com.victorvieira.lifeway.dominio.excecoes.MetaDePesoException;
import com.victorvieira.lifeway.dominio.excecoes.PesoException;

import java.util.Date;

public class Usuario extends Throwable {

    private String nome;
    private Date dataNascimento;
    private double peso;
    private double metaDePeso = 0;
    private double altura;
    private Consumo consumo;

    public Usuario() { super(); }

    public Usuario(String nome, Date dataNascimento, EditText editPeso, EditText editAltura) throws AlturaException, PesoException {
        this.nome = nome;
        this.dataNascimento = dataNascimento;

        try {
            this.peso = Double.parseDouble(editPeso.getText().toString());
        } catch (Exception e) {
            throw new PesoException();
        }

        try {
            this.altura = Double.parseDouble(editAltura.getText().toString());
        } catch (Exception e) {
            throw new AlturaException();
        }

        consumo = null;
    }

    public void addRefeicao(Alimento alimento) {
        if(consumo == null) {
            consumo = new Consumo();
        }

        Date horario = new Date();

        if(isAfterLast(horario)) {
            consumo.addRefeicao(null, horario);
        }

        consumo.addRefeicao(alimento, horario);
    }

    public void addRefeicao(Alimento alimento, Date horario) { consumo.addRefeicao(alimento, horario); }

    public Consumo getConsumo() {
        return consumo;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public double getMetaDePeso() { return metaDePeso; }

    public void setMetaDePeso(EditText editMetaDePeso) throws MetaDePesoException {
        try {
            this.metaDePeso = Double.parseDouble(editMetaDePeso.getText().toString());
        } catch (Exception e) {
            throw new MetaDePesoException();
        }

    }

    public double getAltura() {
        return altura;
    }

    public boolean isAfterLast(Date horario) {
        int diaDoMes = Integer.parseInt(horario.toString().substring(8,10));
        int mes = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(horario.toString().substring(4,7));
        int ano = Integer.parseInt(horario.toString().substring(24));
        int diaDoMesL;
        int mesL;
        int anoL;
        try {
            diaDoMesL = Integer.parseInt(consumo.getHorarios().get(consumo.getHorarios().size()-1).toString().substring(8,10));
            mesL = MySingleton.getBancoDeDados().getApp().getIndexOfMonth(consumo.getHorarios().get(consumo.getHorarios().size()-1).toString().substring(4,7));
            anoL = Integer.parseInt(consumo.getHorarios().get(consumo.getHorarios().size()-1).toString().substring(24));
        } catch(Exception e) {
            return false;
        }

        if(diaDoMes > diaDoMesL) {
            return true;
        } else {
            if(mes > mesL) {
                return true;
            } else {
                if(ano > anoL) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

}
