package com.manuege.boxfit_processor.info;

import com.manuege.boxfit.annotations.JSONObjectIdentityTransformer;
import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit_processor.errors.InvalidElementException;
import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Manu on 1/2/18.
 */

public class ClassInfo {
    private TypeName type;
    private TypeElement typeElement;
    private FieldInfo primaryKey;
    private List<FieldInfo> fields;
    private TypeName transformer;

    public static ClassInfo newInstance(TypeElement element) throws InvalidElementException {
        ClassInfo classInfo = new ClassInfo();
        classInfo.typeElement = element;
        classInfo.type = TypeName.get(element.asType());

        // Transformer
        // http://hauchee.blogspot.com.es/2015/12/compile-time-annotation-processing-getting-class-value.html
        JsonSerializable jsonSerializableAnnotation = element.getAnnotation(JsonSerializable.class);
        try {
            jsonSerializableAnnotation.transformer().getName();
        } catch (MirroredTypeException e) {
            TypeMirror typeMirror = e.getTypeMirror();
            TypeName typeName = TypeName.get(typeMirror);
            if (!typeName.equals(TypeName.get(JSONObjectIdentityTransformer.class))) {
                classInfo.transformer = typeName;
            }
        }

        // Fields
        classInfo.fields = getFields(element);

        // Primary key
        for (FieldInfo fieldInfo: classInfo.fields) {
            if (fieldInfo.isPrimaryKey()) {
                classInfo.primaryKey = fieldInfo;
            }
        }

        return classInfo;
    }

    private static List<FieldInfo> getFields(TypeElement element) throws InvalidElementException {
        ArrayList<FieldInfo> fields = new ArrayList<>();

        TypeMirror superclassMirror = element.getSuperclass();
        if (superclassMirror != null) {
            Element superclass = Enviroment.getEnvironment().getTypeUtils().asElement(superclassMirror);
            if (superclass instanceof TypeElement) {
                fields.addAll(getFields((TypeElement) superclass));
            }
        }

        for (Element fieldElement: element.getEnclosedElements()) {
            if (fieldElement.getKind() != ElementKind.FIELD) {
                continue;
            }

            FieldInfo info = FieldInfo.newInstance(fieldElement);
            if (info != null) {
                fields.add(info);
            }
        }
        return fields;
    }

    public TypeName getType() {
        return type;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public FieldInfo getPrimaryKey() {
        return primaryKey;
    }

    public List<FieldInfo> getFields() {
        return fields;
    }

    public TypeName getTransformer() {
        return transformer;
    }

    public boolean hasPrimaryKey() {
        return primaryKey != null;
    }

    public String getPackage() {
        return Enviroment.getEnvironment().getElementUtils().getPackageOf(typeElement).toString();
    }
}
