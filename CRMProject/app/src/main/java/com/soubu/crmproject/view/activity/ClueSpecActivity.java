package com.soubu.crmproject.view.activity;

import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.ClueActivityDelegate;
import com.soubu.crmproject.delegate.ClueSpecActivityDelegate;
import com.soubu.crmproject.model.FollowTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-8-22.
 */
public class ClueSpecActivity extends ActivityPresenter<ClueSpecActivityDelegate> {
    @Override
    protected Class<ClueSpecActivityDelegate> getDelegateClass() {
        return ClueSpecActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        FollowTest followTest = new FollowTest();
        List<FollowTest> list = new ArrayList<>();
        Date date = new Date();
        followTest.setTime(date.getTime());
        followTest.setFollowState(FollowTest.STATE_NOT_COMPLETE);
        followTest.setNeedRecord(true);
        list.add(followTest);
        followTest = new FollowTest();
        followTest.setFollowState(FollowTest.STATE_COMPLETE);
        followTest.setTime(date.getTime() + 360000000);
        list.add(followTest);
        followTest = new FollowTest();
        followTest.setTime(date.getTime() + 360000000 * 2);
        followTest.setFollowState(FollowTest.STATE_COMPLETE);
        list.add(followTest);
        followTest = new FollowTest();
        followTest.setTime(date.getTime() + 360000000 * 3);
        followTest.setNeedRecord(true);
        list.add(followTest);
        followTest = new FollowTest();
        followTest.setTime(date.getTime() + 360000000 * 4);
        list.add(followTest);
        followTest = new FollowTest();
        followTest.setTime(date.getTime() + 360000000 * 5);
        followTest.setFollowState(FollowTest.STATE_NOT_COMPLETE);
        list.add(followTest);
        list.add(followTest);
        list.add(followTest);
        viewDelegate.setIndicatorViewPagerAdapter(list);
    }
}
