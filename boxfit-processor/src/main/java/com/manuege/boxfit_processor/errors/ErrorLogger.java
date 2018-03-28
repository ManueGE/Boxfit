package com.manuege.boxfit_processor.errors;

import com.manuege.boxfit_processor.processor.Enviroment;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Created by Manu on 10/1/17.
 */

final public class ErrorLogger {
    public static void putError(String reason, Element element) {
        putMessage(reason, Diagnostic.Kind.ERROR, element);
    }

    public static void putWarning(String reason, Element element) {
        putMessage(reason, Diagnostic.Kind.WARNING, element);
    }

    private static void putMessage(String reason, Diagnostic.Kind kind, Element element) {
        String message = "BoxfitClass: " + reason;
        Enviroment.getEnvironment().getMessager().printMessage(kind, message, element);
    }
}
