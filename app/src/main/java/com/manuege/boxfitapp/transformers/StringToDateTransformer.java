package com.manuege.boxfitapp.transformers;

import com.manuege.boxfit.transformers.Transformer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Manu on 31/1/18.
 */

public abstract class StringToDateTransformer implements Transformer<String, Date> {
    @Override
    public Date transform(String object) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(getPattern(), getLocale());
        try {
            return dateFormat.parse(object);
        } catch (ParseException e) {
            return null;
        }
    }

    protected abstract String getPattern();

    protected Locale getLocale() {
        return Locale.getDefault();
    }

}
