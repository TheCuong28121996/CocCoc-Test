package com.example.cococ;

import com.google.gson.Gson;

public class Application extends android.app.Application {

    private static Application instance;
    private Gson mGSon;

    public static synchronized Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        mGSon = new Gson();
    }

    public Gson getGSon() {
        return mGSon;
    }
}
