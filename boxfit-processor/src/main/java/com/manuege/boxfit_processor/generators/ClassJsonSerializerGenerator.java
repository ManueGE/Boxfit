package com.manuege.boxfit_processor.generators;

import com.manuege.boxfit_processor.info.ClassInfo;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;

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
        return null;
    }

    @Override
    protected String getPackageName() {
        return null;
    }
}
