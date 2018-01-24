package com.steve.stevecrashhandledemo.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author steve
 * @date 2018/1/17
 */
@Entity
public class CrashException {
    public static final int TYPE_TOSEND = 1;
    public static final int TYPE_SENDING = 2;

    @Id
    private Long crashId;
    private String crashTime;
    private String appName;
    private String appVersion;
    private String OSVersion;
    private String mobileBrand;
    private String mobileModel;
    private String crashExceptionInfor;
    private int sendStat = 1;//1表示待发送，2表示正在发送



    @Generated(hash = 1063750127)
    public CrashException(Long crashId, String crashTime, String appName,
            String appVersion, String OSVersion, String mobileBrand,
            String mobileModel, String crashExceptionInfor, int sendStat) {
        this.crashId = crashId;
        this.crashTime = crashTime;
        this.appName = appName;
        this.appVersion = appVersion;
        this.OSVersion = OSVersion;
        this.mobileBrand = mobileBrand;
        this.mobileModel = mobileModel;
        this.crashExceptionInfor = crashExceptionInfor;
        this.sendStat = sendStat;
    }

    @Generated(hash = 2138202335)
    public CrashException() {
    }



    public void setCrashExceptionInfor(String crashExceptionInfor) {
        this.crashExceptionInfor = crashExceptionInfor;
    }

    public static Long creatCrashId(){
        return (Long)((System.currentTimeMillis()+17)/3);
    }

    public Long getCrashId() {
        return this.crashId;
    }

    public void setCrashId(Long crashId) {
        this.crashId = crashId;
    }

    public String getCrashTime() {
        return this.crashTime;
    }

    public void setCrashTime(String crashTime) {
        this.crashTime = crashTime;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOSVersion() {
        return this.OSVersion;
    }

    public void setOSVersion(String OSVersion) {
        this.OSVersion = OSVersion;
    }

    public String getMobileBrand() {
        return this.mobileBrand;
    }

    public void setMobileBrand(String mobileBrand) {
        this.mobileBrand = mobileBrand;
    }

    public String getMobileModel() {
        return this.mobileModel;
    }

    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel;
    }

    public String getCrashExceptionInfor() {
        return this.crashExceptionInfor;
    }

    public int getSendStat() {
        return this.sendStat;
    }

    public void setSendStat(int sendStat) {
        this.sendStat = sendStat;
    }

}
