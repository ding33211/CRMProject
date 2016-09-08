package com.soubu.crmproject.model;

import com.soubu.crmproject.R;

/**
 * 全局常量
 * Created by dingsigang on 16-8-17.
 */
public class Contants {

    /**
     * 下拉刷新控件变化的四个颜色
     */
    public static final int[] colors = new int[] {
            R.color.colorPrimary, R.color.subtitle_grey,
            R.color.colorPrimary, R.color.subtitle_grey
    };

    public static final int TYPE_CLUE = 0x00;
    public static final int TYPE_CUSTOMER = 0x01;
    public static final int TYPE_BUSINESS_OPPORTUNITY = 0x02;
    public static final int TYPE_CONTRACT = 0x03;


    public static final int FROM_CLUE = 0x00;
    public static final int FROM_CUSTOMER = 0x01;
    public static final int FROM_BUSINESS_OPPORTUNITY = 0x02;
    public static final int FROM_CONTRACT = 0x03;
    public static final int FROM_ADD_SOMETHING_ACTIVITY = 0x04;


    //在跟进总表
    public static final int IN_FOLLOW_HOME = 0x00;
    //在四大模块的首页
    public static final int IN_4_HOME = 0x01;


    public static final String FOLLOW_TYPE_OPPORTUNITY = "OPPORTUNITY";
    public static final String FOLLOW_TYPE_CUSTOMER = "CUSTOMER";
    public static final String FOLLOW_TYPE_DEAL = "DEAL";
    public static final String FOLLOW_TYPE_CONTRACT = "CONTRACT";

    public static final String BACK_SALES_TYPE_PLAN = "PLAN";
    public static final String BACK_SALES_TYPE_DETAIL = "DETAIL";


    public static final String SHARED_PREFERENCE_DEFAULT = "DEFAULT";


    public static final String EXTRA_CLUE = "CLUE";
    public static final String EXTRA_CUSTOMER = "CUSTOMER";
    public static final String EXTRA_BUSINESS_OPPORTUNITY = "BUSINESS_OPPORTUNITY";
    public static final String EXTRA_CONTRACT = "CONTRACT";
    public static final String EXTRA_CONTRACT_ID = "CONTRACT_ID";
    public static final String EXTRA_CONTACT = "CONTACT";



    public static final String EXTRA_FROM = "FROM";
    public static final String EXTRA_TYPE = "TYPE";
    public static final String EXTRA_ENTITY = "ENTITY";
    public static final String EXTRA_FROM_ADD_FOLLOW_HOME = "FROM_ADD_FOLLOW_HOME";
    public static final String EXTRA_FROM_ADD_FOLLOW = "FROM_ADD_FOLLOW";
    public static final String EXTRA_CUSTOMER_ID = "CUSTOMER_ID";
    public static final String EXTRA_CUSTOMER_NAME = "CUSTOMER_NAME";



}
