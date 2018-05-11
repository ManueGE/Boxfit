package com.manuege.boxfit_processor.generators;

import com.manuege.boxfit_processor.errors.ErrorLogger;
import com.manuege.boxfit_processor.info.ClassInfo;
import com.manuege.boxfit_processor.info.FieldInfo;
import com.manuege.boxfit_processor.info.KotlinUtils;
import com.manuege.boxfit_processor.info.Utils;
import com.squareup.kotlinpoet.FunSpec;
import com.squareup.kotlinpoet.TypeName;
import com.squareup.kotlinpoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;

public class KtBridgeGenerator extends AbstractKtFileGenerator {

    ClassInfo classInfo;

    public KtBridgeGenerator(ProcessingEnvironment environment, ClassInfo classInfo) {
        super(environment);
        this.classInfo = classInfo;
    }

    @Override
    protected com.squareup.kotlinpoet.TypeSpec getTypeSpec() {
        TypeSpec.Builder builder = TypeSpec.objectBuilder(getFilename());

        for (FieldInfo fieldInfo : classInfo.getFields()) {
            builder.addFunction(getGetter(fieldInfo));
            if (fieldInfo.getKind() == FieldInfo.Kind.NORMAL) {
                builder.addFunction(getSetter(fieldInfo));
            }
        }

        return builder.build();
    }

    private FunSpec getGetter(FieldInfo fieldInfo) {
        TypeName className = KotlinUtils.javaToKotlinType(classInfo.getKtTypeName());
        TypeName fieldName = KotlinUtils.javaToKotlinType(fieldInfo.getKtTypeName());
        if (fieldInfo.isNullable()) {
            fieldName = fieldName.asNullable();
        }

        ErrorLogger.putWarning("GETF: " + fieldInfo.getName(), null);
        ErrorLogger.putWarning("GETN: " + fieldName.getNullable(), null);

        String methodName = "get" + Utils.capitalize(fieldInfo.getName());
        FunSpec.Builder builder = new FunSpec.Builder(methodName)
                .addParameter("obj", className)
                .returns(fieldName)
                .addStatement("return obj.%L", fieldInfo.getName());
        return builder.build();
    }

    private FunSpec getSetter(FieldInfo fieldInfo) {
        TypeName className = KotlinUtils.javaToKotlinType(classInfo.getKtTypeName());
        TypeName fieldName = KotlinUtils.javaToKotlinType(fieldInfo.getKtTypeName());
        if (fieldInfo.isNullable()) {
            fieldName = fieldName.asNullable();
        }

        String methodName = "set" + Utils.capitalize(fieldInfo.getName());
        FunSpec.Builder builder = new FunSpec.Builder(methodName)
                .addParameter("obj", className)
                .addParameter("value", fieldName)
                .addStatement("obj.%L = value", fieldInfo.getName());
        return builder.build();
    }


    @Override
    protected String getPackageName() {
        return classInfo.getPackage();
    }

    @Override
    protected String getFilename() {
        return Utils.getBridgeName(classInfo.getTypeElement());
    }
}