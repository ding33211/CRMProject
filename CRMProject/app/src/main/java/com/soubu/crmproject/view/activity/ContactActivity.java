package com.soubu.crmproject.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.base.greendao.ContactDao;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.ContactActivityDelegate;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-9-8.
 */
public class ContactActivity extends ActivityPresenter<ContactActivityDelegate> {
    //    boolean mIsRefresh = false;
//    int mPageNum = 1;
    List<ContactParams> mList;
    private int mIndex = -1;
    private int mFrom = -1;

    @Override
    protected Class<ContactActivityDelegate> getDelegateClass() {
        return ContactActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mFrom = getIntent().getIntExtra(Contants.EXTRA_FROM, -1);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        if (mFrom != Contants.FROM_ADD_SOMETHING_ACTIVITY) {
            viewDelegate.setRightMenuOne(R.drawable.btn_add, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ContactActivity.this, AddContactActivity.class);
                    intent.putExtra(Contants.EXTRA_CUSTOMER_ID, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID));
                    intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_NAME));
                    startActivity(intent);
                }
            });
        }

//        viewDelegate.registerSwipeRefreshCallBack(new SwipeRefreshAndLoadMoreCallBack() {
//            @Override
//            public void refresh() {
//                getList(true);
//            }
//
//            @Override
//            public void loadMore() {
//                getList(false);
//            }
//        });
        viewDelegate.setOnRecyclerViewItemClickListener(new BaseWithFooterRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                ContactParams param = viewDelegate.getContactParams(pos);
                if(mFrom == Contants.FROM_ADD_SOMETHING_ACTIVITY){
                    Intent intent = new Intent();
                    intent.putExtra(Contants.EXTRA_CONTACT_ID, param.getId());
                    intent.putExtra(Contants.EXTRA_CONTACT_NAME, param.getName());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent(ContactActivity.this, ContactSpecActivity.class);
                    intent.putExtra(Contants.EXTRA_CONTACT, param);
                    intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_NAME));
                    startActivity(intent);
                }

            }
        });
        viewDelegate.setOnPhoneIconCLickListener(new BaseWithFooterRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                final ContactParams param = mList.get(pos);
                if (!TextUtils.isEmpty(param.getMobile()) && !TextUtils.isEmpty(param.getPhone())) {
                    final String[] items = new String[]{param.getMobile(), param.getPhone()};
                    AlertDialog dialog = new AlertDialog.Builder(ContactActivity.this).setTitle(R.string.please_choose_phone)
                            .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mIndex = 0;
                                    mIndex = which;
                                }
                            }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RetrofitRequest.getInstance().touchContact(param.getId());
                                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + items[mIndex]));
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setCancelable(false).create();
                    dialog.show();
                } else if (!TextUtils.isEmpty(param.getMobile())) {
                    RetrofitRequest.getInstance().touchContact(param.getId());
                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + param.getMobile()));
                    startActivity(intent);
                } else {
                    RetrofitRequest.getInstance().touchContact(param.getId());
                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + param.getPhone()));
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        ContactDao contactDao = DBHelper.getInstance(this).getContactDao();
        List<Contact> list = contactDao.queryBuilder().where(ContactDao.Properties.Customer.eq(getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID)))
                .orderDesc(ContactDao.Properties.TouchedAt).list();
        mList = new ArrayList<>();
        for (Contact contact : list) {
            mList.add(contact.copyToContactParams());
        }
        viewDelegate.setData(mList, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getList(true);
        Log.e("xxxxxxxx", "onResume");
    }

//    private void getList(boolean isRefresh) {
//        mIsRefresh = isRefresh;
//        if (isRefresh) {
//            mPageNum = 1;
//        } else {
//            mPageNum++;
//        }
//        RetrofitRequest.getInstance().getContactList(mPageNum, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID));
//        Log.e("xxxxxxxx", "getList");
//
//    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContactParams[] params) {
//        List<ContactParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    params[0]   " + params[0]);
        mList.add(params[0]);
        viewDelegate.setData(mList, true);
//        if (mIsRefresh) {
//            if(list.size() > 1){
//                mIsRefresh = false;
//            }
//            viewDelegate.stopSwipeRefresh();
//        }
    }

}
