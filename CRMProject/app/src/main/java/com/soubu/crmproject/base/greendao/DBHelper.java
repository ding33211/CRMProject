package com.soubu.crmproject.base.greendao;

import android.content.Context;

import org.greenrobot.greendao.async.AsyncSession;

/**
 * Created by dingsigang on 16-8-2.
 */
public class DBHelper {

    private static DBHelper INSTANCE = null;

    /**
     * not thread-safe
     */
    public static DBHelper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new DBHelper(context);
        return INSTANCE;
    }

    private static final String DB_NAME = "crm.db";
    private DaoSession daoSession;
    private AsyncSession asyncSession;

    private DBHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
    }

    public ClientDao getClientDao() {
        return daoSession.getClientDao();
    }

    public UserDao getUserDao() {
        return daoSession.getUserDao();
    }

    public ProductDao getProductDao() {
        return daoSession.getProductDao();
    }

    public ClueDao getClueDao() {
        return daoSession.getClueDao();
    }

    public RemindDao getRemindDao() {
        return daoSession.getRemindDao();
    }

    public AsyncSession getAsyncSession() {
        return asyncSession;
    }
}
