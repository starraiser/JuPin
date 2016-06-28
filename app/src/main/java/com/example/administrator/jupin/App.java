package com.example.administrator.jupin;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2016/3/24.
 */
public class App extends Application {
    public static App sContext;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext=this;

        //yn启动内存检测
          if (BuildConfig.DEBUG) {
              enabledStrictMode();
          }
      refWatcher =  LeakCanary.install(this);

    }

    public static RefWatcher getRefWatcher() {
        return sContext.refWatcher;
    }

    //启用严格模式
    private void enabledStrictMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }

    }


}
