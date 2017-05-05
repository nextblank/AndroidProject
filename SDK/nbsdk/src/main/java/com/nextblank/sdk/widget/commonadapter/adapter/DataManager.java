package com.nextblank.sdk.widget.commonadapter.adapter;

import java.util.Collection;
import java.util.List;

public interface DataManager<T> {
    DataManager<T> clear();

    DataManager<T> add(T t);

    DataManager<T> addAll(Collection<? extends T> list);

    void remove(T t);

    void removeAll(Collection<? extends T> ts);

    List<T> getData();
}
