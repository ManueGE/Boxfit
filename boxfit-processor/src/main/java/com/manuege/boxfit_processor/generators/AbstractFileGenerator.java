package com.manuege.boxfit_processor.generators;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * Created by Manu on 1/2/18.
 */

public abstract class AbstractFileGenerator {
    ProcessingEnvironment environment;

    public AbstractFileGenerator(ProcessingEnvironment environment) {
        this.environment = environment;
    }

    public void generateFile() {
        JavaFile javaFile = JavaFile.builder(getPackageName(), getTypeSpec())
                .build();

        try {
            javaFile.writeTo(environment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract TypeSpec getTypeSpec();
    protected abstract String getPackageName();
}
