package com.manuege.boxfitapp.api.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;

/**
 * Created by Manu on 25/3/18.
 */

@JsonSerializable
public class NoObjectBoxObject {
    @JsonSerializableField
    public int integer;

    @JsonSerializableField
    public String string;
}
