package com.victorvieira.lifeway.persistencia;

import com.victorvieira.lifeway.App;
import com.victorvieira.lifeway.dominio.Usuario;

public class BancoDeDados {

    private App app;
    private Usuario usuario = null;

    public BancoDeDados() {
        super();
        app = new App();
    }

    public void setupUsuario(Usuario usuario) { this.usuario = usuario; }

    public Usuario getUsuario() { return usuario; }
    public App getApp() { return app; }



}

