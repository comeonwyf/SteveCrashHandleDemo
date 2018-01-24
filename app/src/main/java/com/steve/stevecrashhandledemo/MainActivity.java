package com.steve.stevecrashhandledemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import com.steve.stevecrashhandledemo.model.CrashException;

/**
 * @author steve
 * @date 2018/1/24
 */
public class MainActivity extends AppCompatActivity {

    private NetworkBroadcastReceiver mReceiver;
    private boolean doing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerNetworkReceiver();
    }


    public void onClick(View view){

        if(view.getId()== R.id.tv1){
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        }

        if(view.getId()==R.id.tv2){
            String test = null;
            test.length();
        }


    }

    private void registerNetworkReceiver() {
        mReceiver = new NetworkBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver,filter);
        mReceiver.setOnNetChange(new NetworkBroadcastReceiver.NetEvent() {
            @Override
            public void onNetChange(int netMobile) {
                if(netMobile!=-1){
                    Log.e("print", "网络变化：可用！ ");
                    //网络可以用的时候，看看数据库中是否有需要发送的错误日志
                    if(doing){
                      return;
                    }
                    doing = true;
                    List<CrashException> crashExceptionList = CrashExceptionHelper.getCrashExceptionList();
                    if(crashExceptionList!=null && crashExceptionList.size()!=0){
                        for(CrashException crashException : crashExceptionList){
                            crashException.setSendStat(CrashException.TYPE_SENDING);
                            SendExceptionManager.getInstance().sendToServer(crashException);
                        }

                    }
                    doing = false;
                }else {
                    Log.e("print", "网络变化：不可用！ ");
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mReceiver!=null){
           unregisterReceiver(mReceiver);
        }
    }
}
