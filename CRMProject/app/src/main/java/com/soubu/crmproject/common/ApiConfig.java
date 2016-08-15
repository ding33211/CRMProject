package com.soubu.crmproject.common;


import com.soubu.crmproject.BuildConfig;

public class ApiConfig {

    public static boolean IS_PRODUCT_ENV = BuildConfig.IS_PRODUCT_ENV;   //是否是生产环境（正式服）

    public static String API_HOST = IS_PRODUCT_ENV ? "http://www.isoubu.net/soubu/Api/" : "http://www.isoubu.cn/apiTest/Api/";

}
