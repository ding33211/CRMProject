package com.soubu.crmproject.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.FragmentPresenter;
import com.soubu.crmproject.delegate.ProfileFragmentDelegate;
import com.soubu.crmproject.utils.UserManager;
import com.soubu.crmproject.view.activity.LoginActivity;

public class ProfileFragment extends FragmentPresenter<ProfileFragmentDelegate> implements View.OnClickListener{
    @Override
    protected Class<ProfileFragmentDelegate> getDelegateClass() {
        return ProfileFragmentDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.tv_logout);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_logout:
                new AlertDialog.Builder(getActivity()).setTitle(R.string.alert).setMessage(R.string.logout_alert_message).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CrmApplication.getContext().setToken("");
                        CrmApplication.getContext().finishAllActivity();
                        UserManager.clearUser();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
        }
    }
}
