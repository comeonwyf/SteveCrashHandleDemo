package com.steve.stevecrashhandledemo.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.steve.stevecrashhandledemo.model.CrashException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CRASH_EXCEPTION".
*/
public class CrashExceptionDao extends AbstractDao<CrashException, Long> {

    public static final String TABLENAME = "CRASH_EXCEPTION";

    /**
     * Properties of entity CrashException.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CrashId = new Property(0, Long.class, "crashId", true, "_id");
        public final static Property CrashTime = new Property(1, String.class, "crashTime", false, "CRASH_TIME");
        public final static Property AppName = new Property(2, String.class, "appName", false, "APP_NAME");
        public final static Property AppVersion = new Property(3, String.class, "appVersion", false, "APP_VERSION");
        public final static Property OSVersion = new Property(4, String.class, "OSVersion", false, "OSVERSION");
        public final static Property MobileBrand = new Property(5, String.class, "mobileBrand", false, "MOBILE_BRAND");
        public final static Property MobileModel = new Property(6, String.class, "mobileModel", false, "MOBILE_MODEL");
        public final static Property CrashExceptionInfor = new Property(7, String.class, "crashExceptionInfor", false, "CRASH_EXCEPTION_INFOR");
        public final static Property SendStat = new Property(8, int.class, "sendStat", false, "SEND_STAT");
    }


    public CrashExceptionDao(DaoConfig config) {
        super(config);
    }
    
    public CrashExceptionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CRASH_EXCEPTION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: crashId
                "\"CRASH_TIME\" TEXT," + // 1: crashTime
                "\"APP_NAME\" TEXT," + // 2: appName
                "\"APP_VERSION\" TEXT," + // 3: appVersion
                "\"OSVERSION\" TEXT," + // 4: OSVersion
                "\"MOBILE_BRAND\" TEXT," + // 5: mobileBrand
                "\"MOBILE_MODEL\" TEXT," + // 6: mobileModel
                "\"CRASH_EXCEPTION_INFOR\" TEXT," + // 7: crashExceptionInfor
                "\"SEND_STAT\" INTEGER NOT NULL );"); // 8: sendStat
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CRASH_EXCEPTION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CrashException entity) {
        stmt.clearBindings();
 
        Long crashId = entity.getCrashId();
        if (crashId != null) {
            stmt.bindLong(1, crashId);
        }
 
        String crashTime = entity.getCrashTime();
        if (crashTime != null) {
            stmt.bindString(2, crashTime);
        }
 
        String appName = entity.getAppName();
        if (appName != null) {
            stmt.bindString(3, appName);
        }
 
        String appVersion = entity.getAppVersion();
        if (appVersion != null) {
            stmt.bindString(4, appVersion);
        }
 
        String OSVersion = entity.getOSVersion();
        if (OSVersion != null) {
            stmt.bindString(5, OSVersion);
        }
 
        String mobileBrand = entity.getMobileBrand();
        if (mobileBrand != null) {
            stmt.bindString(6, mobileBrand);
        }
 
        String mobileModel = entity.getMobileModel();
        if (mobileModel != null) {
            stmt.bindString(7, mobileModel);
        }
 
        String crashExceptionInfor = entity.getCrashExceptionInfor();
        if (crashExceptionInfor != null) {
            stmt.bindString(8, crashExceptionInfor);
        }
        stmt.bindLong(9, entity.getSendStat());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CrashException entity) {
        stmt.clearBindings();
 
        Long crashId = entity.getCrashId();
        if (crashId != null) {
            stmt.bindLong(1, crashId);
        }
 
        String crashTime = entity.getCrashTime();
        if (crashTime != null) {
            stmt.bindString(2, crashTime);
        }
 
        String appName = entity.getAppName();
        if (appName != null) {
            stmt.bindString(3, appName);
        }
 
        String appVersion = entity.getAppVersion();
        if (appVersion != null) {
            stmt.bindString(4, appVersion);
        }
 
        String OSVersion = entity.getOSVersion();
        if (OSVersion != null) {
            stmt.bindString(5, OSVersion);
        }
 
        String mobileBrand = entity.getMobileBrand();
        if (mobileBrand != null) {
            stmt.bindString(6, mobileBrand);
        }
 
        String mobileModel = entity.getMobileModel();
        if (mobileModel != null) {
            stmt.bindString(7, mobileModel);
        }
 
        String crashExceptionInfor = entity.getCrashExceptionInfor();
        if (crashExceptionInfor != null) {
            stmt.bindString(8, crashExceptionInfor);
        }
        stmt.bindLong(9, entity.getSendStat());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CrashException readEntity(Cursor cursor, int offset) {
        CrashException entity = new CrashException( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // crashId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // crashTime
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // appName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // appVersion
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // OSVersion
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // mobileBrand
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mobileModel
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // crashExceptionInfor
            cursor.getInt(offset + 8) // sendStat
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CrashException entity, int offset) {
        entity.setCrashId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCrashTime(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAppName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAppVersion(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setOSVersion(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMobileBrand(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMobileModel(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCrashExceptionInfor(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSendStat(cursor.getInt(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CrashException entity, long rowId) {
        entity.setCrashId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CrashException entity) {
        if(entity != null) {
            return entity.getCrashId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CrashException entity) {
        return entity.getCrashId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}