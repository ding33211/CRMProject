package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.Contants;

import static com.soubu.crmproject.R.id.v_line;

/**
 * Created by dingsigang on 16-8-29.
 */
public abstract class BaseBig4RvAdapter<T> extends BaseWithFooterRvAdapter<T> {
    //是否是公海
    boolean mIfHighSeas = false;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM || viewType == TYPE_LAST_ONE) {
            //2.0界面，客户列表item调整
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_big_4_recyclerview, parent, false);
            switch (getTypeOf4()) {
//                case Contants.TYPE_CONTRACT:
//                    v.findViewById(R.id.iv_approval_yes).setVisibility(View.VISIBLE);
//                    v.findViewById(R.id.iv_approval_no_or_wait).setVisibility(View.VISIBLE);
                case Contants.TYPE_BUSINESS_OPPORTUNITY:
                case Contants.TYPE_CUSTOMER:
                    v.findViewById(R.id.ll_sub_right).setVisibility(View.VISIBLE);
                    v.findViewById(R.id.ll_customer_bottom).setVisibility(View.VISIBLE);
                    v.findViewById(v_line).setVisibility(View.GONE);
                    v.findViewById(R.id.iv_phone).setVisibility(View.VISIBLE);
                    v.findViewById(R.id.tv_state).setVisibility(View.GONE);
                    break;
            }
            if (viewType == TYPE_LAST_ONE) {
                v.findViewById(v_line).setVisibility(View.GONE);
            }
            if (mIfHighSeas) {
                v.findViewById(R.id.rl_rush_in).setVisibility(View.VISIBLE);
                v.findViewById(R.id.tv_state).setVisibility(View.GONE);
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
        View subLine;
        ImageView ivStateYes;
        ImageView ivStateNoOrWait;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            subTitle = (TextView) itemView.findViewById(R.id.tv_desc);
            state = (TextView) itemView.findViewById(R.id.tv_state);
            subRight = (TextView) itemView.findViewById(R.id.tv_sub_right);
            ivStateYes = (ImageView) itemView.findViewById(R.id.iv_approval_yes);
            ivStateNoOrWait = (ImageView) itemView.findViewById(R.id.iv_approval_no_or_wait);
            subLine = itemView.findViewById(R.id.v_sub_line);
            itemView.findViewById(R.id.rl_rush_in).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.rl_rush_in) {
                mRushListener.onItemClick(v, getLayoutPosition());
                return;
            }
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    abstract int getTypeOf4();

    abstract CharSequence[] getStatusWebArray();

    abstract CharSequence[] getStatusArray();

}
