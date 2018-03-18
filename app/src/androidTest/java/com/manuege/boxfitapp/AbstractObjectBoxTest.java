package com.manuege.boxfitapp;

import com.manuege.boxfitapp.model.MyObjectBox;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;

import io.objectbox.BoxStore;

public abstract class AbstractObjectBoxTest {
    protected BoxStore boxStore;

    @Before
    public void setUp() throws IOException {
        if (!App.getApp().getBoxStore().isClosed()) {
            App.getApp().getBoxStore().close();
        }
        boxStore = MyObjectBox.builder().androidContext(App.getApp()).build();
    }

    @After
    public void tearDown() throws Exception {
        if (boxStore != null) {
            boxStore.close();
            boxStore.deleteAllFiles();
        }
    }
}
