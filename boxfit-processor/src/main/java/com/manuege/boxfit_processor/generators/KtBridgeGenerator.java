package com.manuege.boxfit_processor.generators;

import com.manuege.boxfit_processor.info.ClassInfo;
import com.manuege.boxfit_processor.info.FieldInfo;
import com.manuege.boxfit_processor.info.KotlinUtils;
import com.manuege.boxfit_processor.info.Utils;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;
import com.squareup.kotlinpoet.ClassName;
import com.squareup.kotlinpoet.FunSpec;
import com.squareup.kotlinpoet.TypeName;
import com.squareup.kotlinpoet.TypeSpec;

import java.util.ArrayList;

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
            if (!fieldInfo.getKind().isRelationship()) {
                builder.addFunction(getSetter(fieldInfo));
            }
        }

        return builder.build();
    }

    private FunSpec getGetter(FieldInfo fieldInfo) {
        TypeName className = KotlinUtils.javaToKotlinType(classInfo.getKtTypeName());
        TypeName fieldName = getKotlinTypeName(fieldInfo);

        if (fieldInfo.isNullable()) {
            fieldName = fieldName.asNullable();
        }

        String methodName = "get" + Utils.capitalize(fieldInfo.getName());
        FunSpec.Builder builder = new FunSpec.Builder(methodName)
                .addParameter("obj", className)
                .returns(fieldName)
                .addStatement("return obj.%L", fieldInfo.getName());
        return builder.build();
    }

    private FunSpec getSetter(FieldInfo fieldInfo) {
        TypeName className = KotlinUtils.javaToKotlinType(classInfo.getKtTypeName());
        TypeName fieldName = getKotlinTypeName(fieldInfo);

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

    private TypeName getKotlinTypeName(FieldInfo fieldInfo) {


        TypeName typeName = fieldInfo.getKtTypeName();

        if (fieldInfo.getTypeName() instanceof ParameterizedTypeName) {
            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) fieldInfo.getTypeName();
            com.squareup.javapoet.TypeName argumentTypeName = parameterizedTypeName.typeArguments.get(0);
            if (argumentTypeName instanceof WildcardTypeName) {
                WildcardTypeName wildcardTypeName = (WildcardTypeName) argumentTypeName;
                ArrayList<com.squareup.javapoet.TypeName> typeNames = new ArrayList<>(wildcardTypeName.upperBounds);
                typeNames.addAll(wildcardTypeName.lowerBounds);

                for (com.squareup.javapoet.TypeName t : typeNames) {
                    TypeVariableName typeVariableName = TypeVariableName.get(t.toString());
                    com.squareup.javapoet.TypeName concreteTypeName = classInfo.getGenericParamsMap().get(typeVariableName);

                    if (concreteTypeName != null) {
                        TypeName ktRawType = KotlinUtils.javaToKotlinType(Utils.getKtTypeName(parameterizedTypeName.rawType));
                        while (ktRawType instanceof com.squareup.kotlinpoet.ParameterizedTypeName) {
                            ktRawType = ((com.squareup.kotlinpoet.ParameterizedTypeName) ktRawType).getRawType();
                        }
                        ClassName rawClassName = ClassName.bestGuess(ktRawType.toString());

                        TypeName ktConcreteTypeName = KotlinUtils.javaToKotlinType(Utils.getKtTypeName(concreteTypeName));
                        typeName = com.squareup.kotlinpoet.ParameterizedTypeName.get(rawClassName, ktConcreteTypeName);
                        break;
                    }
                }
            }
        }

        return KotlinUtils.javaToKotlinType(typeName);
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