package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.utils.SearchUtil;

/**
 * Created by dingsigang on 16-8-18.
 */
public class ContractRvAdapter extends BaseBig4RvAdapter<ContractParams> {

    private Context mContext;
    private CharSequence[] mReviewStates;

    public ContractRvAdapter(Context context) {
        mContext = context;
        mReviewStates = SearchUtil.searchContractReviewStateWebArray(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseBig4RvAdapter.ItemViewHolder){
            ((ItemViewHolder) holder).title.setText(mList.get(position).getTitle());
            ((ItemViewHolder) holder).subTitle.setText(mList.get(position).getAmountPrice());
            ((ItemViewHolder) holder).state.setText(getStatusArray()[SearchUtil.searchInArray(getStatusWebArray(), mList.get(position).getStatus())]);
            ((ItemViewHolder) holder).subRight.setText(mList.get(position).getCustomer().getName());
            String state = mList.get(position).getReviewStatus();
            ((ItemViewHolder) holder).ivStateNoOrWait.setVisibility(View.GONE);
            ((ItemViewHolder) holder).ivStateYes.setVisibility(View.GONE);
            if(TextUtils.equals(state, mReviewStates[0])){
                ((ItemViewHolder) holder).ivStateNoOrWait.setImageResource(R.drawable.wait_approval);
                ((ItemViewHolder) holder).ivStateNoOrWait.setVisibility(View.VISIBLE);
            } else if(TextUtils.equals(state, mReviewStates[1])){
                ((ItemViewHolder) holder).ivStateYes.setImageResource(R.drawable.pass);
                ((ItemViewHolder) holder).ivStateYes.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).ivStateNoOrWait.setImageResource(R.drawable.not_pass);
                ((ItemViewHolder) holder).ivStateNoOrWait.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    int getTypeOf4() {
        return Contants.TYPE_CONTRACT;
    }

    @Override
    CharSequence[] getStatusWebArray() {
        return mContext.getResources().getStringArray(R.array.contract_state_web);
    }

    @Override
    CharSequence[] getStatusArray() {
        return mContext.getResources().getStringArray(R.array.contract_state);
    }

}
