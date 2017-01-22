package com.soubu.crmproject.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.base.greendao.ContactDao;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.delegate.AddSomethingActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.CompileUtil;
import com.soubu.crmproject.utils.RegularUtil;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-29.
 */
public class AddContractActivity extends Big4AddActivityPresenter {
    private List<AddItem> mList;
    private boolean mFromEdit;
    private ContractParams mContractParams;
    private String mCustomerId;
    private String mCustomerName;
    private String mBusinessId;
    private String mBusinessName;
    private String mContactId;
    private String mContactName;
    private String mSignedId;
    private String mSignedName;
    public static final int REQUEST_CODE_CHOOSE_BUSINESS = 1110;
    public static final int REQUEST_CODE_CHOOSE_CLIENT_SIGNED = 1111;
    public static final int REQUEST_CODE_CHOOSE_SIGNED = 1112;


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
                    if (mFromEdit && !mTransfer) {
                        Map<String, String> map = CompileUtil.compile(mContractParams, getNewContractParams());
                        Log.e("xxxxxxxxxxxxxx", "xxxxxxxxxxx " + map);
                        //附件先不做考虑
                        map.remove("attachments");
                        if (map.size() > 0) {
                            mEventBusJustForThis = true;
                            RetrofitRequest.getInstance().updateContract(mContractParams.getId(), map);
                        } else {
                            finish();
                        }
                    } else {
                        mEventBusJustForThis = true;
                        RetrofitRequest.getInstance().addContract(getNewContractParams());
                    }
                }
            }
        });
        //一开始有就有，没有就一直没有
        final String customerIdForChooseBusiness = mCustomerId;
        viewDelegate.setOnRelatedBusinessClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddContractActivity.this, BusinessOpportunityActivity.class);
                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_ADD_SOMETHING_ACTIVITY);
                intent.putExtra(Contants.EXTRA_CUSTOMER_ID, customerIdForChooseBusiness);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_BUSINESS);
            }
        });
        viewDelegate.setOnClientSignedClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mCustomerId)) {
                    ShowWidgetUtil.showShort(R.string.please_choose_related_business_first);
                    return;
                } else {
                    final List<Contact> contactsList = DBHelper.getInstance(getApplicationContext()).getContactDao().queryBuilder().where(ContactDao.Properties.Customer.eq(mCustomerId))
                            .orderDesc(ContactDao.Properties.TouchedAt).list();
                    if (contactsList.size() > 0) {
                        String[] items = new String[contactsList.size()];
                        for (int i = 0; i < contactsList.size(); i++) {
                            items[i] = contactsList.get(i).getName();
                        }
                        new AlertDialog.Builder(AddContractActivity.this).setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mContactId = contactsList.get(which).getContact_id();
                                viewDelegate.setLastClickName(contactsList.get(which).getName());
                            }
                        }).setCancelable(true).show();
                    } else {
                        ShowWidgetUtil.showLong(R.string.customer_has_no_contact);
                    }
                }

//                Intent intent = new Intent(AddContractActivity.this, ContactActivity.class);
//                intent.putExtra(Contants.EXTRA_CUSTOMER_ID, mCustomerId);
//                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_ADD_SOMETHING_ACTIVITY);
//                startActivityForResult(intent, REQUEST_CODE_CHOOSE_CLIENT_SIGNED);
            }
        });
        viewDelegate.setOnSignedClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddContractActivity.this, ChooseEmployeeActivity.class);
                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_ADD_CONTRACT);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_SIGNED);
            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mContractParams = (ContractParams) getIntent().getSerializableExtra(Contants.EXTRA_CONTRACT);
        mCustomerId = getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID);
        mCustomerName = getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_NAME);
        mBusinessId = getIntent().getStringExtra(Contants.EXTRA_BUSINESS_ID);
        mBusinessName = getIntent().getStringExtra(Contants.EXTRA_BUSINESS_NAME);
        mTransfer = getIntent().getBooleanExtra(Contants.EXTRA_TRANSFER, false);
        mFromEdit = false;
        if (mContractParams != null) {
            mFromEdit = true;
            if (mTransfer) {
                viewDelegate.setTitle(R.string.transfer_contract);
            } else {
                viewDelegate.setTitle(R.string.edit_contract);
            }
        } else {
            viewDelegate.setTitle(R.string.add_contract);
        }
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
        item.setTitleRes(R.string.related_business_opportunity);
        if (!TextUtils.isEmpty(mBusinessName)) {
            item.setContent(mBusinessName);
            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_UNABLE);
        } else {
            if (mFromEdit && mContractParams.getDeal() != null && !TextUtils.isEmpty(mContractParams.getDeal().getTitle())) {
                item.setContent(mContractParams.getDeal().getTitle());
            }
            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        }
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.related_customer);
        if (!TextUtils.isEmpty(mCustomerName)) {
            item.setContent(mCustomerName);
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_UNABLE);
//        } else {
//            if (mFromEdit && mContractParams.getCustomer() != null && !TextUtils.isEmpty(mContractParams.getCustomer().getName())) {
//                item.setContent(mContractParams.getCustomer().getName());
//            }
//            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
//        }
        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.related_product);
