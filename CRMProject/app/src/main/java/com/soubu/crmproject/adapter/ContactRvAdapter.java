package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;

import java.util.List;

/**
 * Created by dingsigang on 16-9-8.
 */
public class ContactRvAdapter extends BaseWithFooterRvAdapter<ContactParams> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM || viewType == TYPE_LAST_ONE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_contact_recyclerview, parent, false);
            if(viewType == TYPE_LAST_ONE){
                v.findViewById(R.id.v_line).setVisibility(View.INVISIBLE);
            }
            return new ItemViewHolder(v);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recyclerview_footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new BaseWithFooterRvAdapter.FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         if(holder instanceof ItemViewHolder){
             ItemViewHolder holder1 = (ItemViewHolder)holder;
             holder1.tvName.setText(mList.get(position).getName());
             String post = mList.get(position).getPosition();
             if(TextUtils.isEmpty(post)){
                 holder1.tvPosition.setVisibility(View.GONE);
             } else {
                 holder1.tvPosition.setVisibility(View.VISIBLE);
                 holder1.tvPosition.setText(post);
             }
             String phone = mList.get(position).getPhone();
             String mobile = mList.get(position).getMobile();
             if(TextUtils.isEmpty(phone) && TextUtils.isEmpty(mobile)){
                 holder1.vPhone.setVisibility(View.GONE);
                 holder1.vMessage.setVisibility(View.GONE);
             } else if(TextUtils.isEmpty(mobile)){
                 holder1.vMessage.setVisibility(View.GONE);
             } else {
                 holder1.vPhone.setVisibility(View.VISIBLE);
                 holder1.vMessage.setVisibility(View.VISIBLE);
             }
         }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName;
        TextView tvPosition;
        View vPhone;
        View vMessage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPosition = (TextView) itemView.findViewById(R.id.tv_position);
            vPhone = itemView.findViewById(R.id.iv_mobile_contact);
            vMessage = itemView.findViewById(R.id.iv_message_contact);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
