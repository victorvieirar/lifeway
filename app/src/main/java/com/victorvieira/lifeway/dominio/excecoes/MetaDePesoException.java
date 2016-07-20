package com.victorvieira.lifeway.dominio.excecoes;

public class MetaDePesoException extends Exception {

    @Override
    public String getMessage() {
        return "Insira uma meta de peso válida! (Ex: 70)";
    }
}
