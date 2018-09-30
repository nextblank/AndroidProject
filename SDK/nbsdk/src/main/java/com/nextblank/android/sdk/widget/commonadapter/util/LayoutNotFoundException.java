package com.nextblank.android.sdk.widget.commonadapter.util;

public class LayoutNotFoundException extends IllegalStateException {

    public LayoutNotFoundException() {
        super("please provide Layout Id in your viewHolder with @LayoutId.");
    }
}