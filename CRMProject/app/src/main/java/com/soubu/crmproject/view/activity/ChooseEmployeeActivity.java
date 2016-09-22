package com.soubu.crmproject.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ChooseEmployeeRvAdapter;
import com.soubu.crmproject.base.greendao.Staff;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.ChooseEmployeeActivityDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-8-25.
 */
public class ChooseEmployeeActivity extends ActivityPresenter<ChooseEmployeeActivityDelegate> {
    private String mParamId;
    private int mFrom;
    private String mName;

    @Override
    protected Class<ChooseEmployeeActivityDelegate> getDelegateClass() {
        return ChooseEmployeeActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        mFrom = getIntent().getIntExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE);
        if(mFrom == Contants.FROM_ADD_SOMETHING_ACTIVITY){
            viewDelegate.setTitle(R.string.choose_manager);
        } else {
            viewDelegate.setTitle(R.string.choose_transfer);
        }
        mParamId = getIntent().getStringExtra(Contants.EXTRA_PARAM_ID);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnItemClickListener(new ChooseEmployeeRvAdapter.OnItemClickListener() {
            @Override
            public void onClick(final Staff staff) {
//                String message = getString(R.string.transfer_confirm_message, staff.getNickname());
//                if(mFrom == Contants.FROM_ADD_SOMETHING_ACTIVITY){
//                    message = getString(R.string.choose_manager_confirm_message, staff.getNickname());
//                }
//                new AlertDialog.Builder(ChooseEmployeeActivity.this).
//                        setMessage(message).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
                        mName = staff.getNickname();
                        if(mFrom == Contants.FROM_CLUE) {
                            RetrofitRequest.getInstance().transferClue(mParamId, staff.getStaff_id());
                        } else if(mFrom == Contants.FROM_CUSTOMER){
                            RetrofitRequest.getInstance().transferCustomer(mParamId, staff.getStaff_id());
                        } else if(mFrom == Contants.FROM_ADD_SOMETHING_ACTIVITY){
                            Intent intent = new Intent();
                            intent.putExtra(Contants.EXTRA_EMPLOYER_ID, staff.getStaff_id());
                            intent.putExtra(Contants.EXTRA_EMPLOYER_NAME, mName);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
//                    }
//                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).setCancelable(false).show();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ClueParams[] params) {
        if(params != null && params[0] != null){
            Intent intent = new Intent();
            intent.putExtra(Contants.EXTRA_TRANSFER_NAME, mName);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams[] params) {
        if(params != null && params[0] != null){
            Intent intent = new Intent();
            intent.putExtra(Contants.EXTRA_TRANSFER_NAME, mName);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
