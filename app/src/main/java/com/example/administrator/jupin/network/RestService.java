package com.example.administrator.jupin.network;


import android.util.Log;

//import com.menda.dayijr.Constant;
//import com.menda.dayijr.manager.UserManager;
//import com.menda.dayijr.model.BizResult;
//import com.menda.dayijr.network.cookie.PersistentCookieJar;
//import com.menda.dayijr.network.cookie.cache.SetCookieCache;
//import com.menda.dayijr.network.cookie.persistence.SharedPrefsCookiePersistor;
//import com.menda.dayijr.util.NetworkUtils;
//import com.menda.dayijr.util.UIUtils;

import com.example.administrator.jupin.BuildConfig;
import com.example.administrator.jupin.Constant;
import com.example.administrator.jupin.model.BizResult;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求服务
 * Created by Administrator on 2016/3/24.
 */
public class RestService {

    private static final String TAG = RestService.class.getSimpleName();

    static {
        //设置okhttpclient
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) { //设置日志log
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        //设置cookie自动管理持久化
        //builder.cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(UIUtils.getAppContext())));
       // builder.retryOnConnectionFailure(true);

        //设置tk拦截器
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                if (UserManager.getInstance().getUser() != null){
//                    String tk = request.url().queryParameter("token");
//                    if (tk == null) {
//                        HttpUrl url = request.url().newBuilder().addQueryParameter("token", UserManager.getInstance().getUser().getCookie()).build();
//                        request =request.newBuilder().url(url).build();
//                    }
//                }
//
//                return chain.proceed(request);
//            }
//        });

        sRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())

                .baseUrl(Constant.BASE_URL)
                .build();

    }

    private static Retrofit sRetrofit;

    /**
     * 创建接口服务代理对象
     *
     * @param serviceCls
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> serviceCls) {
        return sRetrofit.create(serviceCls);
    }

    /**
     * 异步执行网络请求
     *
     * @param call
     * @param callback 请求回调
     * @param <T>
     */
    public static <T> void execute(Call<T> call, final Callback<T> callback) {
//        if (!NetworkUtils.isNetworkAvailable()) {
//            callback.onError(new IllegalStateException("网络不可用"));
//            return;
//        }
        if (call.isCanceled()) {
            callback.onCancelled();
            return;
        }
        //加入异步请求
        call.enqueue(new retrofit2.Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    final T body = response.body();
                    if (body instanceof BizResult) {
                        BizResult result = (BizResult) body;
//                        if ("300".equals(result.getStatus())){
//                            UserManager.getInstance().setUser(null);
//                            callback.onError(new LoginTimeoutException(result.getMessage()));
//                            return;
//                        }
                    }
                    //{"status" : "300", "message" : "用户未登录或超时退出，请重新登陆！"}


                    callback.onResponse(body);
                } else {
                    try {
                        //服务器返回状态码不在 code >= 200 && code < 300 之间
                        callback.onError(new IllegalStateException(response.errorBody().string()));
                    } catch (IOException ignore) {
                        callback.onError(ignore);
                        if (BuildConfig.DEBUG) {
                            ignore.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                //异常分发处理
                if (t instanceof SocketTimeoutException) {
                    callback.onError(new SocketTimeoutException("连接超时"));
                } else if (t instanceof ConnectException) {
                    callback.onError(new ConnectException("不能连接到服务器，请稍后再试"));
                }else if(t instanceof IOException){
                    if ("Canceled".equalsIgnoreCase(t.getMessage())){ //取消任务时才会有
                        callback.onCancelled();
                    }else{
                        callback.onError(new IOException("连接服务器失败，请稍后再试"));
                    }
                } else {
                    callback.onError(t);
                }

                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "onFailure: exception = " + t.getClass().getName());
                }
            }

        });
    }

    /**
     * 网络回调
     *
     * @param <T>
     */
    public static abstract class Callback<T> {
        /**
         * 请求成功。只有{@link Response#isSuccessful()}返回true
         *
         * @param result
         */
        public abstract void onResponse(T result);

        /**
         * 出现错误。请求过程出现任何错误都回调引方法
         *
         * @param throwable
         */
        public abstract void onError(Throwable throwable);

        /**
         * 请求取消
         */
        public void onCancelled() {
              if (BuildConfig.DEBUG) {
                  Log.d(TAG, "onCancelled() called with: " + "");
                }
        }
    }


}
