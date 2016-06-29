package com.example.administrator.jupin.network.api;

import com.example.administrator.jupin.model.ActivityIndexModel;
import com.example.administrator.jupin.model.AreaModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 代理商账户资金操作相关api
 * Created by Administrator on 2016/6/8.
 */
public interface ActivityService {
    /**
     * 活动主页
     *
     * @return
     */
    @FormUrlEncoded
    @POST("actApp/specialIndex.app")
    Call<ActivityIndexModel> activity(@Field("longitude")float longitude,
                                      @Field("latitude")float latitude,
                                      @Field("startPage")int startPage,
                                      @Field("pageSize")int pageSize,
                                      @Field("actType")String actType,
                                      @Field("distance")String distance,
                                      @Field("time")String time);

//    /**
//     * 活动主页
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("actApp/specialIndex.app")
//    Call<ActivityIndexModel> activityWithType(@Field("longitude")float longitude, @Field("latitude")float latitude, @Field("startPage")int startPage, @Field("pageSize")int pageSize,@Field("actType")String actType);
//
//    /**
//     * 活动主页
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("actApp/specialIndex.app")
//    Call<ActivityIndexModel> activityWithDistance(@Field("longitude")float longitude, @Field("latitude")float latitude, @Field("startPage")int startPage, @Field("pageSize")int pageSize,@Field("distance")String distance);
//
//    /**
//     * 活动主页
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("actApp/specialIndex.app")
//    Call<ActivityIndexModel> activityWithTime(@Field("longitude")float longitude, @Field("latitude")float latitude, @Field("startPage")int startPage, @Field("pageSize")int pageSize,@Field("time")String time);
//
//    /**
//     * 活动主页
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("actApp/specialIndex.app")
//    Call<ActivityIndexModel> activityWithArea(@Field("longitude")float longitude, @Field("latitude")float latitude, @Field("startPage")int startPage, @Field("pageSize")int pageSize,@Field("area")String area);


    /**
     * 获取所有地区
     *
     * @return
     */
    @FormUrlEncoded
    @POST("homeApp/getAllArea.app")
    Call<AreaModel> area(@Field("token")String token);

    /**
     * 获取所有地区
     *
     * @return
     */
    @FormUrlEncoded
    @POST("actApp/specialDetail.app")
    Call<String> detail(@Field("id")String id,@Field("token")String token,@Field("share")String share);
}
