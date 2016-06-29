package com.example.administrator.jupin.Activity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jupin.adapter.ActivityAdapter;
import com.example.administrator.jupin.adapter.DistanceAdapter;
import com.example.administrator.jupin.adapter.GirdDropDownAdapter;
import com.example.administrator.jupin.R;
import com.example.administrator.jupin.network.RestService;
import com.example.administrator.jupin.adapter.TimeAdapter;
import com.example.administrator.jupin.network.api.ActivityService;
import com.example.administrator.jupin.model.ActivityIndexModel;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements GirdDropDownAdapter.ScreenCallback,TimeAdapter.TimeScreenCallback,DistanceAdapter.DistanceScreenCallback{

    ListView listView;

    //筛选菜单
    private String headers[] = {"全部活动","距离","时间"};
    private String activities[] = {"全部活动", "校园招聘", "教育培训"};
    private String distance[] = {"不限", "10公里以内", "20公里以内", "30公里以内"};
    private String time[] = {"不限", "3天以内", "5天以内","7天以内"};

    //筛选菜单适配器
    private GirdDropDownAdapter cityAdapter;
    private DistanceAdapter ageAdapter;
    private TimeAdapter sexAdapter;

    private DropDownMenu mDropDownMenu;

    //筛选条件
    private int actPosition = 0;
    private int disPosition = 0;
    private int timePosition = 0;

    private List<View> popupViews = new ArrayList<>();

    private TextView curLocation;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private BDLocation bdLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curLocation = (TextView)findViewById(R.id.location);
        curLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,ProvinceActivity.class),1);
            }
        });
        //定位
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        initView();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

