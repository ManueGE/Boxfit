package com.manuege.boxfit_processor.generators;

import com.manuege.boxfit.converters.BoxfitConverterFactory;
import com.manuege.boxfit.serializers.AbstractBoxfitSerializer;
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

    private String getClassName() {
        return "BoxfitSerializer";
    }

    @Override
    protected TypeSpec getTypeSpec() {
        // Class definition
        TypeSpec.Builder mainSerializerClass = TypeSpec
                .classBuilder(getClassName())
                .addSuperinterface(AbstractBoxfitSerializer.class)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        // Constructor
        mainSerializerClass.addMethod(getConstructor());

        // Box store field
        mainSerializerClass.addField(BoxStore.class, "boxStore", Modifier.PRIVATE);

        // Generic param
        String genericParamName = "T";
        TypeVariableName genericParam = TypeVariableName.get(genericParamName);
        TypeVariableName typeVariableName = TypeVariableName.get(String.format("? extends %s", genericParamName));
        ParameterizedTypeName genericClassParam = ParameterizedTypeName.get(ClassName.get(Class.class), typeVariableName);

        // Define single serializer
        MethodSpec.Builder fromJsonMethod = MethodSpec
                .methodBuilder("fromJson")
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(genericParam)
                .addParameter(genericClassParam, "clazz")
                .addParameter(JSONObject.class, "jsonObject")
                .returns(genericParam);

        // Define many serializer
        ClassName list = ClassName.get("java.util", "List");
        TypeName listOfObjects = ParameterizedTypeName.get(list, genericParam);
        MethodSpec.Builder fromJsonArrayMethod = MethodSpec
                .methodBuilder("fromJson")
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(genericParam)
                .addParameter(genericClassParam, "clazz")
                .addParameter(JSONArray.class, "jsonArray")
                .returns(listOfObjects);

        // Define to json
        MethodSpec.Builder toJsonMethod = MethodSpec
                .methodBuilder("toJson")
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(genericParam)
                .addParameter(genericParam, "object")
                .returns(JSONObject.class);

        // Define to json array
        MethodSpec.Builder toJsonArrayMethod = MethodSpec
                .methodBuilder("toJson")
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(genericParam)
                .addParameter(listOfObjects, "objects")
                .returns(JSONArray.class)
                .beginControlFlow("if (objects == null)")
                .addStatement("return null")
                .endControlFlow()
                .addStatement("JSONArray array = new $T()", JSONArray.class)
                .beginControlFlow("for ($T object: objects)", genericParam)
                .addStatement("array.put(toJson(object))")
                .endControlFlow()
                .addStatement("return array");

        for (ClassInfo classInfo: classes) {
            TypeElement element = classInfo.getTypeElement();
            ClassName serializer = Utils.getSerializer(classInfo.getTypeElement());

            fromJsonMethod.beginControlFlow("if ($T.class.isAssignableFrom(clazz))", element);
            fromJsonMethod.addStatement("return (T) $T.getInstance().fromJson(jsonObject, boxStore)", serializer);
            fromJsonMethod.endControlFlow();

            fromJsonArrayMethod.beginControlFlow("if ($T.class.isAssignableFrom(clazz))", element);
            fromJsonArrayMethod.addStatement("return (List<T>) $T.getInstance().fromJson(jsonArray, boxStore)", serializer);
            fromJsonArrayMethod.endControlFlow();

            toJsonMethod.beginControlFlow("if (object instanceof $T)", element);
            toJsonMethod.addStatement("return $T.getInstance().toJson(($T) object)", serializer, element);
            toJsonMethod.endControlFlow();
        }

        fromJsonMethod.addStatement("return null");
        fromJsonArrayMethod.addStatement("return null");
        toJsonMethod.addStatement("return null");

        mainSerializerClass.addMethod(getConverterFactoryMethod());
        mainSerializerClass.addMethod(fromJsonMethod.build());
        mainSerializerClass.addMethod(fromJsonArrayMethod.build());
        mainSerializerClass.addMethod(toJsonMethod.build());
        mainSerializerClass.addMethod(toJsonArrayMethod.build());

        return mainSerializerClass.build();
    }

    private MethodSpec getConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(BoxStore.class, "boxStore")
                .addStatement("this.$N = $N", "boxStore", "boxStore")
                .build();
    }

    private MethodSpec getConverterFactoryMethod() {
        return MethodSpec.methodBuilder("getConverterFactory")
                .addParameter(BoxStore.class, "boxStore")
                .returns(BoxfitConverterFactory.class)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addStatement("$N boxfitSerializer = new $N(boxStore)", getClassName(), getClassName())
                .addStatement("return new $T(boxfitSerializer)", BoxfitConverterFactory.class)
                .build();
    }

    @Override
    protected String getPackageName() {
        return "com.manuege.boxfit";
    }
}
