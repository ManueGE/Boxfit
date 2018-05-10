package com.manuege.boxfit_processor.info;

import com.manuege.boxfit_processor.errors.ErrorLogger;
import com.manuege.boxfit_processor.processor.Enviroment;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;

/**
 * Created by Manu on 3/2/18.
 */

public class Utils {
    public static ClassName getSerializer(TypeElement element) {
        return ClassName.get(Enviroment.getEnvironment().getElementUtils().getPackageOf(element).toString(),  getSerializerName(element));
    }

    private static String getSerializerName(TypeElement element) {
        return getHelperClass(element, "Serializer");
    }

    public static ClassName getProxy(TypeElement element) {
        return ClassName.get(Enviroment.getEnvironment().getElementUtils().getPackageOf(element).toString(),  getProxyName(element));
    }

    public static String getProxyName(TypeElement element) {
        return getHelperClass(element, "Proxy");
    }

    private static String getHelperClass(TypeElement element, String suffix) {
        String fullName = element.getQualifiedName().toString();
        String pack = Enviroment.getEnvironment().getElementUtils().getPackageOf(element).toString();
        String elementName = fullName.substring(pack.length() + 1).replace(".", "$");
        return elementName + suffix;
    }

    public static TypeMirror getGenericType(final TypeMirror type, final int index) {
        final TypeMirror[] result = { null };

        type.accept(new SimpleTypeVisitor6<Void, Void>() {
            @Override
            public Void visitDeclared(DeclaredType declaredType, Void v) {
                List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                if (!typeArguments.isEmpty()) {
                    result[0] = typeArguments.get(index);
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

        return result[0];
    }

    private static HashMap<Class, String> classesAndMethods;
    public static String getJsonGetterMethodName(TypeName className) {
        if (classesAndMethods == null) {
            classesAndMethods = new HashMap<>();
            classesAndMethods.put(String.class, "getString");
            classesAndMethods.put(Integer.class, "getInt");
            classesAndMethods.put(Boolean.class, "getBoolean");
            classesAndMethods.put(Long.class, "getLong");
            classesAndMethods.put(Double.class, "getDouble");
            classesAndMethods.put(JSONObject.class, "getJSONObject");
            classesAndMethods.put(JSONArray.class, "getJSONArray");
        }

        for (Class clazz: classesAndMethods.keySet()) {
            if (TypeName.get(clazz).equals(className)) {
                return classesAndMethods.get(clazz);
            }
        }

        return "get";
    }

    public static List<TypeMirror> getInheritanceChain(TypeMirror typeMirror) {
        ArrayList<TypeMirror> arrayList = new ArrayList<>();
        arrayList.addAll(Enviroment.getEnvironment().getTypeUtils().directSupertypes(typeMirror));
        arrayList.add(typeMirror);
        return arrayList;
    }

    public static boolean isList(TypeMirror typeMirror) {
        Types typeUtil = Enviroment.getEnvironment().getTypeUtils();
        for (TypeMirror typeMirror1 : getInheritanceChain(typeMirror)) {
            Element element = typeUtil.asElement(typeMirror1);
            if (element instanceof TypeElement) {
                if (((TypeElement) element).getQualifiedName().toString().startsWith(TypeName.get(List.class).toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static TypeElement getElementFromTypeName(TypeName typeName) {
        return Enviroment.getEnvironment().getElementUtils().getTypeElement(typeName.toString());
    }

    public static TypeMirror getTypeMirrorFromTypeName(TypeName typeName) {
        return getElementFromTypeName(typeName).asType();
    }

    public static void ensureTypeNameHasEmptyInitializer(TypeName typeName) {
        if (typeName == null) {
            return;
        }

        ArrayList<ExecutableElement> constructors = new ArrayList<>();
        TypeElement element = getElementFromTypeName(typeName);
        for (Element e: element.getEnclosedElements()) {
            if (e.getKind() == ElementKind.CONSTRUCTOR) {
                constructors.add((ExecutableElement) e);
            }
        }

        if (constructors.size() == 0) {
            return;
        }

        for (ExecutableElement c : constructors) {
            if (c.getParameters().size() == 0 && c.getModifiers().contains(Modifier.PUBLIC)) {
                return;
            }
        }

        ErrorLogger.putError(String.format("%s must have a public constructor with no arguments", element.getSimpleName()), element);

    }

    public static String capitalize(String string) {
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }
}
