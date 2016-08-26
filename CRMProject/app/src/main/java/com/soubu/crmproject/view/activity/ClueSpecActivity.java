package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.ClueSpecActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-24.
 */
public class ClueSpecActivity extends ActivityPresenter<ClueSpecActivityDelegate> {
    List<AddItem> mList;
    boolean hasTop;
    ClueParams mClueParams;

    @Override
    protected Class<ClueSpecActivityDelegate> getDelegateClass() {
        return ClueSpecActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        mClueParams = (ClueParams) getIntent().getSerializableExtra("clue");
        initClueParams(mClueParams);
    }

    private void initClueParams(ClueParams clueParams) {
        mList = new ArrayList<>();
        AddItem addItem = new AddItem();
        addItem.setTitleRes(R.string.essential_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(clueParams.getContactName(), R.string.name, true);
        initItem(clueParams.getPosition(), R.string.post, hasTop ? false : true);
        initItem(clueParams.getDepartment(), R.string.department, hasTop ? false : true);
        initItem(clueParams.getCompanyName(), R.string.company_name, hasTop ? false : true);
        hasTop = false;
        addItem = new AddItem();
        addItem.setTitleRes(R.string.contact_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(clueParams.getPhone(), R.string.phone, true);
        initItem(clueParams.getMobile(), R.string.mobile, hasTop ? false : true);
        initItem(clueParams.getQq(), R.string.qq, hasTop ? false : true);
        initItem(clueParams.getWechat(), R.string.wechat, hasTop ? false : true);
        initItem(clueParams.getWangwang(), R.string.wangwang, hasTop ? false : true);
        initItem(clueParams.getEmail(), R.string.email, hasTop ? false : true);
        initItem(clueParams.getWebsite(), R.string.website, hasTop ? false : true);
        initItem(clueParams.getAddress(), R.string.address, hasTop ? false : true);
        initItem(clueParams.getPostcode(), R.string.postcode, hasTop ? false : true);
        hasTop = false;
        addItem = new AddItem();
        addItem.setTitleRes(R.string.other_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(clueParams.getStatus(), R.string.follow_state, true);
        initItem(clueParams.getSource(), R.string.clue_from, hasTop ? false : true);
        initItem(clueParams.getNote(), R.string.remark, hasTop ? false : true);
        hasTop = false;
        addItem = new AddItem();
        addItem.setTitleRes(R.string.founder_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(clueParams.getManager(), R.string.manager, true);
        viewDelegate.setData(mList);
    }


    public void initItem(String content, int labelRes, boolean isTop) {
        if (!TextUtils.isEmpty(content)) {
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
        viewDelegate.setSettingText(R.string.edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClueSpecActivity.this, AddClueActivity.class);
                intent.putExtra(Contants.EXTRA_CLUE, mClueParams);
                startActivity(intent);
            }
        });
    }


    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(List<ClueParams> list) {
        initClueParams(list.get(0));
    }

    /**
     * 请求clue失败
     *
     * @param t
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }
}
