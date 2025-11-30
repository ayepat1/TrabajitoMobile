package com.example.trabajito.classes;

import android.app.Application;

import com.example.trabajito.classes.TokenManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializa TokenManager u otras configuraciones globales aquí
        TokenManager.init(this);
    }
}