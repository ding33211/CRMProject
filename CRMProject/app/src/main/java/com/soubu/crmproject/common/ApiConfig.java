package com.soubu.crmproject.common;


import com.soubu.crmproject.BuildConfig;

public class ApiConfig {

    public static int RELUST_OK = 0;
    public static int RELUST_FAIL = -1;

    public static boolean IS_PRODUCT_ENV = BuildConfig.IS_PRODUCT_ENV;   //是否是生产环境（正式服）

    public static String API_HOST = IS_PRODUCT_ENV ? "http://10.85.2.130/api/v1/" : "http://10.85.2.130/api/v1/";


}