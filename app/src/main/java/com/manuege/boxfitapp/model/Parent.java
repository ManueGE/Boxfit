package com.manuege.boxfitapp.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfitapp.transformers.SlashIdTransformer;
import com.manuege.boxfitapp.transformers.ApiStringToDateTransformer;
import com.manuege.boxfitapp.transformers.EnumToIntTransformer;

import java.util.Date;
import java.util.List;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by Manu on 11/3/18.
 */

@Entity
@JsonSerializable(transformer = SlashIdTransformer.class)
public class Parent {

    public enum Enum {
        NONE(0),
        ONE(1),
        TWO(2),
        THREE(3);

        Integer value;

        Enum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

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

    @JsonSerializableField
    public String serializerNameInferred;

    @JsonSerializableField("first.second.third.key")
    public String keyPathField;

    @JsonSerializableField("a.b.c.d")
    public String fakeKeyPathField;

    @JsonSerializableField
    public ToOne<Child> toOne;

    @JsonSerializableField
    public ToMany<Child> toMany;

    @JsonSerializableField
    public List<Child> list;

    @JsonSerializableField(value="enum", transformer = EnumToIntTransformer.class)
    @Convert(converter = EnumToIntTransformer.class, dbType = Integer.class)
    public Enum enumField;

    @JsonSerializableField(value="date", transformer = ApiStringToDateTransformer.class)
    public Date dateField;
}
