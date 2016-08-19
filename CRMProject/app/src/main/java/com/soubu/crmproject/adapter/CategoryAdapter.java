package com.soubu.crmproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soubu.crmproject.R;

/**
 * 筛选popupWindow的适配器
 * Created by dingsigang on 16-8-16.
 */
public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mStr;
    private int mPos;
    private int mType;
    //有第三级菜单
    private boolean mHaveThreeListView = false;

    public static final int TYPE_PARENT = 0x00;  //第一级菜单
    public static final int TYPE_CHILD = 0x01;  //第二级菜单
    public static final int TYPE_GRANDCHILD = 0x02;  //第三级菜单


    public CategoryAdapter(Context context, String[] mStr, int type) {
        mContext = context;
        this.mStr = mStr;
        this.mType = type;
    }

    @Override
    public int getCount() {
        return mStr.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_popup_filter_listview, parent, false);
            holder.tvParentCategoryName = (TextView) convertView.findViewById(R.id.tv_filter_label);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvParentCategoryName.setText(mStr[position]);

        if (mPos == position) {
            holder.tvParentCategoryName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.filter_selected_color));
        } else {
            holder.tvParentCategoryName.setTextColor(mContext.getResources().getColor(android.R.color.black));
            if (mType == TYPE_PARENT || mHaveThreeListView) {
                convertView.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
            } else {
                convertView.setBackgroundColor(mContext.getResources().getColor(R.color.filter_selected_color));
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tvParentCategoryName;
    }

    public void setSelectedPosition(int pos) {
        this.mPos = pos;
    }

    public int getmPos() {
        return mPos;
    }

    public void setData(String[] str) {
        this.mStr = str;
    }

    public void setHaveThreeListVIew(boolean flag) {
        mHaveThreeListView = flag;
    }
}
