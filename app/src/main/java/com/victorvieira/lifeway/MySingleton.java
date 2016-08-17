package com.victorvieira.lifeway;

import com.victorvieira.lifeway.persistencia.ControladorBD;

public final class MySingleton {

    private static MySingleton instance = new MySingleton();
    private static App app = new App();

    private MySingleton() {
        super();
    }

    public static App getApp() { return app; }
    public static MySingleton getInstance() {
        return instance;
    }

}
