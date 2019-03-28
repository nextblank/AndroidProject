package com.pn.android.sdk.widget.commonadapter.holder;

import com.pn.android.sdk.widget.commonadapter.adapter.CommonAdapter;

public abstract class ComplexHolder<T> extends CommonHolder<T> {

    /**
     * @param t        数据
     * @param position 位置
     * @param adapter  所在适配器
     */
    public abstract void bindData(T t, int position, CommonAdapter<T, ? extends CommonHolder<T>> adapter);

    @Override
    public void bindData(T t) {
        // ignore
    }
}