//        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getProduct().toString())) {
//            item.setContent(mContractParams.getProduct().toString());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
//        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.connection_information);
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
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getAmountPrice()) && TextUtils.equals(mContractParams.getAmountPrice(), "0")) {
            item.setContent(mContractParams.getAmountPrice());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contract_status);
        if (mFromEdit && !TextUtils.isEmpty(mContractParams.getStatus())) {
//            CharSequence[] array = SearchUtil.searchContractStateArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchContractStateWebArray(getApplicationContext());
            item.setContent(mContractParams.getStatus());
        } else {
            item.setContent(SearchUtil.searchContractStateWebArray(this)[0].toString());
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
        if (mFromEdit && mContractParams.getClientSignedPerson() != null && !TextUtils.isEmpty(mContractParams.getClientSignedPerson().getName())) {
            item.setContent(mContractParams.getClientSignedPerson().getName());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.signed_person);
        if (mFromEdit && mContractParams.getSignedPerson() != null && !TextUtils.isEmpty(mContractParams.getSignedPerson().getUsername())) {
            item.setContent(mContractParams.getSignedPerson().getUsername());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.signed_date);
        if (mFromEdit && mContractParams.getClosedAt() != null) {
            item.setDate(mContractParams.getClosedAt());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE_DATE);
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
        item.setTitleRes(R.string.manager_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.manager);
        if (mFromEdit && mContractParams.getUser() != null && !TextUtils.isEmpty(mContractParams.getUser().getUsername())) {
            item.setContent(mContractParams.getUser().getUsername());
            mManagerId = mContractParams.getUser().getId();
        } else if (mFromEdit && mContractParams.getCreator() != null && !TextUtils.isEmpty(mContractParams.getUser().getUsername())) {
            item.setContent(mContractParams.getCreator().getUsername());
            mManagerId = mContractParams.getCreator().getId();
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
            if (item.getItemType() == AddSomethingRvAdapter.TYPE_LABEL || TextUtils.isEmpty(item.getContent())) {
                continue;
            }
            if (item.getTitleRes() == R.string.contract_title) {
                contractParams.setTitle(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.related_customer && !TextUtils.isEmpty(mCustomerId)) {
                contractParams.setCustomerId(mCustomerId);
                continue;
            }
            if (item.getTitleRes() == R.string.related_business_opportunity && !TextUtils.isEmpty(mBusinessId)) {
                contractParams.setDealId(mBusinessId);
                continue;
            }
//            if (item.getTitleRes() == R.string.related_product) {
//                contractParams.setProduct(item.getContent());
//                continue;
//            }
            if (item.getTitleRes() == R.string.contract_type) {
                contractParams.setType(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.contract_serial_number) {
                contractParams.setSerialNumber(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.contract_amount_price && RegularUtil.isInteger(item.getContent())) {
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
            if (item.getTitleRes() == R.string.client_signed_person && !TextUtils.isEmpty(mContactId)) {
                contractParams.setClientSignedPersonId(mContactId);
                continue;
            }
            if (item.getTitleRes() == R.string.signed_person && !TextUtils.isEmpty(mSignedId)) {
                contractParams.setSignedPersonId(mSignedId);
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
                if (!TextUtils.isEmpty(mManagerId)) {
                    contractParams.setUserId(mManagerId);
                }
                continue;
            }
        }
        return contractParams;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AddSomethingRvAdapter.REQUEST_CODE_CHOOSE_CUSTOMER:
                    mCustomerId = data.getStringExtra(Contants.EXTRA_CUSTOMER_ID);
                    viewDelegate.setLastClickName(data.getStringExtra(Contants.EXTRA_CUSTOMER_NAME));
                    break;
                case REQUEST_CODE_CHOOSE_BUSINESS:
                    mBusinessId = data.getStringExtra(Contants.EXTRA_BUSINESS_ID);
                    mCustomerId = data.getStringExtra(Contants.EXTRA_CUSTOMER_ID);
                    viewDelegate.setCustomerName(data.getStringExtra(Contants.EXTRA_CUSTOMER_NAME));
                    viewDelegate.setLastClickName(data.getStringExtra(Contants.EXTRA_BUSINESS_NAME));
                    break;
//                case REQUEST_CODE_CHOOSE_CLIENT_SIGNED:
//                    mContactId = data.getStringExtra(Contants.EXTRA_CONTACT_ID);
//                    viewDelegate.setLastClickName(data.getStringExtra(Contants.EXTRA_CONTACT_NAME));
//                    break;
                case REQUEST_CODE_CHOOSE_SIGNED:
                    mSignedId = data.getStringExtra(Contants.EXTRA_EMPLOYEE_ID);
                    viewDelegate.setLastClickName(data.getStringExtra(Contants.EXTRA_EMPLOYEE_NAME));
                    break;
            }
        }
    }

}
