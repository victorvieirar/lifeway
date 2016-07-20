package com.victorvieira.lifeway.dominio.excecoes;

public class AlturaException extends Exception {

    @Override
    public String getMessage() {
        return "Insira uma altura válida! (Ex: 1.65)";
    }
}
