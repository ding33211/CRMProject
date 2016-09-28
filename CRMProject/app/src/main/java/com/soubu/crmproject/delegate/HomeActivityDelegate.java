package com.soubu.crmproject.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.view.fragment.CRMFragment;
import com.soubu.crmproject.view.fragment.ProfileFragment;
import com.soubu.crmproject.view.fragment.TodoFragment;
import com.soubu.crmproject.view.fragment.WorkBenchFragment;

/**
 * Created by dingsigang on 16-8-2.
 */
public class HomeActivityDelegate extends AppDelegate {

    private Button[] mTabs;
    private Fragment[] fragments;
    private WorkBenchFragment mWorkBenchFragment;
    private CRMFragment mCRMFragment;
    private TodoFragment mTodoFragment;
    private ProfileFragment mProfileFragment;

    @Override
    public void initWidget() {
        super.initWidget();
        mWorkBenchFragment = new WorkBenchFragment();
        mCRMFragment = new CRMFragment();
        mTodoFragment = new TodoFragment();
        mProfileFragment = new ProfileFragment();
        mTabs = new Button[]{get(R.id.btn_home), get(R.id.btn_crm),
                get(R.id.btn_todo)};
        fragments = new Fragment[]{mWorkBenchFragment, mCRMFragment, mTodoFragment};
        //一期只显示第二个界面
        mTabs[0].setSelected(true);
//        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mWorkBenchFragment).
//                add(R.id.fragment_container, mCRMFragment).hide(mCRMFragment).show(mWorkBenchFragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mWorkBenchFragment).show(mWorkBenchFragment).commit();
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_home;
    }


    public void showFragmentAndSelectTab(int index, int currentIndex) {
        if (currentIndex != index) {
            FragmentTransaction trx = getActivity().getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
    }

    @Override
    public boolean ifNeedHideToolBar() {
        return true;
    }
}
