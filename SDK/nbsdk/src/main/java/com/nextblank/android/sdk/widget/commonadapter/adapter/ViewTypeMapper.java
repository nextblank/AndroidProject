package com.nextblank.android.sdk.widget.commonadapter.adapter;

import com.nextblank.android.sdk.widget.commonadapter.holder.CommonHolder;

/**
 * 描述实体 => CommonHolder 的关系映射，来简化 ViewType 逻辑
 */
public interface ViewTypeMapper {

    /**
     * 提供从实体或者 position 到 viewType 的映射
     *
     * @param item     实体
     * @param position item 所在的位置
     * @return 该位置所使用的 viewHolder（建议在 Holder 中定义 public 常量）
     */
    Class<? extends CommonHolder<? extends ViewTypeItem>> getViewType(ViewTypeItem item, int position);
}
