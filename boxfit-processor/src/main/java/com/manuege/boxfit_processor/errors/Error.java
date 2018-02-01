package com.manuege.boxfit_processor.errors;

import com.manuege.boxfit_processor.processor.Enviroment;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Created by Manu on 10/1/17.
 */

final public class Error {
    public static void putError(String reason, Element element) {
        String message = "Boxfit: " + reason;
        Enviroment.getEnvironment().getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
