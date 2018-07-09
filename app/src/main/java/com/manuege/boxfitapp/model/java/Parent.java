package com.manuege.boxfitapp.model.java;

import com.manuege.boxfit.annotations.BoxfitClass;
import com.manuege.boxfit.annotations.BoxfitField;
import com.manuege.boxfit.annotations.FromJsonIgnoreNull;
import com.manuege.boxfitapp.transformers.ApiStringToDateTransformer;
import com.manuege.boxfitapp.transformers.EnumToIntTransformer;
import com.manuege.boxfitapp.transformers.ListIntToStringTransformer;
import com.manuege.boxfitapp.transformers.SlashIdTransformer;

import java.util.ArrayList;
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
@BoxfitClass(transformer = SlashIdTransformer.class)
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

    @BoxfitField
    @Id(assignable = true)
    public long id;

    @BoxfitField("long_class")
    public Long longClassField;

    @BoxfitField("integer")
    public int integerField;

    @BoxfitField("integer_class")
    public Integer integerClassField;

    @BoxfitField("bool")
    public boolean boolField;

    @BoxfitField("bool_class")
    public Boolean boolClassField;

    @BoxfitField("double")
    public double doubleField;

    @BoxfitField("double_class")
    public Double doubleClassField;

    @BoxfitField("string")
    public String stringField;

    @BoxfitField
    public String serializerNameInferred;

    @BoxfitField("first.second.third.key")
    public String keyPathField;

    @BoxfitField("a.b.c.d")
    public String fakeKeyPathField;

    @BoxfitField
    public ToOne<Child> toOne;

    @BoxfitField
    public ToMany<Child> toMany;

    @BoxfitField
    public List<Child> list;

    @BoxfitField(value="enum", transformer = EnumToIntTransformer.class)
    @Convert(converter = EnumToIntTransformer.class, dbType = Integer.class)
    public Enum enumField;

    @BoxfitField(value="date", transformer = ApiStringToDateTransformer.class)
    public Date dateField;

    @BoxfitField
    @FromJsonIgnoreNull
    public int fromJsonIgnoreNull;

    @Convert(converter = ListIntToStringTransformer.class, dbType = String.class)
    @BoxfitField
    public List<Integer> listInt = new ArrayList();
}
