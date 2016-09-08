package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.BaseRecyclerViewActivityDelegate;
import com.soubu.crmproject.delegate.ContactActivityDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-9-8.
 */
public class ContactActivity extends ActivityPresenter<ContactActivityDelegate> {
    boolean mIsRefresh = false;
    int mPageNum = 1;

    @Override
    protected Class<ContactActivityDelegate> getDelegateClass() {
        return ContactActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setRightMenuOne(R.drawable.btn_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, AddContactActivity.class);
                intent.putExtra(Contants.EXTRA_CUSTOMER_ID, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID));
                intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_NAME));
                startActivity(intent);
            }
        });
        viewDelegate.registerSwipeRefreshCallBack(new SwipeRefreshAndLoadMoreCallBack() {
            @Override
            public void refresh() {
                getList(true);
            }

            @Override
            public void loadMore() {
                getList(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(true);
    }

    private void getList(boolean isRefresh) {
        mIsRefresh = isRefresh;
        if (isRefresh) {
            mPageNum = 1;
        } else {
            mPageNum++;
        }
        RetrofitRequest.getInstance().getContactList(mPageNum, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID));
    }


    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContactParams[] params) {
        List<ContactParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    refreshData     mIsRefresh   " + mIsRefresh);
        viewDelegate.setData(list, mIsRefresh);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopSwipeRefresh();
        }
    }

    /**
     * 请求clue失败
     *
     * @param t
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }
}
