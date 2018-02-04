package com.manuege.boxfit_processor.info;

import com.manuege.boxfit.annotations.IdentityTransformer;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfit.constants.Constants;
import com.manuege.boxfit.transformers.Transformer;
import com.manuege.boxfit_processor.errors.InvalidElementException;
import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by Manu on 1/2/18.
 */

public class FieldInfo {

    public enum Kind {
        NORMAL,
        TRANSFORMED,
        TO_ONE,
        TO_MANY
    }

    private boolean isPrimaryKey;
    private String name;
    private String serializedName;
    private Kind kind;

    private TypeMirror typeMirror;
    private TypeName typeName;

    private TypeMirror transformerMirror;
    private TypeName transformerName;

    private TypeMirror relationshipFieldMirror;
    private TypeName relationshipFieldName;
    private TypeElement relationshipFieldElement;

    private TypeName jsonFieldTypeName;

    public static FieldInfo newInstance(Element element) throws InvalidElementException {

        Types typeUtil = Enviroment.getEnvironment().getTypeUtils();

        // Check if serializable
        List<? extends AnnotationMirror> annotations = element.getAnnotationMirrors();
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

        fieldInfo.typeMirror = element.asType();
        if (fieldInfo.typeMirror.getKind().isPrimitive()) {
            fieldInfo.typeName = TypeName.get(fieldInfo.typeMirror).box();
        } else {
            fieldInfo.typeName = TypeName.get(fieldInfo.typeMirror);
        }

        fieldInfo.kind = Kind.NORMAL;
        fieldInfo.typeMirror = element.asType();
        fieldInfo.name = element.getSimpleName().toString();

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
                TypeElement typeElement = (TypeElement) typeUtil.asElement(transformerMirror);

                // We need the place where the interface is declared, in order to get their generic params
                for (TypeMirror interfaceMirror: typeElement.getInterfaces()) {
                    if (TypeName.get(interfaceMirror).toString().startsWith(TypeName.get(Transformer.class).toString())) {
                        fieldInfo.transformerMirror = interfaceMirror;
                        break;
                    }
                }
            }
        }

        // To One Relationship
        Element fieldTypeElement = typeUtil.asElement(fieldInfo.typeMirror);
        if (fieldTypeElement instanceof TypeElement) {
            if (((TypeElement) fieldTypeElement).getQualifiedName().toString().startsWith(TypeName.get(ToOne.class).toString())) {
                fieldInfo.kind = Kind.TO_ONE;
            } else if (((TypeElement) fieldTypeElement).getQualifiedName().toString().startsWith(TypeName.get(ToMany.class).toString())) {
                fieldInfo.kind = Kind.TO_MANY;
            }
            // TODO:
            /*else if (((TypeElement) fieldTypeElement).getQualifiedName().toString().startsWith(TypeName.get(List.class).toString())) {
                fieldInfo.kind = Kind.TO_MANY;
            }*/


            if (fieldInfo.kind.equals(Kind.TO_MANY) || fieldInfo.kind.equals(Kind.TO_ONE)) {
                fieldInfo.relationshipFieldMirror = Utils.getGenericType(fieldInfo.typeMirror, 0);
                fieldInfo.relationshipFieldElement = (TypeElement) typeUtil.asElement(fieldInfo.relationshipFieldMirror);
                fieldInfo.relationshipFieldName = TypeName.get(fieldInfo.relationshipFieldMirror);
            }
        }

        return fieldInfo;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }


    public String getSerializedName() {
        return serializedName;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
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

    public TypeElement getRelationshipFieldElement() {
        return relationshipFieldElement;
    }

    public TypeName getRelationshipFieldName() {
        return relationshipFieldName;
    }

    public TypeName getJsonFieldTypeName() {
        if (jsonFieldTypeName == null) {
            if (transformerName == null) {
                jsonFieldTypeName = getTypeName();
            } else {
                TypeMirror genericType = Utils.getGenericType(transformerMirror, 0);
                jsonFieldTypeName = TypeName.get(genericType);
            }
        }
        return jsonFieldTypeName;
    }

    private static HashMap<Class, String> classesAndMethods;
    public String getJsonGetterMethodName() {
        return Utils.getJsonGetterMethodName(getJsonFieldTypeName());
    }
}
