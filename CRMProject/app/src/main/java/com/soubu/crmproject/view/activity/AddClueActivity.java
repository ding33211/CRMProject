package com.soubu.crmproject.view.activity;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddSomethingActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.CompileUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-19.
 */
public class AddClueActivity extends ActivityPresenter<AddSomethingActivityDelegate> {
    private List<AddItem> mList;
    private boolean mFromEdit;
    private ClueParams mClueParams;

    @Override
    protected Class<AddSomethingActivityDelegate> getDelegateClass() {
        return AddSomethingActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewDelegate.verifyRequired()){
                    if(mFromEdit){
                        Map<String, String> map = CompileUtil.compile(mClueParams, getNewClueParams());
                        Log.e("xxxxxxxxxxxxxx", "xxxxxxxxxxx " + map);
                        RetrofitRequest.getInstance().updateClue(mClueParams.getId(), map);
                    } else {
                        RetrofitRequest.getInstance().addClue(getNewClueParams());
                    }
                    finish();
                } else {
                    ShowWidgetUtil.showLong(R.string.please_complete_required);
                }
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        mClueParams = (ClueParams) getIntent().getSerializableExtra("clue");
        mFromEdit = false;
        if (mClueParams != null) {
            mFromEdit = true;
        }
        mList = new ArrayList<>();
        AddItem item = new AddItem();
        item.setTitleRes(R.string.essential_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.name);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getContactName())) {
            item.setContent(mClueParams.getContactName());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.department);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getDepartment())) {
            item.setContent(mClueParams.getDepartment());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.company_name);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getCompanyName())) {
            item.setContent(mClueParams.getCompanyName());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.post);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getPosition())) {
            item.setContent(mClueParams.getPosition());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contact_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.phone);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getPhone())) {
            item.setContent(mClueParams.getPhone());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_PHONE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.mobile);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getMobile())) {
            item.setContent(mClueParams.getMobile());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_PHONE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.qq);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getQq())) {
            item.setContent(mClueParams.getQq());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.email);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getEmail())) {
            item.setContent(mClueParams.getEmail());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.website);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getWebsite())) {
            item.setContent(mClueParams.getWebsite());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.area);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.address);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getAddress())) {
            item.setContent(mClueParams.getAddress());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_LOCATE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.add_contact);
        item.setItemType(AddSomethingRvAdapter.TYPE_OTHER);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.other_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.follow_state);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getStatus())) {
            item.setContent(mClueParams.getStatus());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.clue_from);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getSource())) {
            item.setContent(mClueParams.getSource());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.remark);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getNote())) {
            item.setContent(mClueParams.getNote());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.founder_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.manager);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getManager())) {
            item.setContent(mClueParams.getManager());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.in_department);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        viewDelegate.setData(mList);
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setTitle(R.string.add_clue);
    }

    public ClueParams getNewClueParams(){
        List<AddItem> list = viewDelegate.getData();
        ClueParams clueParams;
        if(mFromEdit) {
            try {
                clueParams = mClueParams.clone();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            clueParams = new ClueParams();
        }
        for(AddItem item : list){
            if(item.getItemType() == AddSomethingRvAdapter.TYPE_LABEL || item.getItemType() == AddSomethingRvAdapter.TYPE_OTHER){
                continue;
            }
            if(item.getTitleRes() == R.string.name){
                clueParams.setContactName(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.department){
                clueParams.setDepartment(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.company_name){
                clueParams.setCompanyName(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.post){
                clueParams.setPosition(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.phone){
                clueParams.setPhone(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.mobile){
                clueParams.setMobile(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.qq){
                clueParams.setQq(item.getContent());
            }
            if(item.getTitleRes() == R.string.email){
                clueParams.setEmail(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.website){
                clueParams.setWebsite(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.address){
                clueParams.setAddress(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.follow_state){
                clueParams.setStatus(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.clue_from){
                clueParams.setSource(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.remark){
                clueParams.setNote(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.manager){
                clueParams.setManager(item.getContent());
                continue;
            }
        }
        return clueParams;
    }


}
