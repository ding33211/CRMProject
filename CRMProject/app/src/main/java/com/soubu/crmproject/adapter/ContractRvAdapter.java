package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.ContractParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-18.
 */
public class ContractRvAdapter extends BaseBig4RvAdapter<ContractParams> {

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseBig4RvAdapter.ItemViewHolder){
            ((ItemViewHolder) holder).title.setText(mList.get(position).getTitle());
            ((ItemViewHolder) holder).subTitle.setText(mList.get(position).getAmountPrice());
            ((ItemViewHolder) holder).state.setText(mList.get(position).getStatus());
            ((ItemViewHolder) holder).subRight.setText(mList.get(position).getCustomer());
//            if(mList.get(position).getStatus() == )
            ((ItemViewHolder) holder).ivState.setImageResource(R.drawable.pass);
        }
    }


    @Override
    int getTypeOf4() {
        return TYPE_CONTRACT;
    }

}
