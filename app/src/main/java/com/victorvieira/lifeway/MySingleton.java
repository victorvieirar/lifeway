package com.victorvieira.lifeway;

import com.victorvieira.lifeway.persistencia.BancoDeDados;

public final class MySingleton {

    private static MySingleton instance = new MySingleton();

    private static BancoDeDados banco = new BancoDeDados();

    private MySingleton() {
        super();
    }

    public static BancoDeDados getBancoDeDados() { return banco; }
    public static MySingleton getInstance() {
        return instance;
    }

}
