package com.manuege.boxfitapp.api.model;

import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfitapp.model.Parent;

/**
 * Created by Manu on 25/3/18.
 */

@JsonSerializable
public class ParentResponse {
    @JsonSerializableField
    public Parent parent;
}
