package com.manuege.boxfit_processor.generators;

import com.squareup.kotlinpoet.FileSpec;
import com.squareup.kotlinpoet.TypeSpec;

import java.io.File;
import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * Created by Manu on 1/2/18.
 */

public abstract class AbstractKtFileGenerator {
    public static final String KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated";

    ProcessingEnvironment environment;

    public AbstractKtFileGenerator(ProcessingEnvironment environment) {
        this.environment = environment;
    }

    public void generateFile() {
        FileSpec file = FileSpec.builder(getPackageName(), getFilename())
                .addType(getTypeSpec())
                .build();

        try {
            String kaptKotlinGeneratedDir = environment.getOptions().get(KAPT_KOTLIN_GENERATED_OPTION_NAME);
            file.writeTo(new File(kaptKotlinGeneratedDir, getFilename() + ".kt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract TypeSpec getTypeSpec();
    protected abstract String getFilename();
    protected abstract String getPackageName();
}
