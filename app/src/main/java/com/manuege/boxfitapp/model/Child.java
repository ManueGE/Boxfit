package com.manuege.boxfitapp.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Manu on 15/3/18.
 */

@Entity
@JsonSerializable
public class Child {
    @Id(assignable = true)
    @JsonSerializableField
    public long id;

    @JsonSerializableField
    public String value;

    public Child() {
    }

    public Child(long id, String value) {
        this.id = id;
        this.value = value;
    }
}
