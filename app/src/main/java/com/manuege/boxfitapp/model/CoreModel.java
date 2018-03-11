package com.manuege.boxfitapp.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Manu on 11/3/18.
 */

@Entity
@JsonSerializable
public class CoreModel {
    @JsonSerializableField
    @Id(assignable = true)
    public long id;

    @JsonSerializableField("long_class")
    public Long longClassField;

    @JsonSerializableField("integer")
    public int integerField;

    @JsonSerializableField("integer_class")
    public Integer integerClassField;

    @JsonSerializableField("bool")
    public boolean boolField;

    @JsonSerializableField("bool_class")
    public Boolean boolClassField;

    @JsonSerializableField("double")
    public double doubleField;

    @JsonSerializableField("double_class")
    public Double doubleClassField;

    @JsonSerializableField("string")
    public String stringField;
}
