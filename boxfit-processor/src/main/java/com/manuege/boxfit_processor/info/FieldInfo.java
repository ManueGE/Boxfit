package com.manuege.boxfit_processor.info;

import com.manuege.boxfit_processor.errors.Error;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by Manu on 1/2/18.
 */

public class FieldInfo {
    private boolean isPrimaryKey;
    private boolean isIgnored;
    private String name;
    private String serializedName;
    private String type;
    private boolean isStatic;

    public static FieldInfo newInstance(Element element) {

        boolean valid = true;

        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.isPrimaryKey = false;
        fieldInfo.name = element.getSimpleName().toString();
        fieldInfo.type = element.asType().toString();

        String serializedName = fieldInfo.name;

        // search for serializer name annotation
        List<? extends AnnotationMirror> annotations = element.getAnnotationMirrors();
        for (AnnotationMirror annotation: annotations) {
            String annotationName = ((TypeElement) annotation.getAnnotationType().asElement()).getQualifiedName().toString();
            if (annotationName.equals("com.google.gson.annotations.SerializedName")) {
                AnnotationValue value = getAnnotationValue(annotation, "value");
                if (value != null) {
                    serializedName = (String) value.getValue();
                }
            }

            else if (annotationName.equals("io.realm.annotations.PrimaryKey")) {
                fieldInfo.isPrimaryKey = true;
            }

            else if (annotationName.equals("io.realm.annotations.Ignore")) {
                fieldInfo.isIgnored = true;
            }
        }

        fieldInfo.serializedName = serializedName;

        if (!fieldInfo.isIgnored()) {
            if (element.getModifiers().contains(Modifier.PRIVATE)) {
                Error.putError("Fields annotated with `@JsonSerializableField` can't be private", element);
                valid = false;
            }
        }

        fieldInfo.isStatic = element.getModifiers().contains(Modifier.STATIC);

        if (valid) {
            return fieldInfo;
        } else {
            return null;
        }
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isIgnored() {
        return isIgnored;
    }

    String getSerializedName() {
        return serializedName;
    }

    String getName() {
        return name;
    }

    String getType() {
        return type;
    }


    // Helpers
    private static AnnotationValue getAnnotationValue(AnnotationMirror annotation, String fieldName) {
        for (ExecutableElement executable : annotation.getElementValues().keySet()) {
            if (fieldName.equals(executable.getSimpleName().toString())) {
                return annotation.getElementValues().get(executable);
            }
        }
        return null;
    }
}
