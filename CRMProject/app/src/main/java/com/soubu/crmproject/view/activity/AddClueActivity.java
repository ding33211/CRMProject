package com.soubu.crmproject.view.activity;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.CompileUtil;
import com.soubu.crmproject.utils.SearchUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-19.
 */
public class AddClueActivity extends Big4AddActivityPresenter {
    private List<AddItem> mList;
    private ClueParams mClueParams;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewDelegate.verifyRequired()) {
                    if (mFromEdit) {
                        Map<String, String> map = CompileUtil.compile(mClueParams, getNewClueParams());
                        Log.e("xxxxxxxxxxxxxx", "xxxxxxxxxxx " + map);
                        if (map.size() > 0) {
                            mEventBusJustForThis = true;
                            RetrofitRequest.getInstance().updateClue(mClueParams.getId(), map);
                        } else {
                            finish();
                        }
                    } else {
                        mEventBusJustForThis = true;
                        RetrofitRequest.getInstance().addClue(getNewClueParams());
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mList = new ArrayList<>();
        AddItem item = new AddItem();
        item.setTitleRes(R.string.essential_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.company_name);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getCompanyName())) {
            item.setContent(mClueParams.getCompanyName());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
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
        item.setTitleRes(R.string.address);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getAddress())) {
            item.setContent(mClueParams.getAddress());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.connection_information);
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
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.post);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getPosition())) {
            item.setContent(mClueParams.getPosition());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.mobile);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getMobile())) {
            item.setContent(mClueParams.getMobile());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_PHONE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.phone);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getPhone())) {
            item.setContent(mClueParams.getPhone());
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

//        item = new AddItem();
//        item.setTitleRes(R.string.area);
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.add_contact);
//        item.setItemType(AddSomethingRvAdapter.TYPE_OTHER);
//        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.other_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.follow_state);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getStatus())) {
//            CharSequence[] array = SearchUtil.searchClueStateArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchClueStateWebArray(getApplicationContext());
            item.setContent(mClueParams.getStatus());
        } else {
            item.setContent(SearchUtil.searchClueStateWebArray(this)[0].toString());
        }
        item.setArrayRes(R.array.clue_status);
        item.setWebArrayRes(R.array.clue_status_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.clue_from);
        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getSource())) {
//            CharSequence[] array = SearchUtil.searchClueSourceArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchClueSourceWebArray(getApplicationContext());
            item.setContent(mClueParams.getSource());
        }
        item.setArrayRes(R.array.clue_source);
        item.setWebArrayRes(R.array.clue_source_web);
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
        item.setTitleRes(R.string.manager_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.manager);
        if (mFromEdit && mClueParams.getUser() != null && !TextUtils.isEmpty(mClueParams.getUser().getUsername())) {
            item.setContent(mClueParams.getUser().getUsername());
            mManagerId = mClueParams.getUser().getId();
        } else if (mFromEdit && mClueParams.getCreator() != null && !TextUtils.isEmpty(mClueParams.getUser().getUsername())) {
            item.setContent(mClueParams.getCreator().getUsername());
            mManagerId = mClueParams.getCreator().getId();
        } else {
            item.setContent(CrmApplication.getContext().getName());
            mManagerId = CrmApplication.getContext().getUid();
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.in_department);
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
//        mList.add(item);
        viewDelegate.setData(mList);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mClueParams = (ClueParams) getIntent().getSerializableExtra(Contants.EXTRA_CLUE);
        mFromEdit = false;
        if (mClueParams != null) {
            mFromEdit = true;
            viewDelegate.setTitle(R.string.edit_clue);
        } else {
            viewDelegate.setTitle(R.string.add_clue);
        }
    }

    public ClueParams getNewClueParams() {
        List<AddItem> list = viewDelegate.getData();
        ClueParams clueParams;
        if (mFromEdit) {
            try {
                clueParams = mClueParams.clone();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            clueParams = new ClueParams();
        }
        for (AddItem item : list) {
            if (item.getItemType() == AddSomethingRvAdapter.TYPE_LABEL || TextUtils.isEmpty(item.getContent())) {
                continue;
            }
            if (item.getTitleRes() == R.string.name) {
                clueParams.setContactName(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.department) {
                clueParams.setDepartment(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.company_name) {
                clueParams.setCompanyName(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.post) {
                clueParams.setPosition(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.phone) {
                clueParams.setPhone(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.mobile) {
                clueParams.setMobile(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.qq) {
                clueParams.setQq(item.getContent());
            }
            if (item.getTitleRes() == R.string.email) {
                clueParams.setEmail(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.website) {
                clueParams.setWebsite(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.address) {
                clueParams.setAddress(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.follow_state) {
                clueParams.setStatus(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.clue_from) {
                clueParams.setSource(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.remark) {
                clueParams.setNote(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.manager) {
                if (!TextUtils.isEmpty(mManagerId)) {
                    clueParams.setUserId(mManagerId);
                }
                continue;
            }
        }
        return clueParams;
    }

}
