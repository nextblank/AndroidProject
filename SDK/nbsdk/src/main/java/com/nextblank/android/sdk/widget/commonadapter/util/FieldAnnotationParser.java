package com.nextblank.android.sdk.widget.commonadapter.util;

import android.app.Activity;

import com.nextblank.android.sdk.widget.commonadapter.ViewId;

import java.lang.reflect.Field;

public class FieldAnnotationParser {
    /**
     * Parse {@link ViewId} annotation and try to assign the view with that id to the annotated field.
     * It will throw a {@link ClassCastException} if the field and the view with the given ID have different types.
     *
     * @param object object where the annotation is.
     * @param view   parent view that contains a view with the viewId given in the annotation.
     */
    public static void setViewFields(final Object object, final android.view.View view) {
        setViewFields(object, new ViewFinder() {
            @Override
            public android.view.View findViewById(int viewId) {
                return view.findViewById(viewId);
            }
        });
    }

    /**
     * Parse {@link ViewId} annotation and try to assign the view with that id to the annotated field.
     * It will throw a {@link ClassCastException} if the field and the view with the given ID have different types.
     *
     * @param object   object where the annotation is.
     * @param activity activity that contains a view with the viewId given in the annotation.
     */
    public static void setViewFields(final Object object, final Activity activity) {
        setViewFields(object, new ViewFinder() {
            @Override
            public android.view.View findViewById(int viewId) {
                return activity.findViewById(viewId);
            }
        });
    }

    /**
     * Parse {@link ViewId} annotation and try to assign the view with that id to the annotated field.
     * It will throw a {@link ClassCastException} if the field and the view with the given ID have different types.
     *
     * @param object     object where the annotation is.
     * @param viewFinder callback that provides a way with finding the view by the viewID given in the annotation.
     */
    private static void setViewFields(final Object object, final ViewFinder viewFinder) {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ViewId.class)) {
                field.setAccessible(true);
                ViewId viewIdAnnotation = field.getAnnotation(ViewId.class);
                try {
                    field.set(object, field.getType().cast(viewFinder.findViewById(viewIdAnnotation.value())));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private interface ViewFinder {
        android.view.View findViewById(int viewId);
    }
}
