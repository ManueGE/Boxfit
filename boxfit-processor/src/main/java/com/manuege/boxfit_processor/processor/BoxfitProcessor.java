package com.manuege.boxfit_processor.processor;

import com.manuege.boxfit.annotations.BoxfitClass;
import com.manuege.boxfit_processor.errors.ErrorLogger;
import com.manuege.boxfit_processor.errors.InvalidElementException;
import com.manuege.boxfit_processor.generators.ClassJsonSerializerGenerator;
import com.manuege.boxfit_processor.generators.MainJsonSerializerGenerator;
import com.manuege.boxfit_processor.info.ClassInfo;

import java.util.ArrayList;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Created by Manu on 1/2/18.
 */

@SupportedAnnotationTypes({"com.manuege.boxfit.annotations.BoxfitClass"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BoxfitProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Enviroment.setEnvironment(processingEnv);

        ArrayList<ClassInfo> classesInfo = new ArrayList<>();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BoxfitClass.class)) {
            if (element.getKind() != ElementKind.CLASS || !(element instanceof TypeElement)) {
                ErrorLogger.putError("JsonSerializer must be applied to classes", element);
                continue;
            }

            try {
                ClassInfo classInfo = ClassInfo.newInstance((TypeElement) element);
                classesInfo.add(classInfo);
            } catch (InvalidElementException e) {
                e.putError();
                return false;
            }
        }

        for (ClassInfo classInfo : classesInfo) {
            ClassJsonSerializerGenerator generator = new ClassJsonSerializerGenerator(processingEnv, classInfo);
            generator.generateFile();
        }

        MainJsonSerializerGenerator generator = new MainJsonSerializerGenerator(processingEnv, classesInfo);
        generator.generateFile();

        return false;
    }
}
