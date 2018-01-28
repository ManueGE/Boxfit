package com.manuege.boxfit;

import android.app.Application;

import com.manuege.boxfit.model.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Created by Manu on 28/1/18.
 */

public class App extends Application {
    private static App app;
    private BoxStore boxStore;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
