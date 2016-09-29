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
    public static final int TYPE_HIGH_SEAS = 0x04;
    //还没有转成合同的商机
    public static final int TYPE_NO_TRANSFER_BUSINESS = 0x05;
    public static final int TYPE_FOLLOW_PLAN = 0x06;
    public static final int TYPE_FOLLOW_RECORD = 0x07;



    public static final int FROM_CLUE = 0x10;
    public static final int FROM_CUSTOMER = 0x11;
    public static final int FROM_BUSINESS_OPPORTUNITY = 0x12;
    public static final int FROM_CONTRACT = 0x13;
    public static final int FROM_ADD_SOMETHING_ACTIVITY = 0x14;
    public static final int FROM_CLUE_HIGH_SEAS = 0x15;
    public static final int FROM_CONTRACT_APPROVAL = 0x16;
    public static final int FROM_ADD_CONTRACT = 0x17;
    public static final int FROM_CUSTOMER_HIGH_SEAS = 0x18;


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

    public static final String FOLLOW_TYPE_PLAN = "PLAN";
    public static final String FOLLOW_TYPE_RECORD = "RECORD";


    public static final String SHARED_PREFERENCE_DEFAULT = "DEFAULT";


    public static final String EXTRA_CLUE = "CLUE";
    public static final String EXTRA_CUSTOMER = "CUSTOMER";
    public static final String EXTRA_BUSINESS_OPPORTUNITY = "BUSINESS_OPPORTUNITY";
    public static final String EXTRA_CONTRACT = "CONTRACT";
    public static final String EXTRA_CONTRACT_ID = "CONTRACT_ID";
    public static final String EXTRA_CONTACT = "CONTACT";
    public static final String EXTRA_CONTACT_ID = "CONTACT_ID";
    public static final String EXTRA_CONTACT_NAME = "CONTACT_NAME";


    public static final String EXTRA_FROM = "FROM";
    public static final String EXTRA_TYPE = "TYPE";
    public static final String EXTRA_ENTITY = "ENTITY";
    public static final String EXTRA_FROM_ADD_FOLLOW_HOME = "FROM_ADD_FOLLOW_HOME";
    public static final String EXTRA_FROM_ADD_FOLLOW = "FROM_ADD_FOLLOW";
    public static final String EXTRA_CUSTOMER_ID = "CUSTOMER_ID";
    public static final String EXTRA_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String EXTRA_BUSINESS_ID = "BUSINESS_ID";
    public static final String EXTRA_BUSINESS_NAME = "BUSINESS_NAME";

    public static final String EXTRA_TRANSFER = "TRANSFER";
    public static final String EXTRA_TRANSFER_NAME = "TRANSFER_NAME";
    public static final String EXTRA_PARAM_ID = "PARAM_ID";
    public static final String EXTRA_EMPLOYER_ID = "EMPLOYER_ID";
    public static final String EXTRA_EMPLOYER_NAME = "EMPLOYER_NAME";

    public static final String EXTRA_REMIND_TITLE = "REMIND_TITLE";
    public static final String EXTRA_REMIND_MESSAGE = "REMIND_MESSAGE";


    public static final int EVENT_BUS_KEY_ADD_BUSINESS = 0x00;
    public static final int EVENT_BUS_KEY_ADD_CONTRACT = 0x01;


    public static final String SP_KEY_TOKEN = "TOKEN";
    public static final String SP_KEY_USER_ID = "USER_ID";
    public static final String SP_KEY_USER_NAME = "USER_NAME";


}
