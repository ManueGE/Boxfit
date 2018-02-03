package com.manuege.boxfit_processor.info;

import com.squareup.javapoet.ClassName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by Manu on 3/2/18.
 */

public class Utils {
    public static ClassName getSerializer(ProcessingEnvironment environment, TypeElement element) {
        return ClassName.get(environment.getElementUtils().getPackageOf(element).toString(),  getSerializer(element));
    }

    private static String getSerializer(TypeElement element) {
        String elementName = element.getSimpleName().toString();
        return elementName + "Serializer";
    }
}
