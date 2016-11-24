package com.nd.sdp.ele.android.lu.compiler;

import com.google.auto.service.AutoService;
import com.nd.sdp.ele.android.lu.ann.Cmp;
import com.nd.sdp.ele.android.lu.ann.Description;
import com.nd.sdp.ele.android.lu.ann.Lu;
import com.nd.sdp.ele.android.lu.ann.Name;
import com.nd.sdp.ele.android.lu.compiler.converter.LuClassWriter;
import com.nd.sdp.ele.android.lu.compiler.converter.LuTypeHolder;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * @author Jackoder
 * @version 2016/11/24
 */
@AutoService(Processor.class)
public class LuProcessor extends AbstractProcessor {

    private Types    mTypeUtils;
    private Elements mElementUtils;
    private Filer    mFiler;
    private Messager mErrorMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mErrorMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportAnnotationTypes = new HashSet<>();
        supportAnnotationTypes.add(Lu.class.getCanonicalName());
        supportAnnotationTypes.add(Name.class.getCanonicalName());
        supportAnnotationTypes.add(Description.class.getCanonicalName());
        supportAnnotationTypes.add(Cmp.class.getCanonicalName());
        return supportAnnotationTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Lu.class)) {
                if (annotatedElement.getKind() == ElementKind.CLASS) {
                    TypeElement annotatedClass = (TypeElement) annotatedElement;
                    String packageName = mElementUtils.getPackageOf(annotatedClass).getQualifiedName().toString();
                    LuTypeHolder holder = LuTypeHolder.from(annotatedClass).logger(mErrorMessager).build();
                    LuClassWriter writer = LuClassWriter.from(packageName, holder);
                    writer.write(mFiler);
                    return true;
                }
            }
        } catch (Exception ex) {
            error("Exception while processing Lu classes. Message:\n%s", ex.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
//        return super.getSupportedSourceVersion();
    }

    private void error(String message, Object... args) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(message, args));
    }
}
