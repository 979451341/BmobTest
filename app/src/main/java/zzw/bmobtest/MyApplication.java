package zzw.bmobtest;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by ASUS on 2018/6/3.
 */

public class MyApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
                ////设置appkey
                .setApplicationId("55635d865b1609c2afce397d6617b6b8")
                ////请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(15)
                ////文件分片上传时每片的大小（单位字节），默认512*1024
                //.setUploadBlockSize(1024*1024)
                ////文件的过期时间(单位为秒)：默认1800s
                //.setFileExpiration(2500)
                .build();
        Bmob.initialize(config);

        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
