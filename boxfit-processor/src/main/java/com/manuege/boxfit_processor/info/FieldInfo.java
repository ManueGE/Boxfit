package com.manuege.boxfit_processor.info;

import com.manuege.boxfit.annotations.IdentityTransformer;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfit.constants.Constants;
import com.manuege.boxfit.transformers.Transformer;
import com.manuege.boxfit_processor.errors.Error;
import com.manuege.boxfit_processor.errors.InvalidElementException;
import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.List;

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

    public static FieldInfo newInstance(Element element) throws InvalidElementException {

        Types typeUtil = Enviroment.getEnvironment().getTypeUtils();

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

        TypeMirror typeMirror = element.asType();
        if (typeMirror.getKind().isPrimitive()) {
            fieldInfo.typeName = TypeName.get(typeMirror).box();
        } else {
            fieldInfo.typeName = TypeName.get(typeMirror);
        }

        fieldInfo.kind = Kind.NORMAL;
        fieldInfo.name = element.getSimpleName().toString();
        fieldInfo.jsonFieldTypeName = fieldInfo.getTypeName();

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
                        TypeMirror genericType = Utils.getGenericType(interfaceMirror, 0);
                        fieldInfo.jsonFieldTypeName = TypeName.get(genericType);
                        break;
                    }
                }
            }
        }

        // Relationships
        Element fieldTypeElement = typeUtil.asElement(typeMirror);
        if (fieldTypeElement instanceof TypeElement) {
            if (((TypeElement) fieldTypeElement).getQualifiedName().toString().startsWith(TypeName.get(ToOne.class).toString())) {
                fieldInfo.kind = Kind.TO_ONE;
            } else if (((TypeElement) fieldTypeElement).getQualifiedName().toString().startsWith(TypeName.get(ToMany.class).toString())) {
                fieldInfo.kind = Kind.TO_MANY;
            } else if (((TypeElement) fieldTypeElement).getQualifiedName().toString().startsWith(TypeName.get(List.class).toString())) {
                fieldInfo.kind = Kind.TO_MANY;
            }


            if (fieldInfo.kind.equals(Kind.TO_MANY) || fieldInfo.kind.equals(Kind.TO_ONE)) {
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
                        Error.putWarning(typeVariableName.toString(), element);
                    }
                }
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
