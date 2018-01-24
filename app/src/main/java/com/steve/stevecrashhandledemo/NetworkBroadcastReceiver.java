package com.steve.stevecrashhandledemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * Created by steve on 2017/12/4.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private NetEvent evevt ;
    // 自定义接口
    public interface NetEvent {
        public void onNetChange(int netMobile);
    }

    public void setOnNetChange(NetEvent netEvent){
        this.evevt = netEvent;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetworkUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型
            if(evevt!=null){
                evevt.onNetChange(netWorkState);
            }
        }
    }


}
