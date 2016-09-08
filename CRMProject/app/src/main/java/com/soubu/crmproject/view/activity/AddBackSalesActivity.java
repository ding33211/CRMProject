package com.soubu.crmproject.view.activity;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.base.mvp.presenter.FragmentPresenter;
import com.soubu.crmproject.delegate.AddSomethingActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.BackSalesParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.CompileUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-9-7.
 */
public class AddBackSalesActivity extends ActivityPresenter<AddSomethingActivityDelegate> {
    private List<AddItem> mList;


    @Override
    protected Class<AddSomethingActivityDelegate> getDelegateClass() {
        return AddSomethingActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.add_already_sales_back);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewDelegate.verifyRequired()){
                    RetrofitRequest.getInstance().addBackSales(getNewBackSalesParams(), getIntent().getStringExtra(Contants.EXTRA_CONTRACT_ID));
                    finish();
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
        item.setTitleRes(R.string.price_of_sales_back);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.date_of_sales_back);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE_DATE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.price_of_bill);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.method_of_sales_back);
        item.setArrayRes(R.array.contract_pay_method);
        item.setWebArrayRes(R.array.contract_pay_method_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.remark);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        viewDelegate.setData(mList);
    }


    public BackSalesParams getNewBackSalesParams(){
        List<AddItem> list = viewDelegate.getData();
        BackSalesParams backSalesParams = new BackSalesParams();
        backSalesParams.setType(Contants.BACK_SALES_TYPE_DETAIL);
        for(AddItem item : list){
            if(item.getItemType() == AddSomethingRvAdapter.TYPE_LABEL){
                continue;
            }
            if(item.getTitleRes() == R.string.percent_of_sales_back){
                continue;
            }
            if(item.getTitleRes() == R.string.price_of_sales_back){
                backSalesParams.setAmount(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.date_of_sales_back){
                backSalesParams.setCollectedAt(item.getDate());
                continue;
            }
            if(item.getTitleRes() == R.string.price_of_bill){
                continue;
            }
            if(item.getTitleRes() == R.string.method_of_sales_back){
                backSalesParams.setMethod(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.payee){

                continue;
            }
            if(item.getTitleRes() == R.string.remark){
                backSalesParams.setNote(item.getContent());
            }
        }
        return backSalesParams;
    }
}
