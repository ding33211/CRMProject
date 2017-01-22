package com.soubu.crmproject.model;

import com.soubu.crmproject.server.ObjectToMapInterface;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Created by dingsigang on 16-8-26.
 */
@Data
public class CustomerParams extends ObjectToMapInterface implements Serializable, Cloneable{

    String id; //id标识
    Integer uid; //APP对应用户id
    String name; //客户名称
    String property; //客户属性
    String contactName; // 联系人名称
    String city; //所在城市
    String address; //详细地址
    String buyOrdersCount; //买入订单数
    String buyOrdersAmount; //买入订单总额
    String sellOrdersCount; //买入订单数
    String sellOrdersAmount; //买入订单总额
    String recordsCount; //有效拜访记录数
    Date registeredAt; //用户注册时间
    Date createdAt; //创建时间
    Date updatedAt; //更新时间

    String size; //人员规模
    String position; //联系人职位
    String mobile; //联系人手机号
    String email; //邮箱
    String level; //级别
    String province; //省份
    String area; //区域
    String businessScope; //主营项目
    Boolean isLocked; //是否锁定
    Boolean isVerified; //手机收否验证
    Boolean isVip; //是否vip客户
    Boolean isKa; //是否ka客户
    String certificationType; //认证类型
    String roleType; //用户类型
    String buyerRole; //采购商类型（只有当用户类型为采购时显示该字段）
    String providerRole; //供应商类型（只有当用户类型为供应时显示该字段）

    public CustomerParams clone() throws CloneNotSupportedException{
        return (CustomerParams)super.clone();
    }
}
