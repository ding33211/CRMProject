package com.soubu.crmproject.view.activity;

import android.content.Intent;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddSomethingActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;
import com.soubu.crmproject.widget.customcalendar.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dingsigang on 16-9-19.
 */
public abstract class Big4AddActivityPresenter extends ActivityPresenter<AddSomethingActivityDelegate>{
    String mManagerId;
    boolean mFromEdit;
    //是否是线索转客户,商机转合同
    boolean mTransfer;

    @Override
    protected Class<AddSomethingActivityDelegate> getDelegateClass() {
        return AddSomethingActivityDelegate.class;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ClueParams[] params) {
        //每次修改或者是添加只会有一条
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        if (params != null && params.length == 1) {
            success();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        if (params != null && params.length == 1) {
            if(mTransfer){
                Intent intent = new Intent();
                intent.putExtra(Contants.EXTRA_CUSTOMER, params[0]);
                setResult(RESULT_OK, intent);
            }
            success();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(BusinessOpportunityParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        if (params != null && params.length == 1) {
            if(!mFromEdit && !mTransfer){
                EventBus.getDefault().post(Contants.EVENT_BUS_KEY_ADD_BUSINESS);
            }
            success();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContractParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        if (params != null && params.length == 1) {
            if(!mFromEdit && !mTransfer){
                EventBus.getDefault().post(Contants.EVENT_BUS_KEY_ADD_CONTRACT);
            } else if(mTransfer){
                Intent intent = new Intent();
                intent.putExtra(Contants.EXTRA_CONTRACT, params[0]);
                setResult(RESULT_OK, intent);
            }
            success();
        }
    }

    private void success(){
        if(isFinishing()){
            return;
        }
        if (mFromEdit && !mTransfer) {
            ShowWidgetUtil.showLong(R.string.edit_params_succeed_message);
        } else if (!mTransfer){
            ShowWidgetUtil.showLong(R.string.add_params_succeed_message);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == AddSomethingRvAdapter.REQUEST_CODE_CHOOSE_MANAGER){
            viewDelegate.setLastClickName(data.getStringExtra(Contants.EXTRA_EMPLOYEE_NAME));
            mManagerId = data.getStringExtra(Contants.EXTRA_EMPLOYEE_ID);
        }
    }
}
