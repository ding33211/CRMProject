package com.soubu.crmproject.base.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "Contact".
*/
public class ContactDao extends AbstractDao<Contact, Long> {

    public static final String TABLENAME = "Contact";

    /**
     * Properties of entity Contact.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Customer = new Property(2, String.class, "customer", false, "CUSTOMER");
        public final static Property Position = new Property(3, String.class, "position", false, "POSITION");
        public final static Property Phone = new Property(4, String.class, "phone", false, "PHONE");
        public final static Property Mobile = new Property(5, String.class, "mobile", false, "MOBILE");
        public final static Property Qq = new Property(6, String.class, "qq", false, "QQ");
        public final static Property Wechat = new Property(7, String.class, "wechat", false, "WECHAT");
        public final static Property Wangwang = new Property(8, String.class, "wangwang", false, "WANGWANG");
        public final static Property Department = new Property(9, String.class, "department", false, "DEPARTMENT");
        public final static Property CreatedAt = new Property(10, java.util.Date.class, "createdAt", false, "CREATED_AT");
        public final static Property UpdatedAt = new Property(11, java.util.Date.class, "updatedAt", false, "UPDATED_AT");
        public final static Property TouchedAt = new Property(12, java.util.Date.class, "touchedAt", false, "TOUCHED_AT");
        public final static Property TouchedCount = new Property(13, Integer.class, "touchedCount", false, "TOUCHED_COUNT");
        public final static Property Contact_id = new Property(14, String.class, "contact_id", false, "CONTACT_ID");
    };


    public ContactDao(DaoConfig config) {
        super(config);
    }
    
    public ContactDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"Contact\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"CUSTOMER\" TEXT," + // 2: customer
                "\"POSITION\" TEXT," + // 3: position
                "\"PHONE\" TEXT," + // 4: phone
                "\"MOBILE\" TEXT," + // 5: mobile
                "\"QQ\" TEXT," + // 6: qq
                "\"WECHAT\" TEXT," + // 7: wechat
                "\"WANGWANG\" TEXT," + // 8: wangwang
                "\"DEPARTMENT\" TEXT," + // 9: department
                "\"CREATED_AT\" INTEGER," + // 10: createdAt
                "\"UPDATED_AT\" INTEGER," + // 11: updatedAt
                "\"TOUCHED_AT\" INTEGER," + // 12: touchedAt
                "\"TOUCHED_COUNT\" INTEGER," + // 13: touchedCount
                "\"CONTACT_ID\" TEXT);"); // 14: contact_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"Contact\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Contact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String customer = entity.getCustomer();
        if (customer != null) {
            stmt.bindString(3, customer);
        }
 
        String position = entity.getPosition();
        if (position != null) {
            stmt.bindString(4, position);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(6, mobile);
        }
 
        String qq = entity.getQq();
        if (qq != null) {
            stmt.bindString(7, qq);
        }
 
        String wechat = entity.getWechat();
        if (wechat != null) {
            stmt.bindString(8, wechat);
        }
 
        String wangwang = entity.getWangwang();
        if (wangwang != null) {
            stmt.bindString(9, wangwang);
        }
 
        String department = entity.getDepartment();
        if (department != null) {
            stmt.bindString(10, department);
        }
 
        java.util.Date createdAt = entity.getCreatedAt();
        if (createdAt != null) {
            stmt.bindLong(11, createdAt.getTime());
        }
 
        java.util.Date updatedAt = entity.getUpdatedAt();
        if (updatedAt != null) {
            stmt.bindLong(12, updatedAt.getTime());
        }
 
        java.util.Date touchedAt = entity.getTouchedAt();
        if (touchedAt != null) {
            stmt.bindLong(13, touchedAt.getTime());
        }
 
        Integer touchedCount = entity.getTouchedCount();
        if (touchedCount != null) {
            stmt.bindLong(14, touchedCount);
        }
 
        String contact_id = entity.getContact_id();
        if (contact_id != null) {
            stmt.bindString(15, contact_id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Contact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String customer = entity.getCustomer();
        if (customer != null) {
            stmt.bindString(3, customer);
        }
 
        String position = entity.getPosition();
        if (position != null) {
            stmt.bindString(4, position);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(6, mobile);
        }
 
        String qq = entity.getQq();
        if (qq != null) {
            stmt.bindString(7, qq);
        }
 
        String wechat = entity.getWechat();
        if (wechat != null) {
            stmt.bindString(8, wechat);
        }
 
        String wangwang = entity.getWangwang();
        if (wangwang != null) {
            stmt.bindString(9, wangwang);
        }
 
        String department = entity.getDepartment();
        if (department != null) {
            stmt.bindString(10, department);
        }
 
        java.util.Date createdAt = entity.getCreatedAt();
        if (createdAt != null) {
            stmt.bindLong(11, createdAt.getTime());
        }
 
        java.util.Date updatedAt = entity.getUpdatedAt();
        if (updatedAt != null) {
            stmt.bindLong(12, updatedAt.getTime());
        }
 
        java.util.Date touchedAt = entity.getTouchedAt();
        if (touchedAt != null) {
            stmt.bindLong(13, touchedAt.getTime());
        }
 
        Integer touchedCount = entity.getTouchedCount();
        if (touchedCount != null) {
            stmt.bindLong(14, touchedCount);
        }
 
        String contact_id = entity.getContact_id();
        if (contact_id != null) {
            stmt.bindString(15, contact_id);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Contact readEntity(Cursor cursor, int offset) {
        Contact entity = new Contact( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // customer
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // position
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // phone
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // mobile
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // qq
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // wechat
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // wangwang
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // department
            cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)), // createdAt
            cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)), // updatedAt
            cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)), // touchedAt
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // touchedCount
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // contact_id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Contact entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCustomer(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPosition(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhone(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMobile(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setQq(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setWechat(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setWangwang(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDepartment(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCreatedAt(cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)));
        entity.setUpdatedAt(cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)));
        entity.setTouchedAt(cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)));
        entity.setTouchedCount(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setContact_id(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Contact entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Contact entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
