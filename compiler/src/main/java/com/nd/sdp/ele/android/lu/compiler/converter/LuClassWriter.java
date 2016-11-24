package com.nd.sdp.ele.android.lu.compiler.converter;

import com.nd.sdp.ele.android.lu.Lu;
import com.nd.sdp.ele.android.lu.compiler.exception.LuException;
import com.nd.sdp.ele.android.lu.mapper.IMapper;
import com.nd.sdp.ele.android.lu.mapper.LuMapper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

/**
 * @author Jackoder
 * @version 2016/11/24
 */
public class LuClassWriter {

    String       mPackageName;
    LuTypeHolder mLuTypeHolder;

    private LuClassWriter(String packageName, LuTypeHolder luTypeHolder) {
        this.mPackageName = packageName;
        this.mLuTypeHolder = luTypeHolder;
    }

    public static LuClassWriter from(String packageName, LuTypeHolder luTypeHolder) {
        return new LuClassWriter(packageName, luTypeHolder);
    }

    public void write(Filer filer) throws LuException, IOException {
        ClassName generatedClassName = ClassName.get(mPackageName,
                mLuTypeHolder.getLuType().getSimpleName().toString() + LuMapper.SUFFIX);
        TypeSpec typeSpec = TypeSpec.classBuilder(generatedClassName)
                .addSuperinterface(IMapper.class)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(convert())
                .build();
        JavaFile javaFile = JavaFile.builder(mPackageName, typeSpec).build();
        javaFile.writeTo(filer);
    }

    private MethodSpec convert() throws LuException {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("map")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(Object.class, "target")
                .returns(Lu.class)
                .addStatement("$T lu = new $T()", Lu.class, Lu.class);
        setParam(builder, mLuTypeHolder.getName(), "setName");
        setParam(builder, mLuTypeHolder.getDescription(), "setDescription");
        setParam(builder, mLuTypeHolder.getCmp(), "setCmp");
        builder.addStatement("return lu");
        return builder.build();
    }

    private void setParam(MethodSpec.Builder builder, Element element, String methodName) {
        builder.addStatement(element.getKind() == ElementKind.FIELD ?
                        "lu.$N((($T)target).$N)" : "lu.$N((($T)target).$N())",
                methodName, mLuTypeHolder.getLuType().asType(),
                element.getSimpleName().toString());
    }

}
