package com.soubu.crmproject.utils;

import android.content.Context;
import android.text.TextUtils;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.base.greendao.User;
import com.soubu.crmproject.base.greendao.UserDao;
import com.soubu.crmproject.model.UserParams;

import java.util.List;


public class UserManager {

    private static Context context;
    private static UserDao dao;
    private static User user;

    public static void init(Context _context) {
        context = _context;
    }

    public static boolean saveUserInfo(UserParams params) {
        try {
            if (dao == null) {
                dao = DBHelper.getInstance(context).getUserDao();
            }
            List<User> list = dao.queryBuilder().where(UserDao.Properties.User_id.eq(params.getId())).list();
            user = new User();
            if (list.size() > 0) {
                user = list.get(0);
            }
            user.setUsername(params.getUserName());
            user.setNickname(params.getNickName());
            user.setLoginname(params.getLoginName());
            user.setDepartment(params.getDepartment());
            user.setPosition(params.getPosition());
            user.setMobile(params.getMobile());
            user.setEmail(params.getEmail());
            user.setEmployeeNumber(params.getEmployeeNumber());
            user.setOfficeAddress(params.getOfficeAddress());
            user.setNote(params.getNote());
            user.setActivated(params.getActivated());
            user.setActivatedAt(params.getActivatedAt());
            user.setCreatedAt(params.getCreatedAt());
            user.setUpdatedAt(params.getUpdatedAt());
            user.setUser_id(params.getId());
            if (list.size() > 0) {
                dao.update(user);
            } else {
                dao.insert(user);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void clearUser() {
        if (dao != null && user != null) {
            dao.delete(user);
        }
    }

    public static boolean initUser() {
        dao = DBHelper.getInstance(context).getUserDao();
        String uid = CrmApplication.getContext().getUid();
        if (!TextUtils.isEmpty(uid)) {
            List<User> list = dao.queryBuilder().where(UserDao.Properties.User_id.eq(uid)).list();
            if (list.size() > 0) {
                user = list.get(0);
                return true;
            }
        }
        return false;
    }

    public static User getUser(){
        if(user == null){
          initUser();
        }
        return user;
    }
}
