package com.manuege.boxfit_processor.info;

import com.manuege.boxfit.annotations.JSONObjectIdentityTransformer;
import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit_processor.errors.InvalidElementException;
import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import io.objectbox.annotation.Entity;

/**
 * Created by Manu on 1/2/18.
 */

public class ClassInfo {
    private TypeName type;
    private Boolean isEntity;
    private TypeElement typeElement;
    private FieldInfo primaryKey;
    private List<FieldInfo> fields;
    private TypeName transformer;
    private HashMap<TypeVariableName, TypeName> genericParamsMap;

    public static ClassInfo newInstance(TypeElement element) throws InvalidElementException {

        ClassInfo classInfo = new ClassInfo();
        classInfo.typeElement = element;

        TypeMirror typeMirror = element.asType();
        classInfo.type = TypeName.get(typeMirror);

        classInfo.validate();

        // Transformer
        // http://hauchee.blogspot.com.es/2015/12/compile-time-annotation-processing-getting-class-value.html
        JsonSerializable jsonSerializableAnnotation = element.getAnnotation(JsonSerializable.class);
        try {
            jsonSerializableAnnotation.transformer().getName();
        } catch (MirroredTypeException e) {
            TypeMirror transformerTypeMirror = e.getTypeMirror();
            TypeName typeName = TypeName.get(transformerTypeMirror);
            if (!typeName.equals(TypeName.get(JSONObjectIdentityTransformer.class))) {
                classInfo.transformer = typeName;
            }
        }

        classInfo.isEntity = element.getAnnotation(Entity.class) != null;

        // Fields
        classInfo.genericParamsMap = getGenericMap(element);
        classInfo.fields = getFields(element, classInfo);

        // Primary key
        for (FieldInfo fieldInfo: classInfo.fields) {
            if (fieldInfo.isPrimaryKey()) {
                classInfo.primaryKey = fieldInfo;
            }
        }

        return classInfo;
    }

    private static List<FieldInfo> getFields(TypeElement element, ClassInfo classInfo) throws InvalidElementException {
        ArrayList<FieldInfo> fields = new ArrayList<>();

        TypeMirror superclassMirror = element.getSuperclass();
        if (superclassMirror != null) {
            Element superclass = Enviroment.getEnvironment().getTypeUtils().asElement(superclassMirror);
            if (superclass instanceof TypeElement) {
                fields.addAll(getFields((TypeElement) superclass, classInfo));
            }
        }

        for (Element fieldElement: element.getEnclosedElements()) {
            if (fieldElement.getKind() != ElementKind.FIELD) {
                continue;
            }

            FieldInfo info = FieldInfo.newInstance(fieldElement, classInfo);
            if (info != null) {
                fields.add(info);
            }
        }
        return fields;
    }

    // https://stackoverflow.com/questions/37542768/how-to-get-the-qualified-class-name-of-generic-type-in-annotation-processing
    // Check cases
    private static HashMap<TypeVariableName, TypeName> getGenericMap(TypeElement typeElement) {

        HashMap<TypeVariableName, TypeName> hashMap = new HashMap<>();
        Types typeUtils = Enviroment.getEnvironment().getTypeUtils();
        TypeMirror superclassMirror = typeElement.getSuperclass();
        if (superclassMirror != null) {

            List<? extends TypeMirror> concreteTypes = null;
            if (superclassMirror instanceof DeclaredType) {
                concreteTypes = ((DeclaredType) superclassMirror).getTypeArguments();
            }

            List<? extends TypeParameterElement> genericTypes = null;
            Element superclass = typeUtils.asElement(superclassMirror);
            if (superclass instanceof TypeElement) {
                hashMap.putAll(getGenericMap((TypeElement) superclass));

                genericTypes = ((TypeElement) superclass).getTypeParameters();
            }

            if (concreteTypes != null && genericTypes != null) {
                for (int i = 0; i < concreteTypes.size(); i++) {
                    TypeVariableName variableName = TypeVariableName.get(genericTypes.get(i));
                    TypeName typeName = TypeName.get(concreteTypes.get(i));
                    hashMap.put(variableName, typeName);
                }
            }
        }

        return hashMap;
    }

    public TypeName getType() {
        return type;
    }

    public Boolean isEntity() {
        return isEntity;
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

    public HashMap<TypeVariableName, TypeName> getGenericParamsMap() {
        return genericParamsMap;
    }

    private void validate() throws InvalidElementException{
        if (typeElement.getTypeParameters().size() > 0) {
            throw new InvalidElementException("Classes annotated with @JsonSerializable can't be generic. Please, add a concrete subclass and annotate it", typeElement);
        }

        Utils.ensureTypeNameHasEmptyInitializer(type);
    }
}
