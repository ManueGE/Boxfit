package com.manuege.boxfit_processor.info;

import com.manuege.boxfit.annotations.BoxfitClass;
import com.manuege.boxfit.annotations.BoxfitId;
import com.manuege.boxfit.annotations.FromJsonIgnoreNull;
import com.manuege.boxfit.annotations.IdentityTransformer;
import com.manuege.boxfit.annotations.BoxfitField;
import com.manuege.boxfit.annotations.ToJsonIgnore;
import com.manuege.boxfit.annotations.ToJsonIncludeNull;
import com.manuege.boxfit.constants.Constants;
import com.manuege.boxfit.transformers.Transformer;
import com.manuege.boxfit_processor.errors.ErrorLogger;
import com.manuege.boxfit_processor.errors.InvalidElementException;
import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;
import com.squareup.kotlinpoet.TypeNames;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

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

        public boolean isRelationship() {
            return this == TO_ONE || this == TO_MANY;
        }

        public boolean targetIsBoxfitObject() {
            return isRelationship() || this == JSON_SERIALIZABLE;
        }
    }

    boolean isObjectBoxPrimaryKey;
    boolean isManualPrimaryKey;
    private boolean isPrimitive;
    private String name;
    private String serializedName;
    private Kind kind;
    private boolean fromJsonIgnoreNull;
    private boolean toJsonIncludeNull;
    private boolean toJsonIgnore;

    private Element element;

    // Typename representing the type of the field
    private TypeName typeName;

    // Typename representing the type of the field
    private com.squareup.kotlinpoet.TypeName ktTypeName;

    // Typename representing the type if the transformer of the field
    private TypeName transformerName;

    // Typename representing the type of the relationship target
    private TypeName relationshipName;

    // Typename representing the type of the relationship target serializer
    private TypeName relationshipSerializerName;

    // Typename representing the type of the value in a json
    private TypeName jsonFieldTypeName;

    // The enclosing class of the field
    private ClassInfo classInfo;

    // Tells if the field is nullable
    private boolean nullable;

    public static FieldInfo newInstance(Element element, ClassInfo classInfo) throws InvalidElementException {

        Types typeUtil = Enviroment.getEnvironment().getTypeUtils();
        Elements elementUtil = Enviroment.getEnvironment().getElementUtils();

        // Check if serializable
        BoxfitField boxfitField = element.getAnnotation(BoxfitField.class);
        if (boxfitField == null) {
            return null;
        }

        // Check if valid
        // TODO: fix for kotlin
        if (!classInfo.isKotlinClass()) {
            if (element.getModifiers().contains(Modifier.PRIVATE)) {
                throw new InvalidElementException("BoxfitField annotated fields can't be private", element);
            }

            if (element.getModifiers().contains(Modifier.STATIC)) {
                throw new InvalidElementException("BoxfitField annotated fields can't be static", element);
            }
        }

        // Basic info
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.element = element;
        fieldInfo.classInfo = classInfo;
        fieldInfo.nullable = element.getAnnotation(Nullable.class) != null;

        TypeMirror typeMirror = element.asType();

        // If generic, extract the concrete type
        TypeName typeName = TypeName.get(typeMirror);
        if (typeName instanceof TypeVariableName) {
            TypeName concreteTypeName = classInfo.getGenericParamsMap().get(typeName);
            typeMirror = Utils.getTypeMirrorFromTypeName(concreteTypeName);
        }

        fieldInfo.isPrimitive = typeMirror.getKind().isPrimitive();
        if (fieldInfo.isPrimitive) {
            fieldInfo.typeName = typeName.box();
        } else {
            fieldInfo.typeName = typeName;
        }

        if (classInfo.isKotlinClass()) {
            fieldInfo.ktTypeName = TypeNames.get(typeMirror);
        }

        fieldInfo.kind = Kind.NORMAL;
        fieldInfo.name = element.getSimpleName().toString();
        fieldInfo.jsonFieldTypeName = fieldInfo.getTypeName();
        fieldInfo.toJsonIgnore = (element.getAnnotation(ToJsonIgnore.class) != null);
        fieldInfo.toJsonIncludeNull = (element.getAnnotation(ToJsonIncludeNull.class) != null);
        fieldInfo.fromJsonIgnoreNull = (element.getAnnotation(FromJsonIgnoreNull.class) != null);

        // Primary key
        Id objectBoxId = element.getAnnotation(Id.class);
        fieldInfo.isObjectBoxPrimaryKey = (objectBoxId != null);

        BoxfitId boxfitId = element.getAnnotation(BoxfitId.class);
        fieldInfo.isManualPrimaryKey = (boxfitId != null);

        // Serializable info
        if (boxfitField.value().equals(Constants.SERIALIZABLE_NULL_KEY_PATH)) {
            fieldInfo.serializedName = fieldInfo.name;
        } else {
            fieldInfo.serializedName = boxfitField.value();
        }

        // Transformer
        try {
            boxfitField.transformer().getName(); // Never should come here, just done to call the catch
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
                            TypeMirror genericType = Utils.getGenericType(interfaceMirror, 1);
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
            if (fieldTypeElement.getAnnotation(BoxfitClass.class) != null) {
                fieldInfo.kind = Kind.JSON_SERIALIZABLE;
            } else if (((TypeElement) fieldTypeElement).getQualifiedName().toString().startsWith(TypeName.get(ToOne.class).toString())) {
                fieldInfo.kind = Kind.TO_ONE;
            } else if (Utils.isList(typeMirror)) {
                fieldInfo.kind = Kind.TO_MANY;
            }

            if (fieldInfo.kind.targetIsBoxfitObject()) {
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

                    } else if (relationshipTypeName instanceof WildcardTypeName) {
                        WildcardTypeName wildcardTypeName = (WildcardTypeName) relationshipTypeName;
                        ArrayList<TypeName> typeNames = new ArrayList<>(wildcardTypeName.upperBounds);
                        typeNames.addAll(wildcardTypeName.lowerBounds);
                        for (TypeName t : typeNames) {
                            TypeVariableName typeVariableName = TypeVariableName.get(t.toString());
                            TypeName concreteTypeName = classInfo.getGenericParamsMap().get(typeVariableName);

                            if (concreteTypeName != null) {
                                fieldInfo.relationshipName = concreteTypeName;
                                TypeElement relationshipFieldElement = elementUtil.getTypeElement(fieldInfo.relationshipName.toString());
                                fieldInfo.relationshipSerializerName = Utils.getSerializer(relationshipFieldElement);
                            }
                        }
                    }

                } else {
                    fieldInfo.relationshipName = fieldInfo.typeName;
                    fieldInfo.relationshipSerializerName = Utils.getSerializer((TypeElement) fieldTypeElement);
                }
            }
        }

        fieldInfo.validate();
        return fieldInfo;
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

    public boolean isFromJsonIgnoreNull() {
        return fromJsonIgnoreNull;
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

    public com.squareup.kotlinpoet.TypeName getKtTypeName() {
        return ktTypeName;
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

    public boolean isNullable() {
        return nullable;
    }

    private void validate() {
        Utils.ensureTypeNameHasEmptyInitializer(transformerName);;
        transformersDoesNotHaveEffectInRelationships();
    }

    private void transformersDoesNotHaveEffectInRelationships() {
        if (transformerName != null && getKind() != Kind.TRANSFORMED) {
            ErrorLogger.putWarning("Transformer doesn't have effect in relationships fields", element);
        }
    }
}
