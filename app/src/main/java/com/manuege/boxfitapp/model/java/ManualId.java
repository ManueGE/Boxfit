package com.manuege.boxfitapp.model.java;

import com.manuege.boxfit.annotations.BoxfitClass;
import com.manuege.boxfit.annotations.BoxfitField;
import com.manuege.boxfit.annotations.BoxfitId;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
@BoxfitClass
public class ManualId {
    @Id
    long id;

    @BoxfitId
    @BoxfitField("id")
    public String manualId;

    @BoxfitField
    public String value;

    public ManualId() {
    }

    public ManualId(String manualId, String value) {
        this.manualId = manualId;
        this.value = value;
    }
}
