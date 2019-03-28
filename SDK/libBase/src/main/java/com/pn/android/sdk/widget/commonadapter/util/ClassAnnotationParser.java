package com.pn.android.sdk.widget.commonadapter.util;

import com.pn.android.sdk.widget.commonadapter.LayoutId;

import java.lang.annotation.Annotation;

public class ClassAnnotationParser {

    /**
     * Parse {@link LayoutId} annotation form a class
     *
     * @param myClass class that is annotated
     * @return the integer value with the annotation
     */
    public static Integer getLayoutId(Class myClass) {
        Annotation annotation = myClass.getAnnotation(LayoutId.class);

        if (annotation instanceof LayoutId) {
            LayoutId layoutIdAnnotation = (LayoutId) annotation;
            return layoutIdAnnotation.value();
        }

        return null;
    }
}
