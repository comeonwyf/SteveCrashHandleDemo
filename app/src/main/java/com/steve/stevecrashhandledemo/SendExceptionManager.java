package com.steve.stevecrashhandledemo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import com.steve.stevecrashhandledemo.model.CrashException;


/**
 * @author steve
 * @date 2018/1/17
 */

public class SendExceptionManager {

    /**
     * 单例
     */
    private static SendExceptionManager mSendExceptionManager;
    private static ScheduledExecutorService scheduledThreadPool;
    public static Context applicationContext;

    private SendExceptionManager() {
        //创建定长的5个线程
        scheduledThreadPool = Executors.newScheduledThreadPool(5);

    }

    public static synchronized SendExceptionManager getInstance() {

        if (mSendExceptionManager == null) {
            synchronized (SendExceptionManager.class) {
                if (mSendExceptionManager == null) {
                    mSendExceptionManager = new SendExceptionManager();
                }
            }
        }
        return mSendExceptionManager;
    }


    //发送到邮箱
    public void sendToEmail(final CrashException crashException) {

        if (scheduledThreadPool == null) {
            scheduledThreadPool = Executors.newScheduledThreadPool(5);
        }

        scheduledThreadPool.execute(new Runnable() {
            @Override
            public void run() {

                //账号密码
                //用此邮箱来发送邮件（账号：********@qq.com , 密码：（开启POP3/SMTP服务的授权码））
                Mail mail = new Mail("填账号", "填授权码");

                //接受者邮箱 可以是多个

                mail.set_to(new String[]{"******@qq.com","******@hotmail.com"});
                //邮件来源
                mail.set_from("******@qq.com");
                //设置主题标题
                mail.set_subject(getApplicationName(CrashApp.getInstance()) + "错误日志");
                mail.setBody(crashException.toString());

                try {
                    if (mail.send()) {
                        Log.e("crashInfor: ", "发送邮件成功");
                    } else {
                        Log.e("crashInfor:", "发送邮件失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    //发送到服务器（Ion）
    public void sendToServer(final CrashException crashException) {
        String device = crashException.getMobileBrand()+"--"+crashException.getMobileModel()+"--"+crashException.getOSVersion();
        Ion.with(CrashApp.getInstance())
                .load("url")//填写后台的的地址.
                .setTimeout(3000)
                .setBodyParameter("note", crashException.getCrashExceptionInfor())
                .setBodyParameter("device",device)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        crashException.setSendStat(CrashException.TYPE_TOSEND);
                        if (e != null) {
                            CrashExceptionHelper.addCrashException(crashException);
                        }
                        if (result != null) {
                            Log.e("crashInfor:", "崩溃信息上传成功:"+result);
                            Boolean stat = result.get("success").getAsBoolean();
                            if(stat){
                                CrashExceptionHelper.deleteCrashException(crashException);
                            }else {
                                CrashExceptionHelper.addCrashException(crashException);
                            }
                        } else {
                            Log.e("crashInfor:", "崩溃信息上传失败");
                            CrashExceptionHelper.addCrashException(crashException);
                        }

                    }
                });

    }

//    //发送到服务器（http原始）
//    public void sendToServerByHttp(final CrashException crashException) {
//        try {
//            URL url = new URL("http://192.168.31.139:6010/log/exception/add");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(3000);
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//
//            // 设置维持长连接
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            // 设置文件字符集:
//            conn.setRequestProperty("Charset", "UTF-8");
//
//            //转换为字节数组
//            String json = new Gson().toJson(crashException);
//            json = "param="+json;
//
////            String charset = "UTF-8";
////            String urlParameters = String.format(
////                    "appCode=%s&userId=%s&note=%s&device=%s",
////                    URLEncoder.encode(fileName, charset),
////                    URLEncoder.encode(param02, charset),
////                    URLEncoder.encode(param03, charset),
////                    URLEncoder.encode(param05, charset),
////                    URLEncoder.encode(param08, charset),
////                    URLEncoder.encode(param11, charset));
////
////            Log.e("print", "sendToServerByHttp: "+json );
//
//            byte[] data = (json.toString()).getBytes();
//
//            // 设置文件长度
//            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
//
//            // 设置文件类型:
////            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Content-Type", "multipart/form-data");
//
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//            // 开始连接请求
//            conn.connect();
//            OutputStream out = conn.getOutputStream();
//
//            // 写入请求的字符串
//            out.write(data);
//            out.flush();
//            out.close();
//
//            Log.e("print", "返回的状态码: "+conn.getResponseCode() );
//
//            // 请求返回的状态
//            if (conn.getResponseCode() == 200) {
//                Log.e("print", "连接成功" );
//                // 请求返回的数据
//                InputStream in = conn.getInputStream();
//                String a = null;
//                try {
//                    byte[] data1 = new byte[in.available()];
//                    in.read(data1);
//                    // 转成字符串
//                    a = new String(data1);
//                    Log.e("print", "返回结果 "+a );
//                } catch (Exception e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//            } else {
//                Log.e("print", "连接失败 ");
//            }
//
//        } catch (MalformedURLException e) {
//            Log.e("print", "连接失败2 ");
//            e.printStackTrace();
//        }catch (IOException e) {
//            Log.e("print", "连接失败3 ");
//            e.printStackTrace();
//        }
//
//
//
//    }



    private static String getApplicationName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        String name = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(
                    context.getApplicationInfo().packageName, 0);
            name = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (final PackageManager.NameNotFoundException e) {
            String[] packages = context.getPackageName().split(".");
            name = packages[packages.length - 1];
        }
        return name;
    }
}
