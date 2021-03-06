package com.steve.stevecrashhandledemo.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.steve.stevecrashhandledemo.model.CrashException;

import com.steve.stevecrashhandledemo.dao.CrashExceptionDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig crashExceptionDaoConfig;

    private final CrashExceptionDao crashExceptionDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        crashExceptionDaoConfig = daoConfigMap.get(CrashExceptionDao.class).clone();
        crashExceptionDaoConfig.initIdentityScope(type);

        crashExceptionDao = new CrashExceptionDao(crashExceptionDaoConfig, this);

        registerDao(CrashException.class, crashExceptionDao);
    }
    
    public void clear() {
        crashExceptionDaoConfig.clearIdentityScope();
    }

    public CrashExceptionDao getCrashExceptionDao() {
        return crashExceptionDao;
    }

}
