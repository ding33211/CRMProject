package com.soubu.crmproject.delegate;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.SpecRvAdapter;
import com.soubu.crmproject.base.greendao.Staff;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.utils.DrawableUtils;

import java.util.List;

/**
 * Created by dingsigang on 16-8-24.
 */
public class SpecActivityDelegate extends BaseRecyclerViewActivityDelegate {
    private SpecRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new SpecRvAdapter();
        setListAdapter(mAdapter);
    }

    public void setData(List<AddItem> list){
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    public void addHeaderView(View view){
        LinearLayout contentAll = get(R.id.ll_recycler_container);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        contentAll.addView(view, 0);
    }

    public void setStaffInfo(Staff staff){
        TextView tvCircleName = get(R.id.tv_circle_name);
        tvCircleName.setBackgroundResource(DrawableUtils.getAvatarColor(staff.getUsername()));
        tvCircleName.setText(DrawableUtils.getMiniName(staff.getUsername()));
        ((TextView)get(R.id.tv_name)).setText(staff.getUsername());
    }
}
