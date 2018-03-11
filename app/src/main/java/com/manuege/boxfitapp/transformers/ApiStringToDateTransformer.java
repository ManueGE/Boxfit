package com.manuege.boxfitapp.transformers;

/**
 * Created by Manu on 11/3/18.
 */

public class ApiStringToDateTransformer extends StringToDateTransformer {
    @Override
    protected String getPattern() {
        return "yyyy-MM-dd";
    }
}
