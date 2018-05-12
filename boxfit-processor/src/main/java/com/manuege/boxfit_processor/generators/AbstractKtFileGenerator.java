package com.manuege.boxfit_processor.generators;

import com.squareup.kotlinpoet.FileSpec;
import com.squareup.kotlinpoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

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

            FileObject kotlinFileObject = environment.getFiler().createResource(StandardLocation.SOURCE_OUTPUT,
                    getPackageName(), getFilename() + ".kt");
            Writer writer = kotlinFileObject.openWriter();
            writer.write(file.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract TypeSpec getTypeSpec();
    protected abstract String getFilename();
    protected abstract String getPackageName();
}
