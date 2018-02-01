package com.manuege.boxfit_processor.generators;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

/**
 * Created by Manu on 1/2/18.
 */

public class HelloWorldFileGenerator extends AbstractFileGenerator {
    public HelloWorldFileGenerator(ProcessingEnvironment environment) {
        super(environment);
    }

    @Override
    protected TypeSpec getTypeSpec() {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, Boxfit!")
                .build();

        return TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();
    }

    @Override
    protected String getPackageName() {
        return "com.manuege.helloworld";
    }
}