//    private void showPopupWindow(View view, List<String> listdata, final int type){  // 弹窗
//        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_window,null);
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
//        WindowManager.LayoutParams params=MainActivity.this.getWindow().getAttributes();
//        params.alpha=0.7f;
//
//        MainActivity.this.getWindow().setAttributes(params);
//        //mask.setVisibility(View.VISIBLE);
//
//        WindowManager wm = this.getWindowManager();
//        int width = wm.getDefaultDisplay().getWidth();
//        final ListView listView = (ListView)contentView.findViewById(R.id.grouplist);
//
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < listdata.size(); i++) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("groupName", listdata.get(i));
//            list.add(map);
//        }
//
//        int tmp = 0;
//        if (type == 0) {
//            tmp = actPosition;
//        }
//        if (type == 1) {
//            tmp = disPosition;
//        }
//        if (type == 2) {
//            tmp = timePosition;
//        }
//        final FilterAdapter adapter = new FilterAdapter(MainActivity.this,listdata,tmp);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(type == 0){
//                    actPosition = position;
//                }
//                if(type == 1){
//                    disPosition = position;
//                }
//                if(type == 2){
//                    timePosition = position;
//                }
//                listView.setAdapter(null);
//                popupWindow.dismiss();
//            }
//        });
//
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams params=MainActivity.this.getWindow().getAttributes();
//                params.alpha=1f;
//                listView.setAdapter(null);
//                //mask.setVisibility(View.GONE);
//                MainActivity.this.getWindow().setAttributes(params);
//
//            }
//        });
//
//        choose_type.getLocationOnScreen(location);
//
//        popupWindow.setTouchable(true);  // 设置弹出窗口
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fff9f9")));
//        popupWindow.setWidth(width);
//        popupWindow.showAtLocation(choose_type, Gravity.NO_GRAVITY,location[0] - choose_type.getWidth(), location[1] + choose_type.getMeasuredHeight()+(choose_type.getHeight()-choose_type.getHeight())/2);
//        popupWindow.showAsDropDown(view);
//
//
//    }

    private void initView() {
        mDropDownMenu = (DropDownMenu)findViewById(R.id.dropDownMenu);
        //init city menu
        final ListView cityView = new ListView(this);
        cityAdapter = new GirdDropDownAdapter(this, Arrays.asList(activities),this);
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new DistanceAdapter(this, Arrays.asList(distance),this);
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new TimeAdapter(this, Arrays.asList(time),this);
        sexView.setAdapter(sexAdapter);

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        //popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : activities[position]);
                mDropDownMenu.closeMenu();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : distance[position]);
                mDropDownMenu.closeMenu();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : time[position]);
                mDropDownMenu.closeMenu();
            }
        });

        //init context view
        listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //虚拟数据
        ActivityIndexModel model = new ActivityIndexModel();
        ActivityIndexModel.Act act = model.new Act();
        act.setName("活动");
        act.setArea("广东东莞");
        act.setBeginTime("2012");
        act.setDistance("10km");
        act.setIsCost("0");
        act.setJoin_nums(50);
        act.setActNums(100);

        ActivityIndexModel.Act act2 = model.new Act();
        act2.setName("活动2");
        act2.setArea("广东东莞");
        act2.setBeginTime("2012");
        act2.setDistance("10km");
        act2.setIsCost("1");
        act2.setJoin_nums(50);
        act2.setActNums(0);

        ActivityIndexModel.Act act3 = model.new Act();
        act3.setName("活动2");
        act3.setArea("广东东莞");
        act3.setBeginTime("2012");
        act3.setDistance("10km");
        act3.setIsCost("1");
        act3.setJoin_nums(50);
        act3.setActNums(0);

        ActivityIndexModel.Act act4 = model.new Act();
        act4.setName("活动2");
        act4.setArea("广东东莞");
        act4.setBeginTime("2012");
        act4.setDistance("10km");
        act4.setIsCost("1");
        act4.setJoin_nums(50);
        act4.setActNums(0);

        ActivityIndexModel.Act act5 = model.new Act();
        act5.setName("活动2");
        act5.setArea("广东东莞");
        act5.setBeginTime("2012");
        act5.setDistance("10km");
        act5.setIsCost("1");
        act5.setJoin_nums(50);
        act5.setActNums(0);

        //添加数据
        final List<ActivityIndexModel.Act> list = new ArrayList<>();
        list.add(act);
        list.add(act2);
        list.add(act3);
        list.add(act4);
        list.add(act5);

        listView = new ListView(this);
        ActivityAdapter activityAdapter = new ActivityAdapter(MainActivity.this,list);
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(activityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",list.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, listView);
    }

    @Override
    public void onClick(final String str) {
        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<ActivityIndexModel> call = activityService.activity((float) bdLocation.getLongitude(),(float) bdLocation.getLatitude(),0,10,String.valueOf(actPosition),String.valueOf(disPosition),String.valueOf(timePosition));
        //final Call<ActivityIndexModel> call = activityService.area("f");
        RestService.execute(call, new RestService.Callback<ActivityIndexModel>() {
            @Override
            public void onResponse(ActivityIndexModel result) {
                Toast toast = Toast.makeText(MainActivity.this,result.getStatecode(),Toast.LENGTH_SHORT);
                toast.show();
                if(str.equals("全部活动")){
                    actPosition=0;
                }
                if(str.equals("校园招聘")){
                    actPosition=1;
                }
                if(str.equals("教育培训")){
                    actPosition=2;
                }
                System.out.println(actPosition + "   "+disPosition+"   "+timePosition);

                //虚拟数据
                ActivityIndexModel model2 = new ActivityIndexModel();
                ActivityIndexModel.Act act = model2.new Act();
                act.setName("活动");
                act.setArea("广东东莞");
                act.setBeginTime("2012");
                act.setDistance("10km");
                act.setIsCost("0");
                act.setJoin_nums(50);
                act.setActNums(100);

                //添加数据
                List<ActivityIndexModel.Act> list = new ArrayList<>();
                list.add(act);
                listView.setAdapter(null);
                ActivityAdapter activityAdapter = new ActivityAdapter(MainActivity.this,list);
                listView.setAdapter(activityAdapter);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("no");
            }
        });

        mDropDownMenu.closeMenu();
    }

    @Override
    public void onDistanceClick(final String str) {
        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<ActivityIndexModel> call = activityService.activity((float) bdLocation.getLongitude(),(float) bdLocation.getLatitude(),0,10,String.valueOf(actPosition),String.valueOf(disPosition),String.valueOf(timePosition));
        //final Call<ActivityIndexModel> call = activityService.area("f");
        RestService.execute(call, new RestService.Callback<ActivityIndexModel>() {
            @Override
            public void onResponse(ActivityIndexModel result) {
                Toast toast = Toast.makeText(MainActivity.this,result.getStatecode(),Toast.LENGTH_SHORT);
                toast.show();

                if(str.equals("不限")){
                    disPosition=0;
                }
                if(str.equals("10公里以内")){
                    disPosition=1;
                }
                if(str.equals("20公里以内")){
                    disPosition=2;
                }
                if(str.equals("30公里以内")){
                    disPosition=3;
                }
                System.out.println(actPosition + "   "+disPosition+"   "+timePosition);

                //虚拟数据
                ActivityIndexModel model = new ActivityIndexModel();
                ActivityIndexModel.Act act = model.new Act();
                act.setName("活动");
                act.setArea("广东东莞");
                act.setBeginTime("2012");
                act.setDistance("10km");
                act.setIsCost("0");
                act.setJoin_nums(50);
                act.setActNums(100);

                ActivityIndexModel.Act act2 = model.new Act();
                act2.setName("活动2");
                act2.setArea("广东东莞");
                act2.setBeginTime("2012");
                act2.setDistance("10km");
                act2.setIsCost("1");
                act2.setJoin_nums(50);
                act2.setActNums(0);

                //添加数据
                List<ActivityIndexModel.Act> list = new ArrayList<>();
                list.add(act);
                list.add(act2);

                //listView = new ListView(this);
                ActivityAdapter activityAdapter = new ActivityAdapter(MainActivity.this,list);
                listView.setAdapter(null);
                listView.setAdapter(activityAdapter);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("no");
            }
        });

        mDropDownMenu.closeMenu();
    }

    @Override
    public void onTimeClick(final String str) {
        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<ActivityIndexModel> call = activityService.activity((float) bdLocation.getLongitude(),(float) bdLocation.getLatitude(),0,10,String.valueOf(actPosition),String.valueOf(disPosition),String.valueOf(timePosition));
        //final Call<ActivityIndexModel> call = activityService.area("f");
        RestService.execute(call, new RestService.Callback<ActivityIndexModel>() {
            @Override
            public void onResponse(ActivityIndexModel result) {
                Toast toast = Toast.makeText(MainActivity.this,result.getStatecode(),Toast.LENGTH_SHORT);
                toast.show();
                if(str.equals("不限")){
                    timePosition=0;
                }
                if(str.equals("3天以内")){
                    timePosition=1;
                }
                if(str.equals("7天以内")){
                    timePosition=2;
                }
                if(str.equals("15天以内")){
                    timePosition=3;
                }
                System.out.println(actPosition + "   "+disPosition+"   "+timePosition);

                //虚拟数据
                ActivityIndexModel model = new ActivityIndexModel();

                ActivityIndexModel.Act act2 = model.new Act();
                act2.setName("活动2");
                act2.setArea("广东东莞");
                act2.setBeginTime("2012");
                act2.setDistance("10km");
                act2.setIsCost("1");
                act2.setJoin_nums(50);
                act2.setActNums(0);

                //添加数据
                List<ActivityIndexModel.Act> list = new ArrayList<>();
                list.add(act2);


                //listView = new ListView(this);
                ActivityAdapter activityAdapter = new ActivityAdapter(MainActivity.this,list);
                listView.setAdapter(null);
                listView.setAdapter(activityAdapter);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("no");
            }
        });

        mDropDownMenu.closeMenu();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
                curLocation.setText(location.getCity());

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                bdLocation = location;
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                curLocation.setText(location.getCity());
                sb.append(location.getCity());
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                curLocation.setText("定位失败");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                curLocation.setText("定位失败");
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                curLocation.setText("定位失败");
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        System.out.println(requestCode);
        System.out.println(resultCode);
        if(requestCode==1){
            curLocation.setText(data.getStringExtra("city"));
        }
    }
}
