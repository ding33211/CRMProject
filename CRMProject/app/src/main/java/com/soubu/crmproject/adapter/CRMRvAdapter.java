package com.soubu.crmproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.ConvertUtil;
import com.soubu.crmproject.view.activity.AddFollowHomeActivity;
import com.soubu.crmproject.view.activity.AllFollowActivity;
import com.soubu.crmproject.view.activity.BusinessOpportunityActivity;
import com.soubu.crmproject.view.activity.ChooseEmployeeActivity;
import com.soubu.crmproject.view.activity.ClueActivity;
import com.soubu.crmproject.view.activity.ContractActivity;
import com.soubu.crmproject.view.activity.CustomerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-12.
 */
public class CRMRvAdapter extends RecyclerView.Adapter {

    private static final int TYPE_CUSTOMER_MANAGEMENT = 0x00;//客户管理
    private static final int TYPE_BUSINESS_MANAGEMENT = 0x01;//业务管理
    private static final int TYPE_FOLLOW= 0x02;//跟进
    private static final int TYPE_POPULAR_FUNCTION = 0x03;//常用功能


    private List<CRMRvItem> mCustomerItems;
    private List<CRMRvItem> mBusinessItems;
    private List<CRMRvItem> mFollowItems;
    private List<CRMRvItem> mPopularItems;

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_CUSTOMER_MANAGEMENT;
            case 1:
                return TYPE_BUSINESS_MANAGEMENT;
            case 2:
                return TYPE_FOLLOW;
            case 3:
                return TYPE_POPULAR_FUNCTION;
            default:
                return 0;
        }
    }

    public CRMRvAdapter(Activity activity) {
        int[] labelsOne = new int[]{R.string.clue, R.string.customer, R.string.business_opportunity, R.string.contract};
        int[] iconsOne = new int[]{R.drawable.clue, R.drawable.customer, R.drawable.business_opportunity, R.drawable.contract};
        Intent[] intentsOne = new Intent[]{new Intent(activity, ClueActivity.class), new Intent(activity, CustomerActivity.class),
                new Intent(activity, BusinessOpportunityActivity.class), new Intent(activity, ContractActivity.class)};

//        int[] labelsTwo = new int[]{R.string.company_contacts, R.string.product_management, R.string.clue_high_seas, R.string.customer_high_seas,
//                R.string.weekly_and_monthly_report, R.string.approval};
        int[] labelsTwo = new int[]{R.string.company_contacts, R.string.customer_high_seas, R.string.clue_high_seas};
        int[] iconsTwo = new int[]{R.drawable.sales_management, R.drawable.customer_high_seas, R.drawable.clue_high_seas};
        Intent intent = new Intent(activity, ClueActivity.class);
        intent.putExtra(Contants.EXTRA_FROM, Contants.TYPE_HIGH_SEAS);
        Intent intent1 = new Intent(activity, CustomerActivity.class);
        intent1.putExtra(Contants.EXTRA_FROM, Contants.TYPE_HIGH_SEAS);
        Intent intent2 = new Intent(activity, ChooseEmployeeActivity.class);
        intent2.putExtra(Contants.EXTRA_FROM, Contants.FROM_HOME);
        Intent[] intentsTwo = new Intent[]{intent2, intent1,
                intent};

        int[] labelsFour = new int[]{R.string.follow_record, R.string.follow_plan};
        int[] iconsFour = new int[]{R.drawable.follow_record, R.drawable.follow_plan};
        Intent intent4_1 = new Intent(activity, AllFollowActivity.class);
        intent4_1.putExtra(Contants.EXTRA_TYPE, Contants.TYPE_FOLLOW_RECORD);
        Intent intent4_2 = new Intent(activity, AllFollowActivity.class);
        intent4_2.putExtra(Contants.EXTRA_TYPE, Contants.TYPE_FOLLOW_PLAN);
        Intent[] intentsFour = new Intent[]{intent4_1, intent4_2};


        int[] labelsThree = new int[]{R.string.business_card_scanning, R.string.nearby_customers, R.string.fill_in_follow_up, R.string.new_plan};
        int[] iconsThree = new int[]{R.drawable.business_card_scanning_disable, R.drawable.nearby_customers_disable, R.drawable.fill_in_follow_up, R.drawable.new_reminder};
        Intent intent3_3 = new Intent(activity, AddFollowHomeActivity.class);
        intent3_3.putExtra(Contants.EXTRA_TYPE, Contants.TYPE_FOLLOW_RECORD);
        Intent intent3_4 = new Intent(activity, AddFollowHomeActivity.class);
        intent3_4.putExtra(Contants.EXTRA_TYPE, Contants.TYPE_FOLLOW_PLAN);
        Intent[] intentsThree = new Intent[]{null, null,
                intent3_3, intent3_4};
        mCustomerItems = new ArrayList<>();
        for (int i = 0; i < labelsOne.length; i++) {
            CRMRvItem crm = new CRMRvItem();
            crm.setTitle(labelsOne[i]);
            crm.setIconRes(iconsOne[i]);
            crm.setTarget(intentsOne[i]);
            mCustomerItems.add(crm);
        }
        mBusinessItems = new ArrayList<>();
        for (int i = 0; i < labelsTwo.length; i++) {
            CRMRvItem crm = new CRMRvItem();
            crm.setTitle(labelsTwo[i]);
            crm.setIconRes(iconsTwo[i]);
            crm.setTarget(intentsTwo[i]);
            mBusinessItems.add(crm);
        }
        mFollowItems = new ArrayList<>();
        for (int i = 0; i < labelsFour.length; i++) {
            CRMRvItem crm = new CRMRvItem();
            crm.setTitle(labelsFour[i]);
            crm.setIconRes(iconsFour[i]);
            crm.setTarget(intentsFour[i]);
            mFollowItems.add(crm);
        }

        mPopularItems = new ArrayList<>();
        for (int i = 0; i < labelsThree.length; i++) {
            CRMRvItem crm = new CRMRvItem();
            crm.setTitle(labelsThree[i]);
            crm.setIconRes(iconsThree[i]);
            crm.setTarget(intentsThree[i]);
            mPopularItems.add(crm);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_crm_recyclerview, parent, false);
        GridLayout glContainer = (GridLayout) v.findViewById(R.id.gl_container);
        switch (viewType) {
            case TYPE_CUSTOMER_MANAGEMENT:
                addContainer(glContainer, context, mCustomerItems, viewType);
                break;
            case TYPE_BUSINESS_MANAGEMENT:
                addContainer(glContainer, context, mBusinessItems, viewType);
                break;
            case TYPE_FOLLOW:
                addContainer(glContainer, context, mFollowItems, viewType);
                break;
            case TYPE_POPULAR_FUNCTION:
                addContainer(glContainer, context, mPopularItems, viewType);
                break;
            default:
                break;
        }
        return new ItemViewHolder(v);
    }

    private void addContainer(GridLayout container, final Context context, List<CRMRvItem> list, int type) {
        if (type == TYPE_POPULAR_FUNCTION) {
            container.setColumnCount(4);
            container.setBackgroundResource(android.R.color.white);
            for (final CRMRvItem item : list) {
                View v = LayoutInflater.from(context).inflate(R.layout.gridlayout_item_crm_recyclerview, null, false);
                v.findViewById(R.id.rl_horizontal).setVisibility(View.GONE);
                v.findViewById(R.id.rl_vertical).setVisibility(View.VISIBLE);
                TextView textView = (TextView) v.findViewById(R.id.tv_bottom_title);
                textView.setText(item.getTitle());
                ((ImageView) v.findViewById(R.id.iv_image_vertical)).setImageResource(item.getIconRes());
                android.support.v7.widget.GridLayout.LayoutParams params =
                        new android.support.v7.widget.GridLayout.LayoutParams();
                params.columnSpec = android.support.v7.widget.GridLayout.spec(GridLayout.UNDEFINED, 1f);
                v.setLayoutParams(params);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getTarget() != null) {
//                        ClueParams params1 = new ClueParams();
//                        params1.setId("12345");
//                        params1.setCompanyName("1234");
//                        item.getTarget().putExtra(Contants.EXTRA_ENTITY, params1);
                            context.startActivity(item.getTarget());
                        }
                    }
                });
                container.addView(v);
            }
        } else {
            int i = 0;

            for (final CRMRvItem item : list) {
                View v = LayoutInflater.from(context).inflate(R.layout.gridlayout_item_crm_recyclerview, null);
                ((TextView) v.findViewById(R.id.tv_right_title)).setText(item.getTitle());
                ((ImageView) v.findViewById(R.id.iv_image)).setImageResource(item.getIconRes());
                final android.support.v7.widget.GridLayout.LayoutParams params =
                        new android.support.v7.widget.GridLayout.LayoutParams();
                params.columnSpec = android.support.v7.widget.GridLayout.spec(GridLayout.UNDEFINED, 1f);
                params.width = ConvertUtil.dip2px(context, 90);
                params.setMargins(0, i == 0 || i == 1 ? 0 : 2, 2, 0);//设置边距,顶上两个没有上边距
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getTarget() != null) {
                            context.startActivity(item.getTarget());
                        }
                    }
                });
                container.addView(v, params);
                Log.e("xxxxxxxx", "i    :   " + i + "    params.width   :   " + params.width + "   v.getMeasuredWidth()   :  " + v.getMeasuredWidth());
                i++;
            }
            if(i % 2 != 0){
                View v = LayoutInflater.from(context).inflate(R.layout.gridlayout_item_crm_recyclerview, null);
                final android.support.v7.widget.GridLayout.LayoutParams params =
                        new android.support.v7.widget.GridLayout.LayoutParams();
                params.columnSpec = android.support.v7.widget.GridLayout.spec(GridLayout.UNDEFINED, 1f);
                params.width = ConvertUtil.dip2px(context, 90);
                params.setMargins(0, i == 0 || i == 1 ? 0 : 2, 2, 0);//设置边距,顶上两个没有上边距
                container.addView(v, params);
            }
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).tvTitle.setVisibility(View.VISIBLE);
            switch (position) {
                case 0:
                    ((ItemViewHolder) holder).tvTitle.setText(R.string.customer_management);
                    break;
                case 1:
                    ((ItemViewHolder) holder).tvTitle.setText(R.string.business_management);
                    break;
                case 2:
                    ((ItemViewHolder) holder).tvTitle.setVisibility(View.GONE);
                    break;
                case 3:
                    ((ItemViewHolder) holder).tvTitle.setText(R.string.popular_functions);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    class CRMRvItem {
        int titleRes;
        int iconRes;
        Intent target;

        public int getTitle() {
            return titleRes;
        }

        public void setTitle(int titleRes) {
            this.titleRes = titleRes;
        }

        public int getIconRes() {
            return iconRes;
        }

        public void setIconRes(int iconRes) {
            this.iconRes = iconRes;
        }

        public Intent getTarget() {
            return target;
        }

        public void setTarget(Intent target) {
            this.target = target;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
