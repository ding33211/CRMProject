package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.Contants;

/**
 * Created by dingsigang on 16-8-29.
 */
public abstract class BaseBig4RvAdapter<T> extends BaseWithFooterRvAdapter<T> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM || viewType == TYPE_LAST_ONE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_big_4_recyclerview, parent, false);
            switch (getTypeOf4()){
                case Contants.TYPE_CONTRACT:
                    v.findViewById(R.id.iv_approval_state).setVisibility(View.VISIBLE);
                case Contants.TYPE_BUSINESS_OPPORTUNITY:
                    v.findViewById(R.id.ll_sub_right).setVisibility(View.VISIBLE);
                    break;
            }
            if(viewType == TYPE_LAST_ONE){
                v.findViewById(R.id.v_line).setVisibility(View.GONE);
            }
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recyclerview_footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new BaseWithFooterRvAdapter.FooterViewHolder(view);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView subTitle;
        TextView state;
        TextView subRight;
        ImageView ivState;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            subTitle = (TextView) itemView.findViewById(R.id.tv_desc);
            state = (TextView) itemView.findViewById(R.id.tv_state);
            subRight = (TextView) itemView.findViewById(R.id.tv_sub_right);
            ivState = (ImageView) itemView.findViewById(R.id.iv_approval_state);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    abstract int getTypeOf4();

    abstract CharSequence[] getStatusWebArray();

    abstract CharSequence[] getStatusArray();

}
