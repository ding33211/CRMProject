package com.soubu.crmproject.view.activity;

import android.content.Intent;
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
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.CompileUtil;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-29.
 */
public class AddContractActivity extends ActivityPresenter<AddSomethingActivityDelegate> {
    private List<AddItem> mList;
    private boolean mFromEdit;
    private ContractParams mContractParams;
    private String mCustomerId;


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
                if (viewDelegate.verifyRequired()) {
                    if (mFromEdit) {
                        Map<String, String> map = CompileUtil.compile(mContractParams, getNewContractParams());
                        Log.e("xxxxxxxxxxxxxx", "xxxxxxxxxxx " + map);
                        //附件先不做考虑
                        map.remove("attachments");
                        if (map.size() > 0) {
                            RetrofitRequest.getInstance().updateContract(mContractParams.getId(), map);
                        }
                    } else {
                        RetrofitRequest.getInstance().addContract(getNewContractParams());
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
        mList = new ArrayList<>();
        AddItem item = new AddItem();
        item.setTitleRes(R.string.essential_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contract_title);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getTitle())) {
            item.setContent(mContractParams.getTitle());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.related_customer);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getCustomer())) {
            item.setContent(mContractParams.getCustomer());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.related_business_opportunity);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getDeal())) {
            item.setContent(mContractParams.getDeal());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.related_product);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getProduct())) {
            item.setContent(mContractParams.getProduct());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contact_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contract_type);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getType())) {
//            CharSequence[] array = SearchUtil.searchContractTypeArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchContractTypeWebArray(getApplicationContext());
            item.setContent(mContractParams.getType());
        }
        item.setArrayRes(R.array.contract_type);
        item.setWebArrayRes(R.array.contract_type_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contract_serial_number);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getSerialNumber())) {
            item.setContent(mContractParams.getSerialNumber());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contract_amount_price);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getAmountPrice())) {
            item.setContent(mContractParams.getAmountPrice());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contract_status);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getStatus())) {
//            CharSequence[] array = SearchUtil.searchContractStateArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchContractStateWebArray(getApplicationContext());
            item.setContent(mContractParams.getStatus());
        }
        item.setArrayRes(R.array.contract_state);
        item.setWebArrayRes(R.array.contract_state_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.start_date);
        if (mFromEdit && mContractParams.getStartedAt() != null) {
            item.setDate(mContractParams.getStartedAt());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE_DATE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.finish_date);
        if (mFromEdit && mContractParams.getFinishedAt() != null) {
            item.setDate(mContractParams.getFinishedAt());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE_DATE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.pay_method);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getPayMethod())) {
//            CharSequence[] array = SearchUtil.searchContractPayMethodArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchContractPayMethodWebArray(getApplicationContext());
            item.setContent(mContractParams.getPayMethod());
        }
        item.setArrayRes(R.array.contract_pay_method);
        item.setWebArrayRes(R.array.contract_pay_method_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.client_signed_person);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getClientSignedPerson())) {
            item.setContent(mContractParams.getClientSignedPerson());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.signed_person);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getSignedPerson())) {
            item.setContent(mContractParams.getSignedPerson());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.signed_date);
        if (mFromEdit && mContractParams.getClosedAt() != null) {
            item.setDate(mContractParams.getClosedAt());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE_DATE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contract_attachments);
        if (mFromEdit && mContractParams.getAttachments() != null) {
            item.setContent(mContractParams.getAttachments().toString());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.remark);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getNote())) {
            item.setContent(mContractParams.getNote());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.founder_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.manager);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getManager())) {
            item.setContent(mContractParams.getManager());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
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
        mContractParams = (ContractParams) getIntent().getSerializableExtra(Contants.EXTRA_CONTRACT);
        mFromEdit = false;
        if (mContractParams != null) {
            mFromEdit = true;
            viewDelegate.setTitle(R.string.edit_contract);
        } else {
            viewDelegate.setTitle(R.string.add_contract);
        }
    }


    public ContractParams getNewContractParams() {
        List<AddItem> list = viewDelegate.getData();
        ContractParams contractParams;
        if (mFromEdit) {
            try {
                contractParams = mContractParams.clone();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            contractParams = new ContractParams();
        }
        for (AddItem item : list) {
            if (item.getItemType() == AddSomethingRvAdapter.TYPE_LABEL || item.getItemType() == AddSomethingRvAdapter.TYPE_OTHER) {
                continue;
            }
            if (item.getTitleRes() == R.string.contract_title) {
                contractParams.setTitle(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.related_customer && !TextUtils.isEmpty(mCustomerId)) {
                contractParams.setCustomer(mCustomerId);
                continue;
            }
            if (item.getTitleRes() == R.string.related_business_opportunity) {
                contractParams.setDeal(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.related_product) {
                contractParams.setProduct(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.contract_type) {
                contractParams.setType(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.contract_serial_number) {
                contractParams.setSerialNumber(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.contract_amount_price) {
                contractParams.setAmountPrice(item.getContent());
            }
            if (item.getTitleRes() == R.string.contract_status) {
                contractParams.setStatus(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.start_date) {
                contractParams.setStartedAt(item.getDate());
                continue;
            }
            if (item.getTitleRes() == R.string.finish_date) {
                contractParams.setFinishedAt(item.getDate());
                continue;
            }
            if (item.getTitleRes() == R.string.pay_method) {
                contractParams.setPayMethod(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.client_signed_person) {
                contractParams.setClientSignedPerson(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.signed_person) {
                contractParams.setSignedPerson(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.signed_date) {
                contractParams.setClosedAt(item.getDate());
                continue;
            }
//            if (item.getTitleRes() == R.string.contract_attachments) {
//                contractParams.setAttachments(new Object[]{});
//                continue;
//            }
            if (item.getTitleRes() == R.string.remark) {
                contractParams.setNote(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.manager) {
                contractParams.setManager(item.getContent());
                continue;
            }
        }
        return contractParams;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AddSomethingRvAdapter.REQUEST_CODE_CHOOSE_CUSTOMER) {
                mCustomerId = data.getStringExtra(Contants.EXTRA_CUSTOMER_ID);
                String name = data.getStringExtra(Contants.EXTRA_CUSTOMER_NAME);
                viewDelegate.setCustomerName(name);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContractParams[] params) {
        if (params != null && params[0] != null) {
            if (mFromEdit) {
                ShowWidgetUtil.showLong(R.string.edit_params_succeed_message);
            } else {
                ShowWidgetUtil.showLong(R.string.add_params_succeed_message);
            }
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }


    /**
     * 错误信息
     *
     * @param errorMsg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(String errorMsg) {
        ShowWidgetUtil.showLong(errorMsg);
    }

}
