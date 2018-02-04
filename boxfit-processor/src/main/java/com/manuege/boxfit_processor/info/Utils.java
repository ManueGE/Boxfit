package com.manuege.boxfit_processor.info;

import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.ClassName;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor6;

/**
 * Created by Manu on 3/2/18.
 */

public class Utils {
    public static ClassName getSerializer(ProcessingEnvironment environment, TypeElement element) {
        return ClassName.get(environment.getElementUtils().getPackageOf(element).toString(),  getSerializer(element));
    }

    private static String getSerializer(TypeElement element) {
        String fullName = element.getQualifiedName().toString();
        String pack = Enviroment.getEnvironment().getElementUtils().getPackageOf(element).toString();
        String elementName = fullName.substring(pack.length() + 1).replace(".", "$");
        return elementName + "Serializer";
    }

    public static TypeMirror getGenericType(final TypeMirror type, int index) {
        final TypeMirror[] result = { null };

        type.accept(new SimpleTypeVisitor6<Void, Void>() {
            @Override
            public Void visitDeclared(DeclaredType declaredType, Void v) {
                List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                if (!typeArguments.isEmpty()) {
                    result[0] = typeArguments.get(0);
                }
                return null;
            }
            @Override
            public Void visitPrimitive(PrimitiveType primitiveType, Void v) {
                return null;
            }
            @Override
            public Void visitArray(ArrayType arrayType, Void v) {
                return null;
            }
            @Override
            public Void visitTypeVariable(TypeVariable typeVariable, Void v) {
                return null;
            }
            @Override
            public Void visitError(ErrorType errorType, Void v) {
                return null;
            }
            @Override
            protected Void defaultAction(TypeMirror typeMirror, Void v) {
                throw new UnsupportedOperationException();
            }
        }, null);

        return result[index];
    }
}
