package com.manuege.boxfit_processor.info;

import com.manuege.boxfit.annotations.IdentityTransformer;
import com.manuege.boxfit.annotations.JsonSerializable;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfit.annotations.ToJsonIgnore;
import com.manuege.boxfit.annotations.ToJsonIncludeNull;
import com.manuege.boxfit.constants.Constants;
import com.manuege.boxfit.transformers.Transformer;
import com.manuege.boxfit_processor.errors.InvalidElementException;
import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by Manu on 1/2/18.
 */

public class FieldInfo {

    public enum Kind {
        NORMAL,
        TRANSFORMED,
        TO_ONE,
        TO_MANY,
        JSON_SERIALIZABLE;

        private boolean isRelationship() {
            return this == TO_ONE || this == TO_MANY || this == JSON_SERIALIZABLE;
        }
    }

    private boolean isPrimaryKey;
    private boolean isPrimitive;
    private String name;
    private String serializedName;
    private Kind kind;
    private boolean toJsonIncludeNull;
    private boolean toJsonIgnore;

    // Typename representing the type of the field
    private TypeName typeName;

    // Typename representing the type if the transformer of the field
    private TypeName transformerName;

    // Typename representing the type of the relationship target
    private TypeName relationshipName;

    // Typename representing the type of the relationship target serializer
    private TypeName relationshipSerializerName;

    // Typename representing the type of the value in a json
    private TypeName jsonFieldTypeName;

    private ClassInfo classInfo;

    public static FieldInfo newInstance(Element element, ClassInfo classInfo) throws InvalidElementException {

        Types typeUtil = Enviroment.getEnvironment().getTypeUtils();
        Elements elementUtil = Enviroment.getEnvironment().getElementUtils();

        // Check if serializable
        JsonSerializableField jsonSerializableField = element.getAnnotation(JsonSerializableField.class);
        if (jsonSerializableField == null) {
            return null;
        }

        // Check if valid
        if (element.getModifiers().contains(Modifier.PRIVATE)) {
            throw new InvalidElementException("JsonSerializableField annotated fields can't be private", element);
        }

        if (element.getModifiers().contains(Modifier.STATIC)) {
            throw new InvalidElementException("JsonSerializableField annotated fields can't be static", element);
        }

        // Basic info
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.classInfo = classInfo;

        TypeMirror typeMirror = element.asType();
        fieldInfo.isPrimitive = typeMirror.getKind().isPrimitive();
        if (fieldInfo.isPrimitive) {
            fieldInfo.typeName = TypeName.get(typeMirror).box();
        } else {
            fieldInfo.typeName = TypeName.get(typeMirror);
        }

        fieldInfo.kind = Kind.NORMAL;
        fieldInfo.name = element.getSimpleName().toString();
        fieldInfo.jsonFieldTypeName = fieldInfo.getTypeName();
        fieldInfo.toJsonIgnore = (element.getAnnotation(ToJsonIgnore.class) != null);
        fieldInfo.toJsonIncludeNull = (element.getAnnotation(ToJsonIncludeNull.class) != null);

        // Primary key
        Id id = element.getAnnotation(Id.class);
        fieldInfo.isPrimaryKey = (id != null);

        // Serializable info
        if (jsonSerializableField.value().equals(Constants.SERIALIZABLE_NULL_KEY_PATH)) {
            fieldInfo.serializedName = fieldInfo.name;
        } else {
            fieldInfo.serializedName = jsonSerializableField.value();
        }

        // Transformer
        try {
            jsonSerializableField.transformer().getName(); // Never should come here, just done to call the catch
        } catch (MirroredTypeException e) {

            TypeMirror transformerMirror = e.getTypeMirror();
            TypeName transformerName = TypeName.get(transformerMirror);

            if (!transformerName.equals(TypeName.get(IdentityTransformer.class))) {
                fieldInfo.kind = Kind.TRANSFORMED;
                fieldInfo.transformerName = transformerName;

                // We need the place where the interface is declared, in order to get their generic params
                TypeMirror mirror = transformerMirror;
                boolean found = false;
                while (!found && mirror != null) {
                    TypeElement typeElement = (TypeElement) typeUtil.asElement(mirror);
                    for (TypeMirror interfaceMirror : typeElement.getInterfaces()) {
                        if (TypeName.get(interfaceMirror).toString().startsWith(TypeName.get(Transformer.class).toString())) {
                            TypeMirror genericType = Utils.getGenericType(interfaceMirror, 0);
                            fieldInfo.jsonFieldTypeName = TypeName.get(genericType);
                            found = true;
                            break;
                        }
                    }
                    mirror = typeElement.getSuperclass();
                }
            }
        }

        // Relationships
        Element fieldTypeElement = typeUtil.asElement(typeMirror);
        if (fieldTypeElement instanceof TypeElement) {
            if (fieldTypeElement.getAnnotation(JsonSerializable.class) != null) {
                fieldInfo.kind = Kind.JSON_SERIALIZABLE;
            } else if (((TypeElement) fieldTypeElement).getQualifiedName().toString().startsWith(TypeName.get(ToOne.class).toString())) {
                fieldInfo.kind = Kind.TO_ONE;
            } else if (Utils.isList(typeMirror)) {
                fieldInfo.kind = Kind.TO_MANY;
            }

            if (fieldInfo.kind.isRelationship()) {
                if (fieldInfo.getTypeName() instanceof ParameterizedTypeName) {
                    ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) fieldInfo.getTypeName();
                    TypeName relationshipTypeName = parameterizedTypeName.typeArguments.get(0);
                    if (relationshipTypeName instanceof ClassName) {
                        fieldInfo.relationshipName = relationshipTypeName;

                        TypeMirror relationshipFieldMirror = Utils.getGenericType(typeMirror, 0);
                        TypeElement relationshipFieldElement = (TypeElement) typeUtil.asElement(relationshipFieldMirror);
                        fieldInfo.relationshipSerializerName = Utils.getSerializer(relationshipFieldElement);

                    } else if (relationshipTypeName instanceof TypeVariableName) {
                        TypeVariableName typeVariableName = (TypeVariableName) relationshipTypeName;
                        fieldInfo.relationshipName = classInfo.getGenericParamsMap().get(typeVariableName);
                        TypeElement relationshipFieldElement = elementUtil.getTypeElement(fieldInfo.relationshipName.toString());
                        fieldInfo.relationshipSerializerName = Utils.getSerializer(relationshipFieldElement);
                    }

                } else {
                    fieldInfo.relationshipName = fieldInfo.typeName;
                    fieldInfo.relationshipSerializerName = Utils.getSerializer((TypeElement) fieldTypeElement);
                }
            }
        }

        return fieldInfo;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public boolean isToJsonIncludeNull() {
        return toJsonIncludeNull;
    }

    public boolean isToJsonIgnore() {
        return toJsonIgnore;
    }

    public String getSerializedName() {
        return serializedName;
    }

    public String getName() {
        return name;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public TypeName getTransformerName() {
        return transformerName;
    }

    public Kind getKind() {
        return kind;
    }

    public TypeName getRelationshipName() {
        return relationshipName;
    }

    public TypeName getRelationshipSerializerName() {
        return relationshipSerializerName;
    }

    public TypeName getJsonFieldTypeName() {
        return jsonFieldTypeName;
    }

    public String getJsonGetterMethodName() {
        return Utils.getJsonGetterMethodName(getJsonFieldTypeName());
    }
}
