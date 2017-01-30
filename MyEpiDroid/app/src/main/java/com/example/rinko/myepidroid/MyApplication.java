package com.example.rinko.myepidroid;

import android.app.Application;

/**
 * Created by Caroline on 31/01/2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    protected void initSingletons()
    {
        // Initialize the instance of MySingleton
        MySingleton.initInstance();
    }

    public void customAppMethod()
    {
        // Custom application method
    }
}
