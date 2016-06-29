package com.example.administrator.jupin.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关工具包
 * Created by Administrator on 2016/3/24.
 */
public final class NetworkUtils {
    private NetworkUtils(){}

    /**
     * 检查网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) UIUtils.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }
}
