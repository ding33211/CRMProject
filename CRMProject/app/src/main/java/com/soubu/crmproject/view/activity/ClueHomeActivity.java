package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by dingsigang on 16-8-22.
 */
public class ClueHomeActivity extends ActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener {
    ClueParams mClueParams;
    CharSequence[] mStateArray;
    CharSequence[] mStateArrayWeb;

    @Override
    protected Class<Big4HomeActivityDelegate> getDelegateClass() {
        return Big4HomeActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setEntity(mClueParams);
        RetrofitRequest.getInstance().getClueFollow(mClueParams.getId(), null, null, null, null, null);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.rl_content);
        viewDelegate.setSettingMenuListener(R.menu.clue_home, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_top) {
            Intent intent = new Intent(this, ClueSpecActivity.class);
            intent.putExtra(Contants.EXTRA_CLUE, mClueParams);
            startActivity(intent);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setTitle(R.string.clue_home);
        viewDelegate.setFrom(Big4HomeActivityDelegate.FROM_CLUE);
        mStateArray = getResources().getStringArray(R.array.clue_status);
        mStateArrayWeb = getResources().getStringArray(R.array.clue_status_web);
        mClueParams = (ClueParams) getIntent().getSerializableExtra(Contants.EXTRA_CLUE);
        ((TextView) viewDelegate.get(R.id.tv_title)).setText(mClueParams.getCompanyName());
        ((TextView) viewDelegate.get(R.id.tv_company_name)).setText(mClueParams.getContactName());
        ((TextView) viewDelegate.get(R.id.tv_follow_state)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, mClueParams.getStatus())]);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(List<ClueParams> list) {
        mClueParams = list.get(0);
        viewDelegate.setEntity(mClueParams);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void refreshFollow(List<FollowParams> list) {
//        viewDelegate.setViewPagerData(1, list);
//    }

}
