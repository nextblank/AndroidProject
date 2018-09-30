package com.nextblank.android.sdk.widget.commonadapter.holder;

import android.view.View;

public abstract class CommonHolder<T> {

    private View mItemView;

    public abstract void bindData(T t);

    public void setItemView(View view) {
        mItemView = view;
    }

    public View getItemView() {
        return mItemView;
    }

    public void initSingleton() {
    }

    public void initView() {
    }
}
