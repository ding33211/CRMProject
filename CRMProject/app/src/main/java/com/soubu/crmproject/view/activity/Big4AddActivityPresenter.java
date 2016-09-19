package com.soubu.crmproject.view.activity;

import android.content.Intent;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddSomethingActivityDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dingsigang on 16-9-19.
 */
public abstract class Big4AddActivityPresenter extends ActivityPresenter<AddSomethingActivityDelegate>{
    String mManagerId;
    boolean mFromEdit;

    @Override
    protected Class<AddSomethingActivityDelegate> getDelegateClass() {
        return AddSomethingActivityDelegate.class;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ClueParams[] params) {
        if (params != null && params[0] != null) {
            if(isFinishing()){
                return;
            }
            if (mFromEdit) {
                ShowWidgetUtil.showLong(R.string.edit_params_succeed_message);
            } else {
                ShowWidgetUtil.showLong(R.string.add_params_succeed_message);
            }
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        ServerErrorUtil.handleServerError(errorCode);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == AddSomethingRvAdapter.REQUEST_CODE_CHOOSE_MANAGER){
            viewDelegate.setManagerName(data.getStringExtra(Contants.EXTRA_EMPLOYER_NAME));
            mManagerId = data.getStringExtra(Contants.EXTRA_EMPLOYER_ID);
        }
    }
}
