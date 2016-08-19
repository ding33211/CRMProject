package com.soubu.crmproject.adapter;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dingsigang on 16-8-19.
 */
public class AddSomethingRvAdapter extends RecyclerView.Adapter {

    public final int TYPE_LABEL = 0x00;
    public final int TYPE_OTHER = 0x01;
    public final int TYPE_ITEM_REQUIRED_FILL = 0x02;  //必填
    public final int TYPE_ITEM_REQUIRED_CHOOSE = 0x03;  // 必选
    public final int TYPE_ITEM_CAN_FILL = 0x04;  //可填
    public final int TYPE_ITEM_CAN_CHOOSE = 0x05;  //可选
    public final int TYPE_ITEM_CAN_LOCATE = 0x06;  //可定位
    public final int TYPE_ITEM_UNABLE = 0x07;  //不可点击


    private List<List<Pair<String, Integer>>> mList;

    public AddSomethingRvAdapter(List<List<Pair<String, Integer>>> list){
         mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
