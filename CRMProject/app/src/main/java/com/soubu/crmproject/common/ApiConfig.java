package com.soubu.crmproject.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.soubu.crmproject.BuildConfig;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.AppUtil;

public class ApiConfig {

    public static int RELUST_OK = 1;
    public static int RELUST_FAIL = 0;

    //    private static String token;
    private static String token;


    private static Context context;

    public static void initContext(Context context) {
        ApiConfig.context = context;
    }

    public static boolean IS_PRODUCT_ENV = BuildConfig.IS_PRODUCT_ENV;   //是否是生产环境（正式服）

    //    public static String API_HOST = IS_PRODUCT_ENV ? "http://114.55.75.5:80/api/v1/" : "http://10.85.2.130/api/v1/";
    public static String API_HOST = IS_PRODUCT_ENV ? "http://114.55.75.5:80/api/v1/" : "http://114.55.75.5:80/api/v1/";

    public static String getToken() {
        if (TextUtils.isEmpty(token)) {
            SharedPreferences sp = AppUtil.getDefaultSharedPreference(context);
            token = sp.getString(Contants.SP_KEY_TOKEN, null);
        }
        return token;
    }

    public static void setToken(String token) {
        ApiConfig.token = token;
        SharedPreferences sp = AppUtil.getDefaultSharedPreference(context);
        sp.edit().putString(Contants.SP_KEY_TOKEN, token).commit();
    }
}
