package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dingsigang on 16-8-17.
 */
public abstract class BaseWithFooterRvAdapter extends RecyclerView.Adapter {
    public static final int TYPE_ITEM = 0x00;//内容
    public static final int TYPE_FOOTER = 0x01;//加载更多
    /**
     * 是否显示加载更多视图
     */
    boolean mShowFooter = true;

    public void setShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }
}
