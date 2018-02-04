package com.manuege.boxfit_processor.info;

import java.util.ArrayList;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by Manu on 1/2/18.
 */

public class ClassInfo {
    private TypeElement typeElement;
    private String className;
    private FieldInfo primaryKey;
    private ArrayList<FieldInfo> fields;

    public static ClassInfo newInstance(TypeElement element) {
        boolean valid = true;
        ClassInfo classInfo = new ClassInfo();
        classInfo.typeElement = element;
        classInfo.className = element.getSimpleName().toString();

        classInfo.fields = new ArrayList<>();
        for (Element fieldElement: element.getEnclosedElements()) {
            if (fieldElement.getKind() != ElementKind.FIELD ||
                    fieldElement.getModifiers().contains(Modifier.STATIC)) {
                continue;
            }

            FieldInfo info = FieldInfo.newInstance(fieldElement);
            if (info == null) {
                valid = false;
            } else {
                classInfo.fields.add(info);
                if (info.isPrimaryKey()) {
                    classInfo.primaryKey = info;
                }
            }
        }

        if (valid) {
            return classInfo;
        } else {
            return null;
        }
    }

    boolean hasPrimaryKey() {
        return primaryKey != null;
    }

    public String getClassName() {
        return className;
    }

    public String getPackage(ProcessingEnvironment environment) {
        return environment.getElementUtils().getPackageOf(typeElement).toString();
    }

    public FieldInfo getPrimaryKey() {
        return primaryKey;
    }

    public ArrayList<FieldInfo> getFields() {
        return fields;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }
}
