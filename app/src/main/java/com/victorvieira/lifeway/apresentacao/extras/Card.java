package com.victorvieira.lifeway.apresentacao.extras;

public class Card {

    private char tipo;

    private String titulo;
    private String info;
    private String descricao;

    private int iconResource;

    public Card(char tipo, String titulo, String info, String descricao, int iconResource) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.info = info;
        this.descricao = descricao;
        this.iconResource = iconResource;
    }

    public Card(char tipo, String titulo, String descricao, int iconResource) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.info = null;
        this.descricao = descricao;
        this.iconResource = iconResource;
    }

    public char getTipo() { return tipo; }
    public void setTipo(char tipo) { this.tipo = tipo; }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getIconResource() {
        return iconResource;
    }
    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

}
