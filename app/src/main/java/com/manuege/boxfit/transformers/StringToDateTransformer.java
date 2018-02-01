package com.manuege.boxfit.transformers;

import com.manuege.boxfit.library.transformers.Transformer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manu on 31/1/18.
 */

public class StringToDateTransformer implements Transformer<String, Date> {
    @Override
    public Date transform(String object) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(object);
        } catch (ParseException e) {
            return null;
        }
    }
}
