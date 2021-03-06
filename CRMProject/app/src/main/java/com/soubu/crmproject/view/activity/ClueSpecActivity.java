package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.SpecActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-8-24.
 */
public class ClueSpecActivity extends ActivityPresenter<SpecActivityDelegate> {
    List<AddItem> mList;
    boolean hasTop;
    ClueParams mClueParams;
    CharSequence[] mStateArray;
    CharSequence[] mStateWebArray;
    CharSequence[] mSourceArray;
    CharSequence[] mSourceWebArray;
    boolean ifHighSeas = false;

    @Override
    protected Class<SpecActivityDelegate> getDelegateClass() {
        return SpecActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        mClueParams = (ClueParams) getIntent().getSerializableExtra(Contants.EXTRA_CLUE);
        mStateArray = SearchUtil.searchClueStateArray(getApplicationContext());
        mStateWebArray = SearchUtil.searchClueStateWebArray(getApplicationContext());
        mSourceArray = SearchUtil.searchClueSourceArray(getApplicationContext());
        mSourceWebArray = SearchUtil.searchClueSourceWebArray(getApplicationContext());
        initClueParams(mClueParams);
    }

    private void initClueParams(ClueParams clueParams) {
        mList = new ArrayList<>();
        AddItem addItem = new AddItem();
        addItem.setTitleRes(R.string.essential_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(clueParams.getCompanyName(), R.string.company_name, true);
        initItem(clueParams.getContactName(), R.string.name, hasTop ? false : true);
        initItem(clueParams.getPosition(), R.string.post, hasTop ? false : true);
        initItem(clueParams.getDepartment(), R.string.department, hasTop ? false : true);
        if(!hasTop){
            mList.remove(mList.size() - 1);
        } else {
            hasTop = false;
        }
        addItem = new AddItem();
        addItem.setTitleRes(R.string.connection_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(clueParams.getMobile(), R.string.mobile, true);
        initItem(clueParams.getPhone(), R.string.phone, hasTop ? false : true);
        initItem(clueParams.getQq(), R.string.qq, hasTop ? false : true);
        initItem(clueParams.getWechat(), R.string.wechat, hasTop ? false : true);
        initItem(clueParams.getWangwang(), R.string.wangwang, hasTop ? false : true);
        initItem(clueParams.getEmail(), R.string.email, hasTop ? false : true);
        initItem(clueParams.getWebsite(), R.string.website, hasTop ? false : true);
        initItem(clueParams.getAddress(), R.string.address, hasTop ? false : true);
        initItem(clueParams.getPostcode(), R.string.postcode, hasTop ? false : true);
        if(!hasTop){
            mList.remove(mList.size() - 1);
        } else {
            hasTop = false;
        }
        addItem = new AddItem();
        addItem.setTitleRes(R.string.other_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(TextUtils.isEmpty(clueParams.getStatus()) ? "" : mStateArray[SearchUtil.searchInArray(mStateWebArray, clueParams.getStatus())].toString(), R.string.follow_state, true);
        initItem(TextUtils.isEmpty(clueParams.getSource()) ? "" : mSourceArray[SearchUtil.searchInArray(mSourceWebArray, clueParams.getSource())].toString(), R.string.clue_from, hasTop ? false : true);
        initItem(clueParams.getNote(), R.string.remark, hasTop ? false : true);
        if(!hasTop){
            mList.remove(mList.size() - 1);
        } else {
            hasTop = false;
        }
        addItem = new AddItem();
        addItem.setTitleRes(R.string.manager_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        if(clueParams.getUser() != null && !TextUtils.isEmpty(clueParams.getUser().getUsername())){
            initItem(clueParams.getUser().getUsername(), R.string.manager, true);
        } else if(clueParams.getCreator() != null && !TextUtils.isEmpty(clueParams.getCreator().getUsername())){
            initItem(clueParams.getCreator().getUsername(), R.string.manager, true);
        } else {
            initItem(CrmApplication.getContext().getName(), R.string.manager, true);
        }
        viewDelegate.setData(mList);
    }


    public void initItem(String content, int labelRes, boolean isTop) {
        if (!TextUtils.isEmpty(content) && !TextUtils.equals(content, "0")) {
            if (!isTop) {
                mList.get(mList.size() - 1).setItemType(AddSomethingRvAdapter.TYPE_ITEM_UNABLE);
            } else {
                hasTop = true;
            }
            AddItem addItem = new AddItem();
            addItem.setTitleRes(labelRes);
            addItem.setContent(content);
            addItem.setItemType(AddSomethingRvAdapter.TYPE_OTHER);
            mList.add(addItem);
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        if (!ifHighSeas) {
            viewDelegate.setSettingText(R.string.edit, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ClueSpecActivity.this, AddClueActivity.class);
                    intent.putExtra(Contants.EXTRA_CLUE, mClueParams);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.clue_spec);
        ifHighSeas = getIntent().getIntExtra(Contants.EXTRA_FROM, -1) == Contants.FROM_CLUE_HIGH_SEAS;
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ClueParams[] params) {
        List<ClueParams> list = Arrays.asList(params);
        mClueParams = list.get(0);
        initClueParams(mClueParams);
    }

}
