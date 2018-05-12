package com.manuege.boxfitapp.model.java;

import com.manuege.boxfit.annotations.BoxfitClass;
import com.manuege.boxfit.annotations.BoxfitField;
import com.manuege.boxfit.annotations.ToJsonIgnore;
import com.manuege.boxfit.annotations.ToJsonIncludeNull;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by Manu on 25/3/18.
 */

@Entity
@BoxfitClass
public class ToJsonTestObject {

    @Id
    long id;

    @BoxfitField("long_class")
    @ToJsonIncludeNull
    public Long longClassField;

    @BoxfitField("integer_class")
    @ToJsonIncludeNull
    public Integer integerClassField;

    @BoxfitField("bool_class")
    @ToJsonIncludeNull
    public Boolean boolClassField;

    @BoxfitField("double_class")
    @ToJsonIncludeNull
    public Double doubleClassField;

    @BoxfitField("string")
    @ToJsonIncludeNull
    public String stringField;

    @ToJsonIncludeNull
    @BoxfitField
    public ToOne<Child> toOne;

    @BoxfitField("ignored")
    @ToJsonIgnore
    public String ignoredField;
}
