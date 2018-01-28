package com.manuege.boxfit;

import com.manuege.boxfit.model.MyObjectBox;

import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

import io.objectbox.BoxStore;

public abstract class AbstractObjectBoxTest {
    protected File boxStoreDir;
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
