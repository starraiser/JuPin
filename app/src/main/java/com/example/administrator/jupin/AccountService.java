package com.example.administrator.jupin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 代理商账户资金操作相关api
 * Created by Administrator on 2016/6/8.
 */
public interface AccountService {
    /**
     * 资产总览
     *
     * @return
     */
    @FormUrlEncoded
    @POST("actApp/specialIndex.app")
    Call<BizResult> fund(@Field("longitude")float longitude,@Field("latitude")float latitude,@Field("startPage")int startPage,@Field("pageSize")int pageSize);

    /**
     * 资产总览
     *
     * @return
     */
    @FormUrlEncoded
    @POST("homeApp/getAllArea.app")
    Call<BizResult> area(@Field("f")String none);
}
