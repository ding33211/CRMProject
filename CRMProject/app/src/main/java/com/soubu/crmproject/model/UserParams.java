package com.soubu.crmproject.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Created by dingsigang on 16-9-9.
 */
@Data
public class UserParams implements Serializable {

    //    String nickname;
    String username;
    String loginname;
    //    String department;
//    String position;
    String mobile;
    String email;
    String token;
    //    String employeeNumber;
//    String officeAddress;
//    String note;
    String pwd;
    String confirmPwd;
    //    Boolean  activated;
//    Date activatedAt;
    Date createdAt;
    Date updatedAt;
    String id;

    String companyId;//公司id
    String companyName;//公司名称
    String roleId;//角色id
    String roleName;//角色名称
    String type;//用户类型
    String area;//所属区域

}
