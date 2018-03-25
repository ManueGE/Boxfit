package com.manuege.boxfitapp.api.model;

import com.manuege.boxfit.annotations.JsonSerializableField;

/**
 * Created by Manu on 25/3/18.
 */

public class SingleObjectResponse<T> {
    @JsonSerializableField
    public T data;
}
