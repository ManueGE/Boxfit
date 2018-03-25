package com.manuege.boxfitapp.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfit.annotations.ToJsonIgnore;
import com.manuege.boxfit.annotations.ToJsonIncludeNull;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by Manu on 25/3/18.
 */

@Entity
@JsonSerializable
public class ToJsonTestObject {

    @Id
    long id;

    @JsonSerializableField("long_class")
    @ToJsonIncludeNull
    public Long longClassField;

    @JsonSerializableField("integer_class")
    @ToJsonIncludeNull
    public Integer integerClassField;

    @JsonSerializableField("bool_class")
    @ToJsonIncludeNull
    public Boolean boolClassField;

    @JsonSerializableField("double_class")
    @ToJsonIncludeNull
    public Double doubleClassField;

    @JsonSerializableField("string")
    @ToJsonIncludeNull
    public String stringField;

    @ToJsonIncludeNull
    @JsonSerializableField
    public ToOne<Child> toOne;

    @JsonSerializableField("ignored")
    @ToJsonIgnore
    public String ignoredField;
}
