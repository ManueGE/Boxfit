package com.manuege.boxfit_processor.generators;

import com.manuege.boxfit_processor.info.ClassInfo;
import com.manuege.boxfit_processor.info.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import io.objectbox.BoxStore;

/**
 * Created by Manu on 1/2/18.
 */

public class MainJsonSerializerGenerator extends AbstractFileGenerator {
    List<ClassInfo> classes;

    public MainJsonSerializerGenerator(ProcessingEnvironment environment, List<ClassInfo> classes) {
        super(environment);
        this.classes = classes;
    }

    @Override
    protected TypeSpec getTypeSpec() {
        // Class definition
        TypeSpec.Builder mainSerializerClass = TypeSpec
                .classBuilder("MainJsonSerializer")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        // Constructor
        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(BoxStore.class, "boxStore")
                .addStatement("this.$N = $N", "boxStore", "boxStore")
                .build();
        mainSerializerClass.addMethod(constructor);

        // Box store field
        mainSerializerClass.addField(BoxStore.class, "boxStore", Modifier.PRIVATE);

        // Generic param
        TypeVariableName genericParam = TypeVariableName.get("T");

        // Define single serializer
        MethodSpec.Builder serializeMethod = MethodSpec
                .methodBuilder("serialize")
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(genericParam)
                .addParameter(Class.class, "clazz")
                .addParameter(JSONObject.class, "jsonObject")
                .returns(genericParam);

        // Define many serializer
        ClassName list = ClassName.get("java.util", "List");
        TypeName listOfObjects = ParameterizedTypeName.get(list, genericParam);
        MethodSpec.Builder serializeManyMethod = MethodSpec
                .methodBuilder("serialize")
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(genericParam)
                .addParameter(Class.class, "clazz")
                .addParameter(JSONArray.class, "jsonArray")
                .returns(listOfObjects);

        for (ClassInfo classInfo: classes) {
            TypeElement element = classInfo.getTypeElement();
            ClassName serializer = Utils.getSerializer(environment, classInfo.getTypeElement());

            serializeMethod.beginControlFlow("if ($T.class.isAssignableFrom(clazz))", element);
            serializeMethod.addStatement("return (T) new $T(boxStore).serialize(jsonObject)", serializer);
            serializeMethod.endControlFlow();

            serializeManyMethod.beginControlFlow("if ($T.class.isAssignableFrom(clazz))", element);
            serializeManyMethod.addStatement("return (List<T>) new $T(boxStore).serialize(jsonArray)", serializer);
            serializeManyMethod.endControlFlow();
        }

        serializeMethod.addStatement("return null");
        serializeManyMethod.addStatement("return null");

        mainSerializerClass.addMethod(serializeMethod.build());
        mainSerializerClass.addMethod(serializeManyMethod.build());

        return mainSerializerClass.build();
    }

    @Override
    protected String getPackageName() {
        return "com.manuege.boxfit";
    }
}
