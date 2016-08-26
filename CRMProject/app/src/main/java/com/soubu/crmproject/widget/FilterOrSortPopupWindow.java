package com.soubu.crmproject.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.CategoryAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * 筛选和排序弹窗
 * Created by dingsigang on 16-8-16.
 */
public class FilterOrSortPopupWindow extends PopupWindow {

    //是筛选还是排序
    private boolean mIsFilter = true;

    private SelectCategory mSelectCategory;

    private String[][] mChildrenStrings;
    private String[][][] mGrandChildrenStrings;

    private CustomHorizontalScrollView mHsvContainer;
    private GridLayout mGlContainer;
    private int mWindowWidth;
    android.support.v7.widget.GridLayout.LayoutParams mLayoutParams;

    private ListView mLvParentCategory = null;
    private ListView mLvChildrenCategory = null;
    private CategoryAdapter mCategoryAdapter = null;
    private CategoryAdapter mChildrenCategoryAdapter = null;
    private Map<Integer, Integer> mMap;
    private int mParentPos;

    /**
     * @param parentStrings   字类别数据
     * @param childrenStrings 字类别二位数组
     * @param activity
     * @param selectCategory  回调接口注入
     */
    public FilterOrSortPopupWindow(String[] parentStrings, String[][] childrenStrings, String[][][] grandChildStrings, final Activity activity, SelectCategory selectCategory) {
        this.mSelectCategory = selectCategory;
        this.mChildrenStrings = childrenStrings;
        this.mGrandChildrenStrings = grandChildStrings;

        if (parentStrings == null || parentStrings.length == 0) {
            return;
        }
        if (childrenStrings == null || childrenStrings.length == 0) {
            //是排序
            mIsFilter = false;
        }

        View contentView = LayoutInflater.from(activity).inflate(R.layout.popup_window_filter, null);
        mGlContainer = (GridLayout) contentView.findViewById(R.id.gl_container);
        mHsvContainer = (CustomHorizontalScrollView) contentView.findViewById(R.id.hsv_container);
        View llAction = contentView.findViewById(R.id.ll_action);
        ListView lvSort = (ListView) contentView.findViewById(R.id.lv_sort);
        mCategoryAdapter = new CategoryAdapter(activity, parentStrings, CategoryAdapter.TYPE_PARENT);
        /* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */
        /**
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
        contentView.findViewById(R.id.ll_translucent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.setContentView(contentView);
        mWindowWidth = dm.widthPixels;
        this.setWidth(mWindowWidth);
        this.setHeight(dm.heightPixels);
        if (mIsFilter) {
            //父类别适配器
            mLvParentCategory = new ListView(activity);
            mLvParentCategory.setDivider(null);
            mLayoutParams = new android.support.v7.widget.GridLayout.LayoutParams();
            mLayoutParams.width = dm.widthPixels / 2;
            mLayoutParams.height = android.support.v7.widget.GridLayout.LayoutParams.MATCH_PARENT;
            mLayoutParams.setMargins(0, 0, 2, 0);
            mLvParentCategory.setLayoutParams(mLayoutParams);
            mLvParentCategory.setBackgroundResource(R.drawable.bg_filter_and_sort);
            mLvParentCategory.setDivider(new ColorDrawable(activity.getResources().getColor(R.color.item_line_grey)));
            mLvParentCategory.setDividerHeight(2);
            mLvParentCategory.setAdapter(mCategoryAdapter);

            //子类别适配器
            mLvChildrenCategory = new ListView(activity);
            mLvChildrenCategory.setLayoutParams(mLayoutParams);
            mLvChildrenCategory.setDivider(new ColorDrawable(activity.getResources().getColor(R.color.item_line_grey)));
            mLvChildrenCategory.setDividerHeight(2);
            mLvChildrenCategory.setBackgroundResource(R.drawable.bg_filter_and_sort);
            mChildrenCategoryAdapter = new CategoryAdapter(activity, childrenStrings[0], CategoryAdapter.TYPE_CHILD);
            mLvChildrenCategory.setAdapter(mChildrenCategoryAdapter);

            mLvParentCategory.setOnItemClickListener(parentItemClickListener);
            mLvChildrenCategory.setOnItemClickListener(childrenItemClickListener);
            mGlContainer.addView(mLvParentCategory);
            mGlContainer.addView(mLvChildrenCategory);
        } else {
            llAction.setVisibility(View.GONE);
            mHsvContainer.setVisibility(View.GONE);
            lvSort.setVisibility(View.VISIBLE);
            lvSort.setAdapter(mCategoryAdapter);
            lvSort.setDivider(new ColorDrawable(activity.getResources().getColor(R.color.item_line_grey)));
            lvSort.setDividerHeight(2);
            lvSort.setOnItemClickListener(sortItemClickListener);
            //最多显示8个item的高度
            if (parentStrings.length > 8) {
                View item = mCategoryAdapter.getView(0, null, lvSort);
                item.measure(0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (8 * item.getMeasuredHeight()));
                lvSort.setLayoutParams(params);
            }
        }
        mMap = new HashMap<Integer, Integer>();
        View vClear = contentView.findViewById(R.id.ll_filter_clear);
        View vOk = contentView.findViewById(R.id.rl_filter_ok);
        vClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                mChildrenCategoryAdapter.setSelectedPosition(0);
                mChildrenCategoryAdapter.notifyDataSetChanged();
            }
        });
        vOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectCategory != null) {
                    mSelectCategory.selectFilter(mMap);
                }
                dismiss();
            }
        });
    }

    /**
     * 子类点击事件
     */
    private AdapterView.OnItemClickListener childrenItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mGlContainer.getChildCount() == 3) {
                mGlContainer.removeViewAt(2);
            }
            mChildrenCategoryAdapter.setSelectedPosition(position);
            if (mGrandChildrenStrings != null && mGrandChildrenStrings[mCategoryAdapter.getmPos()][position].length != 0) {
                ListView thirdList = new ListView(parent.getContext());
                thirdList.setLayoutParams(mLayoutParams);
                thirdList.setDivider(new ColorDrawable(parent.getContext().getResources().getColor(R.color.item_line_grey)));
                thirdList.setDividerHeight(2);
                thirdList.setBackgroundResource(R.drawable.bg_filter_and_sort);
                CategoryAdapter childrenCategoryAdapter = new CategoryAdapter(parent.getContext(), mChildrenStrings[position], CategoryAdapter.TYPE_CHILD);
                thirdList.setAdapter(childrenCategoryAdapter);
                mGlContainer.addView(thirdList);
                mHsvContainer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHsvContainer.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                }, 100);
                childrenCategoryAdapter.setHaveThreeListVIew(true);
            }
            mChildrenCategoryAdapter.notifyDataSetChanged();
            mMap.put(mParentPos, position);
        }
    };

    /**
     * 父类点击事件
     */
    private AdapterView.OnItemClickListener parentItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mGlContainer.getChildCount() == 3) {
                mGlContainer.removeViewAt(2);
            }
            mChildrenCategoryAdapter.setData(mChildrenStrings[position]);
            mChildrenCategoryAdapter.setHaveThreeListVIew(false);
            mChildrenCategoryAdapter.setSelectedPosition(0);
            mChildrenCategoryAdapter.notifyDataSetChanged();
            mCategoryAdapter.setSelectedPosition(position);
            mCategoryAdapter.notifyDataSetChanged();
            mParentPos = position;
        }
    };

    /**
     * 选择成功回调
     */
    public interface SelectCategory {
        /**
         * 筛选的map
         * key为左,value为右
         *
         * @param map
         */
        public void selectFilter(Map<Integer, Integer> map);

        /**
         * 排序的回调
         * @param pos 选择位置
         */
        public void selectSort(int pos);
    }


    /**
     * 排序点击事件
     */
    private AdapterView.OnItemClickListener sortItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mSelectCategory != null) {
                mSelectCategory.selectSort(position);
            }
            dismiss();
        }
    };

}