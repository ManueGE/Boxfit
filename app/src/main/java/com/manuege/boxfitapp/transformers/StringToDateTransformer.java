package com.manuege.boxfitapp.transformers;

import com.manuege.boxfit.transformers.Transformer;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Manu on 31/1/18.
 */

public abstract class StringToDateTransformer implements Transformer<Date, String> {

    @Override
    public Date transform(String object) {
        try {
            return getFormat().parse(object);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String inverseTransform(Date object) {
        if (object == null) {
            return null;
        }
        return getFormat().format(object);
    }

    protected abstract DateFormat getFormat();

    protected Locale getLocale() {
        return Locale.getDefault();
    }

}
