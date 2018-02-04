package com.manuege.boxfit_processor.errors;

import javax.lang.model.element.Element;

/**
 * Created by Manu on 4/2/18.
 */

public class InvalidElementException extends Exception {
    Element element;

    public InvalidElementException(String s, Element element) {
        super(s);
        this.element = element;
    }

    public void putError() {
        Error.putError(getMessage(), element);
    }
}
