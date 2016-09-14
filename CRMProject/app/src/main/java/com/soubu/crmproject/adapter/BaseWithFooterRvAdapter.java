package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soubu.crmproject.model.ClueParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-17.
 */
public abstract class BaseWithFooterRvAdapter<T> extends RecyclerView.Adapter {
    public static final int TYPE_ITEM = 0x00;//内容
    public static final int TYPE_FOOTER = 0x01;//加载更多
    public static final int TYPE_LAST_ONE = 0x02;//最后一个

    //默认一页显式10项数据
     final int PAGE_SIZE = 10;
    /**
     * 是否显示加载更多视图
     */
    boolean mShowFooter = false;

    public void setShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    List<T> mList;
    OnItemClickListener mListener;
    OnItemClickListener mRushListener;

    public BaseWithFooterRvAdapter(){
        mList = new ArrayList<T>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            if(mShowFooter){
                return TYPE_FOOTER;
            } else {
                return TYPE_LAST_ONE;
            }
        } else {
            return TYPE_ITEM;
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setOnRushClickListener(OnItemClickListener listener){
        mRushListener = listener;
    }

    public T getParams(int pos){
        return mList.get(pos);
    }

    public void setData(List<T> list, boolean isRefresh) {
        if(isRefresh){
            if (!mList.isEmpty()) {
                mList.clear();
            }
        }
        if(list.size() <= PAGE_SIZE){
            setShowFooter(false);
        } else {
            setShowFooter(true);
        }
        mList.addAll(list);
    }

//    public void clear(){
//        mList.clear();
//    }

    @Override
    public int getItemCount() {
        return isShowFooter() ? mList.size() + 1 : mList.size();
    }

}
