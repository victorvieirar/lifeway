package com.victorvieira.lifeway.dominio.excecoes;

public class PesoException extends Exception {

    @Override
    public String getMessage() {
        return "Insira um peso válido! (Ex: 55)";
    }
}
