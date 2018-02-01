package com.manuege.boxfit_processor.processor;

import com.manuege.boxfit_processor.generators.HelloWorldFileGenerator;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Created by Manu on 1/2/18.
 */

@SupportedAnnotationTypes({"com.manuege.boxfit.annotations.JsonSerializable"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BoxfitProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        new HelloWorldFileGenerator(processingEnv).generateFile();
        return false;
    }
}
