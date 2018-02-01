package com.manuege.boxfit_processor.generators;

import com.manuege.boxfit_processor.info.ClassInfo;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;

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
        return null;
    }

    @Override
    protected String getPackageName() {
        return null;
    }
}
