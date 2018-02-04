package com.manuege.boxfit_processor.info;

import com.manuege.boxfit.annotations.IdentityTransformer;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfit.constants.Constants;
import com.squareup.javapoet.TypeName;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import io.objectbox.annotation.Id;

/**
 * Created by Manu on 1/2/18.
 */

public class FieldInfo {
    private TypeMirror type;
    private TypeName typeName;
    private boolean isPrimaryKey;
    private String name;
    private String serializedName;
    private boolean isStatic;
    private Class transformer;

    public static FieldInfo newInstance(Element element) {

        boolean valid = true;

        FieldInfo fieldInfo = new FieldInfo();

        fieldInfo.type = element.asType();
        if (fieldInfo.type.getKind().isPrimitive()) {
            fieldInfo.typeName = TypeName.get(Utils.getClassFromPrimitive(fieldInfo.type.toString()));
        } else {
            fieldInfo.typeName = TypeName.get(fieldInfo.type);
        }

        fieldInfo.type = element.asType();

        fieldInfo.isPrimaryKey = false;
        fieldInfo.name = element.getSimpleName().toString();

        String serializedName = fieldInfo.name;

        // search for serializer name annotation
        List<? extends AnnotationMirror> annotations = element.getAnnotationMirrors();
        for (AnnotationMirror annotation: annotations) {
            String annotationName = ((TypeElement) annotation.getAnnotationType().asElement()).getQualifiedName().toString();
            if (annotationName.equals(JsonSerializableField.class.getCanonicalName())) {
                String value = (String) Utils.getAnnotationValue(annotation, "value").getValue();
                if (value.equals(Constants.SERIALIZABLE_NULL_KEY_PATH)) {
                    serializedName = fieldInfo.name;
                } else {
                    serializedName = value;
                }

                Class transformerClass = (Class) Utils.getAnnotationValue(annotation, "transformer").getValue();
                if (!transformerClass.equals(IdentityTransformer.class)) {
                    fieldInfo.transformer = transformerClass;
                }
            }

            else if (annotationName.equals(Id.class.getCanonicalName())) {
                fieldInfo.isPrimaryKey = true;
            }
        }

        fieldInfo.serializedName = serializedName;


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

    public String getSerializedName() {
        return serializedName;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return type;
    }

    public TypeName getTypeName() {
        return typeName;
    }
}
