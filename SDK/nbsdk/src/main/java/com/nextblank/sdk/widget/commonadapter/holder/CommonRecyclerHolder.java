package com.nextblank.sdk.widget.commonadapter.holder;

import android.support.v7.widget.RecyclerView;

public final class CommonRecyclerHolder<T> extends RecyclerView.ViewHolder {

    private CommonHolder<T> mCommonHolder;

    public CommonRecyclerHolder(CommonHolder<T> commonHolder) {
        super(commonHolder.getItemView());
        mCommonHolder = commonHolder;
    }

    public CommonHolder<T> getCommonHolder() {
        return mCommonHolder;
    }
}
