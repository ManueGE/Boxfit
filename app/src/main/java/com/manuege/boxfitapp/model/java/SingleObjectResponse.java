package com.manuege.boxfitapp.model.java;

import com.manuege.boxfit.annotations.BoxfitField;

/**
 * Created by Manu on 25/3/18.
 */

public class SingleObjectResponse<T> {
    @BoxfitField
    public T data;
}
