package com.victorvieira.lifeway;

import com.victorvieira.lifeway.persistencia.ControladorBD;

public final class MySingleton {

    private static MySingleton instance = new MySingleton();

    private static ControladorBD banco = new ControladorBD();

    private MySingleton() {
        super();
    }

    public static ControladorBD getBancoDeDados() { return banco; }
    public static MySingleton getInstance() {
        return instance;
    }

}
