package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.view.AppDelegate;

/**
 * Created by dingsigang on 16-8-2.
 */
public class SplashActivityDelegate extends AppDelegate {

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_splash;
    }

    //欢迎界面需要全屏
    @Override
    public boolean ifNeedFullScreen() {
        return true;
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
