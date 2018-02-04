package com.manuege.boxfit_processor.generators;

import com.manuege.boxfit.serializers.AbstractSerializer;
import com.manuege.boxfit_processor.info.ClassInfo;
import com.manuege.boxfit_processor.info.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

import io.objectbox.BoxStore;

/**
 * Created by Manu on 1/2/18.
 */

public class ClassJsonSerializerGenerator extends AbstractFileGenerator {
    ClassInfo classInfo;

    public ClassJsonSerializerGenerator(ProcessingEnvironment environment, ClassInfo classInfo) {
        super(environment);
        this.classInfo = classInfo;
    }

    @Override
    protected TypeSpec getTypeSpec() {
        ClassName abstractSerializerClass = ClassName.get(AbstractSerializer.class);
        TypeName entity = TypeName.get(classInfo.getTypeElement().asType());
        TypeName primaryKey;
        if (classInfo.getPrimaryKey() != null) {
            primaryKey = classInfo.getPrimaryKey().getTypeName();
        } else {
            primaryKey = TypeName.get(Void.class);
        }

        ParameterizedTypeName superclass = ParameterizedTypeName.get(abstractSerializerClass, entity, primaryKey);

        // Class definition
        TypeSpec.Builder serializerClass = TypeSpec
                .classBuilder(Utils.getSerializer(environment, classInfo.getTypeElement()))
                .superclass(superclass)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        // Box store field
        serializerClass.addField(BoxStore.class, "boxStore", Modifier.PRIVATE);

        // Methods
        serializerClass.addMethod(getConstructor());

        return serializerClass.build();
    }

    private MethodSpec getConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(BoxStore.class, "boxStore")
                .addStatement("super(boxStore)")
                .build();
    }

    @Override
    protected String getPackageName() {
        return environment.getElementUtils().getPackageOf(classInfo.getTypeElement()).toString();
    }
}
