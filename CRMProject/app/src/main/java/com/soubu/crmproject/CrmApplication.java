package com.soubu.crmproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.text.TextUtils;

import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.sdk.eventbus.MyEventBusIndex;
import com.soubu.crmproject.utils.AppUtil;
import com.soubu.crmproject.utils.CrashHandler;
import com.soubu.crmproject.utils.PhoneUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.EventBus;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * Created by dingsigang on 16-8-1.
 */
public class CrmApplication extends Application {
    private static CrmApplication instance;

    public static String cacheDir = "";
//    private RefWatcher refWatcher;

    //token以及uid做成全局参数
    private static String mToken;
    private static String mUid;
    private static String mName;
    private ActivityLifecycle activityLifecycle;

//    private UserDao mUserDao;

    @Override
    public void onCreate() {
        super.onCreate();
        //Log机制
        instance = (CrmApplication) getApplicationContext();
        cacheDir = PhoneUtil.getCacheDir(instance);
//        mUserDao = DBHelper.getInstance(instance).getUserDao();
        //初始化crash输出工具
        //具体决策需要商议
        CrashHandler.getInstance().init(instance);
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        ShowWidgetUtil.register(this);
        initActivityCycle();
    }


    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll()  //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    // 获取ApplicationContext
    public static CrmApplication getContext() {
        return instance;
    }

    public String getToken() {
        if (TextUtils.isEmpty(mToken)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
            mToken = sp.getString(Contants.SP_KEY_TOKEN, null);
        }
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
        sp.edit().putString(Contants.SP_KEY_TOKEN, token).commit();
    }

    public String getUid() {
        if (TextUtils.isEmpty(mUid)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
            mUid = sp.getString(Contants.SP_KEY_USER_ID, null);
        }
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
        sp.edit().putString(Contants.SP_KEY_USER_ID, uid).commit();
    }

    public void setName(String name){
        mName = name;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
        sp.edit().putString(Contants.SP_KEY_USER_NAME, name).commit();
    }

    public String getName(){
        if (TextUtils.isEmpty(mName)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(instance);
            mName = sp.getString(Contants.SP_KEY_USER_NAME, null);
        }
        return mName;
    }

    private void initActivityCycle() {
        activityLifecycle = new ActivityLifecycle();
        this.registerActivityLifecycleCallbacks(activityLifecycle);//注册
    }

    public Context getNowContext() {
        return activityLifecycle.getNowContext();
    }

    public void finishAllActivity() {
        activityLifecycle.finishAllActivity();
    }
}
