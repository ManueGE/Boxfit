package com.manuege.boxfitapp.model.java;

import com.manuege.boxfit.annotations.BoxfitClass;
import com.manuege.boxfit.annotations.BoxfitField;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Manu on 15/3/18.
 */

@Entity
@BoxfitClass
public class Child {
    @Id(assignable = true)
    @BoxfitField
    public long id;

    @BoxfitField
    public String value;

    public Child() {
    }

    public Child(long id, String value) {
        this.id = id;
        this.value = value;
    }
}
