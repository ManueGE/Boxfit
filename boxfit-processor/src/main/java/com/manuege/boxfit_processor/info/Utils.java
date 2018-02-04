package com.manuege.boxfit_processor.info;

import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.ClassName;

import java.util.HashMap;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Created by Manu on 3/2/18.
 */

public class Utils {
    public static ClassName getSerializer(ProcessingEnvironment environment, TypeElement element) {
        return ClassName.get(environment.getElementUtils().getPackageOf(element).toString(),  getSerializer(element));
    }

    private static String getSerializer(TypeElement element) {
        String fullName = element.getQualifiedName().toString();
        String pack = Enviroment.getEnvironment().getElementUtils().getPackageOf(element).toString();
        String elementName = fullName.substring(pack.length() + 1).replace(".", "$");
        return elementName + "Serializer";
    }

    public static AnnotationValue getAnnotationValue(AnnotationMirror annotation, String fieldName) {
        for (ExecutableElement executable : annotation.getElementValues().keySet()) {
            if (fieldName.equals(executable.getSimpleName().toString())) {
                return annotation.getElementValues().get(executable);
            }
        }
        return null;
    }

    public static Class getClassFromPrimitive(String primitive) {
        HashMap<String, Class> primitivesAndWrapperClasses = new HashMap<>();
        primitivesAndWrapperClasses.put("byte", Byte.class);
        primitivesAndWrapperClasses.put("short", Short.class);
        primitivesAndWrapperClasses.put("int", Integer.class);
        primitivesAndWrapperClasses.put("long", Long.class);
        primitivesAndWrapperClasses.put("float", Float.class);
        primitivesAndWrapperClasses.put("double", Double.class);
        primitivesAndWrapperClasses.put("boolean", Boolean.class);
        primitivesAndWrapperClasses.put("char", Character.class);
        return primitivesAndWrapperClasses.get(primitive);
    }
}
