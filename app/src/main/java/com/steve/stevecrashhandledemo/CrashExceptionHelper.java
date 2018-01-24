package com.steve.stevecrashhandledemo;


import java.util.List;

import com.steve.stevecrashhandledemo.dao.CrashExceptionDao;
import com.steve.stevecrashhandledemo.model.CrashException;

/**
 * Created by steve on 2017/8/23.
 */

public class CrashExceptionHelper {
    private static CrashExceptionDao dao = CrashApp.getDaoInstance().getCrashExceptionDao();

    public static boolean addCrashException(final CrashException crashException){
        boolean flag = false;
        if(crashException==null){
           return flag;
        }
        flag = dao.insertOrReplace(crashException) == -1 ? false : true;
        return flag;
    }

    public static boolean addCrashExceptionLisr(final List<CrashException> crashExceptionlist){
        boolean flag = false;
        if(crashExceptionlist==null){
            return flag;
        }
        try {
            CrashApp.getDaoInstance().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (CrashException crashException : crashExceptionlist) {
                        dao.insertOrReplace(crashException);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean deleteCrashException(CrashException crashException){
        boolean flag = false;
        if(crashException==null){
            return flag;
        }
        dao.delete(crashException);
        flag =true;
        return flag;
    }

    public static boolean deleteAll(){
        boolean flag = false;
        dao.deleteAll();
        flag =true;
        return flag;
    }

    public static List<CrashException> getCrashExceptionList(){
        List<CrashException> list = dao.queryBuilder().where(CrashExceptionDao.Properties.SendStat.eq(CrashException.TYPE_TOSEND)).orderAsc(CrashExceptionDao.Properties.CrashId).list();
        return list;
    }



}
