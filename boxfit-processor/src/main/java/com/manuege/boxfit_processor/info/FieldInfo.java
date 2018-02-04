package com.manuege.boxfit_processor.info;

import com.manuege.boxfit.annotations.IdentityTransformer;
import com.manuege.boxfit.annotations.JsonSerializableField;
import com.manuege.boxfit.constants.Constants;
import com.manuege.boxfit.transformers.Transformer;
import com.manuege.boxfit_processor.errors.Error;
import com.manuege.boxfit_processor.errors.InvalidElementException;
import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.TypeName;

import org.json.JSONArray;
import org.json.JSONObject;

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

/**
 * Created by Manu on 1/2/18.
 */

public class FieldInfo {
    private TypeMirror type;
    private TypeName typeName;
    private boolean isPrimaryKey;
    private String name;
    private String serializedName;
    private TypeMirror transformerMirror;
    private TypeName transformer;
    private TypeName jsonFieldTypeName;

    public static FieldInfo newInstance(Element element) throws InvalidElementException {

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

        fieldInfo.type = element.asType();
        if (fieldInfo.type.getKind().isPrimitive()) {
            fieldInfo.typeName = TypeName.get(fieldInfo.type).box();
        } else {
            fieldInfo.typeName = TypeName.get(fieldInfo.type);
        }

        fieldInfo.type = element.asType();
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

            TypeMirror typeMirror = e.getTypeMirror();
            TypeName typeName = TypeName.get(typeMirror);
            Types typeUtil = Enviroment.getEnvironment().getTypeUtils();

            if (!typeName.equals(TypeName.get(IdentityTransformer.class))) {
                fieldInfo.transformer = typeName;
                TypeElement typeElement = (TypeElement) typeUtil.asElement(typeMirror);

                // We need the place where the interface is declared, in order to get their generic params
                for (TypeMirror interfaceMirror: typeElement.getInterfaces()) {
                    if (TypeName.get(interfaceMirror).toString().startsWith(TypeName.get(Transformer.class).toString())) {
                        fieldInfo.transformerMirror = interfaceMirror;
                        break;
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

    public TypeMirror getType() {
        return type;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public TypeName getTransformer() {
        return transformer;
    }

    public TypeName getJsonFieldTypeName() {
        if (jsonFieldTypeName == null) {
            if (transformer == null) {
                jsonFieldTypeName = getTypeName();
            } else {
                Error.putWarning(transformerMirror.toString(), null);
                TypeMirror genericType = Utils.getGenericType(transformerMirror, 0);
                jsonFieldTypeName = TypeName.get(genericType);
            }
        }
        return jsonFieldTypeName;
    }

    private static HashMap<Class, String> classesAndMethods;
    public String getJsonGetterMethodName() {
        if (classesAndMethods == null) {
            classesAndMethods = new HashMap<>();
            classesAndMethods.put(String.class, "getString");
            classesAndMethods.put(Integer.class, "getInt");
            classesAndMethods.put(Boolean.class, "getBoolean");
            classesAndMethods.put(Long.class, "getLong");
            classesAndMethods.put(Double.class, "getDouble");
            classesAndMethods.put(JSONObject.class, "getJSONObject");
            classesAndMethods.put(JSONArray.class, "getJSONArray");
        }

        for (Class clazz: classesAndMethods.keySet()) {
            if (TypeName.get(clazz).equals(getJsonFieldTypeName())) {
                return classesAndMethods.get(clazz);
            }
        }

        return "get";
    }
}
