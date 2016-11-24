package com.nd.sdp.ele.android.lu.compiler.converter;

import com.nd.sdp.ele.android.lu.ann.Cmp;
import com.nd.sdp.ele.android.lu.ann.Description;
import com.nd.sdp.ele.android.lu.ann.Name;
import com.nd.sdp.ele.android.lu.compiler.exception.LuException;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * @author Jackoder
 * @version 2016/11/24
 */
public class LuTypeHolder {

    TypeElement mEleLuType;
    Element mEleName;
    Element mEleDescription;
    Element mEleCmp;

    Messager mMessager;

    private LuTypeHolder(TypeElement eleLuType) {
        mEleLuType = eleLuType;
    }

    public static LuTypeHolder from(TypeElement typeElement) {
        return new LuTypeHolder(typeElement);
    }

    public LuTypeHolder logger(Messager messager) {
        mMessager = messager;
        return this;
    }

    public LuTypeHolder build() throws LuException {
        List<? extends Element> enclosedElements = mEleLuType.getEnclosedElements();
        mEleName = findElementByAnnotation(enclosedElements, Name.class);
        mEleDescription = findElementByAnnotation(enclosedElements, Description.class);
        mEleCmp = findElementByAnnotation(enclosedElements, Cmp.class);
        return this;
    }

    public TypeElement getLuType() {
        return mEleLuType;
    }

    public Element getName() {
        return mEleName;
    }

    public Element getDescription() {
        return mEleDescription;
    }

    public Element getCmp() {
        return mEleCmp;
    }

    private Element findElementByAnnotation(List<? extends Element> enclosedElements, Class<? extends Annotation> annClazz) throws LuException {
        for (Element element : enclosedElements) {
            Annotation annotation = element.getAnnotation(annClazz);
            if (annotation != null) {
                //TODO valid type
                return element;
            }
        }
        throw new LuException(mEleLuType.getQualifiedName().toString() + " missing annotation @" + annClazz.getSimpleName());
    }

}
