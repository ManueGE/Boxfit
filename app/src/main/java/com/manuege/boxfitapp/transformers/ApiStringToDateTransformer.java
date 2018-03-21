package com.manuege.boxfitapp.transformers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Manu on 11/3/18.
 */

public class ApiStringToDateTransformer extends StringToDateTransformer {
    @Override
    protected DateFormat getFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", getLocale());
    }
}
