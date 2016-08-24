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

/**
 * 筛选和排序弹窗
 * Created by dingsigang on 16-8-16.
 */
public class FilterOrSortPopupWindow extends PopupWindow {

    //是筛选还是排序
    private boolean mIsFilter = true;

    private SelectCategory selectCategory;

    private String[][] childrenStrings;
    private String[][][] grandChildrenStrings;

    private CustomHorizontalScrollView mHsvContainer;
    private GridLayout mGlContainer;
    private int mWindowWidth;
    android.support.v7.widget.GridLayout.LayoutParams mLayoutParams;

    private ListView lvParentCategory = null;
    private ListView lvChildrenCategory = null;
    private CategoryAdapter categoryAdapter = null;
    private CategoryAdapter childrenCategoryAdapter = null;

    /**
     * @param parentStrings   字类别数据
     * @param childrenStrings 字类别二位数组
     * @param activity
     * @param selectCategory  回调接口注入
     */
    public FilterOrSortPopupWindow(String[] parentStrings, String[][] childrenStrings, String[][][] grandChildStrings, final Activity activity, SelectCategory selectCategory) {
        this.selectCategory = selectCategory;
        this.childrenStrings = childrenStrings;
        this.grandChildrenStrings = grandChildStrings;

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
        categoryAdapter = new CategoryAdapter(activity, parentStrings, CategoryAdapter.TYPE_PARENT);
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
            lvParentCategory = new ListView(activity);
            lvParentCategory.setDivider(null);
            mLayoutParams = new android.support.v7.widget.GridLayout.LayoutParams();
            mLayoutParams.width = dm.widthPixels / 2;
            mLayoutParams.height = android.support.v7.widget.GridLayout.LayoutParams.MATCH_PARENT;
            mLayoutParams.setMargins(0, 0, 2, 0);
            lvParentCategory.setLayoutParams(mLayoutParams);
            lvParentCategory.setBackgroundResource(R.drawable.bg_filter_and_sort);
            lvParentCategory.setDivider(new ColorDrawable(activity.getResources().getColor(R.color.item_line_grey)));
            lvParentCategory.setDividerHeight(2);
            lvParentCategory.setAdapter(categoryAdapter);

            //子类别适配器
            lvChildrenCategory = new ListView(activity);
            lvChildrenCategory.setLayoutParams(mLayoutParams);
            lvChildrenCategory.setDivider(new ColorDrawable(activity.getResources().getColor(R.color.item_line_grey)));
            lvChildrenCategory.setDividerHeight(2);
            lvChildrenCategory.setBackgroundResource(R.drawable.bg_filter_and_sort);
            childrenCategoryAdapter = new CategoryAdapter(activity, childrenStrings[0], CategoryAdapter.TYPE_CHILD);
            lvChildrenCategory.setAdapter(childrenCategoryAdapter);

            lvParentCategory.setOnItemClickListener(parentItemClickListener);
            lvChildrenCategory.setOnItemClickListener(childrenItemClickListener);
            mGlContainer.addView(lvParentCategory);
            mGlContainer.addView(lvChildrenCategory);
        } else {
            llAction.setVisibility(View.GONE);
            mHsvContainer.setVisibility(View.GONE);
            lvSort.setVisibility(View.VISIBLE);
            lvSort.setAdapter(categoryAdapter);
            lvSort.setDivider(new ColorDrawable(activity.getResources().getColor(R.color.item_line_grey)));
            lvSort.setDividerHeight(2);
            lvSort.setOnItemClickListener(sortItemClickListener);
            //最多显示8个item的高度
            if (parentStrings.length > 8) {
                View item = categoryAdapter.getView(0, null, lvSort);
                item.measure(0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (8 * item.getMeasuredHeight()));
                lvSort.setLayoutParams(params);
            }
        }
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
            childrenCategoryAdapter.setSelectedPosition(position);
            if (grandChildrenStrings != null && grandChildrenStrings[categoryAdapter.getmPos()][position].length != 0) {
                ListView thirdList = new ListView(parent.getContext());
                thirdList.setLayoutParams(mLayoutParams);
                thirdList.setDivider(new ColorDrawable(parent.getContext().getResources().getColor(R.color.item_line_grey)));
                thirdList.setDividerHeight(2);
                thirdList.setBackgroundResource(R.drawable.bg_filter_and_sort);
                CategoryAdapter childrenCategoryAdapter = new CategoryAdapter(parent.getContext(), childrenStrings[position], CategoryAdapter.TYPE_CHILD);
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
            childrenCategoryAdapter.notifyDataSetChanged();
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
            childrenCategoryAdapter.setData(childrenStrings[position]);
            childrenCategoryAdapter.setHaveThreeListVIew(false);
            childrenCategoryAdapter.setSelectedPosition(0);
            childrenCategoryAdapter.notifyDataSetChanged();

            categoryAdapter.setSelectedPosition(position);
            categoryAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 选择成功回调
     */
    public interface SelectCategory {
        /**
         * 把选中的下标通过方法回调回来
         *
         * @param parentSelectPosition     父类选中下标
         * @param childrenSelectPosition   子类选中下标
         * @param grandChildSelectPosition 孙类选中下标
         */
        public void selectCategory(int parentSelectPosition, int childrenSelectPosition, int grandChildSelectPosition);
    }


    /**
     * 排序点击事件
     */
    private AdapterView.OnItemClickListener sortItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectCategory != null) {
                selectCategory.selectCategory(categoryAdapter.getmPos(), 0, 0);
            }
        }
    };

}