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

}
