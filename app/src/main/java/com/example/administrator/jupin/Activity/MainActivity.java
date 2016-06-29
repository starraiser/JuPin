package com.example.administrator.jupin.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jupin.adapter.ActivityAdapter;
import com.example.administrator.jupin.adapter.ConstellationAdapter;
import com.example.administrator.jupin.adapter.DistanceAdapter;
import com.example.administrator.jupin.adapter.FilterAdapter;
import com.example.administrator.jupin.adapter.GirdDropDownAdapter;
import com.example.administrator.jupin.adapter.ListDropDownAdapter;
import com.example.administrator.jupin.R;
import com.example.administrator.jupin.RestService;
import com.example.administrator.jupin.adapter.TimeAdapter;
import com.example.administrator.jupin.api.ActivityService;
import com.example.administrator.jupin.model.ActivityIndexModel;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements GirdDropDownAdapter.ScreenCallback,TimeAdapter.TimeScreenCallback,DistanceAdapter.DistanceScreenCallback{

    private int[] location = new int[2];
    ListView listView;

    private String headers[] = {"全部活动","距离","时间"};
    private String activities[] = {"全部活动", "校园招聘", "教育培训"};
    private String distance[] = {"不限", "10公里以内", "20公里以内", "30公里以内"};
    private String time[] = {"不限", "3天以内", "5天以内","7天以内"};
    //private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};
    private GirdDropDownAdapter cityAdapter;
    private DistanceAdapter ageAdapter;
    private TimeAdapter sexAdapter;
    private String actCondition= "0";
    private String disCondition= "0";
    private String timeCondition= "0";
//    private ConstellationAdapter constellationAdapter;
//    private int constellationPosition = 0;

    private RelativeLayout choose_type;
    private RelativeLayout choose_dis;
    private RelativeLayout choose_time;

    private View mask;

    private DropDownMenu mDropDownMenu;

    private int actPosition = 0;
    private int disPosition = 0;
    private int timePosition = 0;

    private List<View> popupViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        choose_type = (RelativeLayout)findViewById(R.id.choose_type);
//        choose_dis = (RelativeLayout)findViewById(R.id.choose_dis);
//        choose_time = (RelativeLayout)findViewById(R.id.choose_time);

        //mask = (View)findViewById(R.id.mask);

//        choose_type.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> list = new ArrayList<String>();
//                list.add("全部活动");
//                list.add("校园招聘");
//                list.add("教育培训");
//                showPopupWindow(v,list,0);
//
//                mDropDownMenu = (DropDownMenu)findViewById(R.id.dropDownMenu);
//                final ListView  cityView = new ListView(MainActivity.this);
//                FilterAdapter adapter = new FilterAdapter(MainActivity.this,list,0);
//                cityView.setAdapter(adapter);
//            }
//        });
//
//        choose_dis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> list = new ArrayList<String>();
//                list.add("不限");
//                list.add("10公里以内");
//                list.add("20公里以内");
//                list.add("30公里以内");
//                list.add("50公里以内");
//                showPopupWindow(v,list,1);
//            }
//        });
//
//        choose_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> list = new ArrayList<String>();
//                list.add("不限");
//                list.add("3天以内");
//                list.add("7天以内");
//                list.add("15天以内");
//                showPopupWindow(v,list,2);
//            }
//        });

        initView();
//        List<String> list = new ArrayList<String>();
//        list.add("全部活动");
//        list.add("校园招聘");
//        list.add("教育培训");
//
//        mDropDownMenu = (DropDownMenu)findViewById(R.id.dropDownMenu);
//        final ListView  cityView = new ListView(MainActivity.this);
//        FilterAdapter adapter = new FilterAdapter(MainActivity.this,list,0);
//        cityView.setAdapter(adapter);
//        popupViews.add(cityView);
//
//        TextView contentView = new TextView(this);
//        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        contentView.setText("内容显示区域");
//        contentView.setGravity(Gravity.CENTER);
//        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//
//        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    public void showWithoutLimit(){
        ActivityService activityService = RestService.create(ActivityService.class);
        //final Call<ActivityIndexModel> call = accountService.fund(114.171994f,22.281089f,0,10);
        final Call<ActivityIndexModel> call = activityService.area("f");
        RestService.execute(call, new RestService.Callback<ActivityIndexModel>() {
            @Override
            public void onResponse(ActivityIndexModel result) {
                Toast toast = Toast.makeText(MainActivity.this,result.getStatecode(),Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("no");
            }
        });
    }

    public void showWithType(String type){
        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<ActivityIndexModel> call = activityService.activityWithType(114.171994f,22.281089f,0,10,type);
        //final Call<ActivityIndexModel> call = activityService.area("f");
        RestService.execute(call, new RestService.Callback<ActivityIndexModel>() {
            @Override
            public void onResponse(ActivityIndexModel result) {
                Toast toast = Toast.makeText(MainActivity.this,result.getStatecode(),Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("no");
            }
        });
    }

    public void showWithDistance(String distance){
        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<ActivityIndexModel> call = activityService.activityWithDistance(114.171994f,22.281089f,0,10,distance);
        //final Call<ActivityIndexModel> call = activityService.area("f");
        RestService.execute(call, new RestService.Callback<ActivityIndexModel>() {
            @Override
            public void onResponse(ActivityIndexModel result) {
                Toast toast = Toast.makeText(MainActivity.this,result.getStatecode(),Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("no");
            }
        });
    }

    public void showWithTime(String time){
        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<ActivityIndexModel> call = activityService.activityWithTime(114.171994f,22.281089f,0,10,time);
        //final Call<ActivityIndexModel> call = activityService.area("f");
        RestService.execute(call, new RestService.Callback<ActivityIndexModel>() {
            @Override
            public void onResponse(ActivityIndexModel result) {
                Toast toast = Toast.makeText(MainActivity.this,result.getStatecode(),Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("no");
            }
        });
    }

    private void showPopupWindow(View view, List<String> listdata, final int type){  // 弹窗
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_window,null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
        WindowManager.LayoutParams params=MainActivity.this.getWindow().getAttributes();
        params.alpha=0.7f;

        MainActivity.this.getWindow().setAttributes(params);
        //mask.setVisibility(View.VISIBLE);

        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        final ListView listView = (ListView)contentView.findViewById(R.id.grouplist);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < listdata.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("groupName", listdata.get(i));
            list.add(map);
        }

        int tmp = 0;
        if (type == 0) {
            tmp = actPosition;
        }
        if (type == 1) {
            tmp = disPosition;
        }
        if (type == 2) {
            tmp = timePosition;
        }
        final FilterAdapter adapter = new FilterAdapter(MainActivity.this,listdata,tmp);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type == 0){
                    actPosition = position;
                }
                if(type == 1){
                    disPosition = position;
                }
                if(type == 2){
                    timePosition = position;
                }
                listView.setAdapter(null);
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params=MainActivity.this.getWindow().getAttributes();
                params.alpha=1f;
                listView.setAdapter(null);
                //mask.setVisibility(View.GONE);
                MainActivity.this.getWindow().setAttributes(params);

            }
        });

        choose_type.getLocationOnScreen(location);

        popupWindow.setTouchable(true);  // 设置弹出窗口
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fff9f9")));
        popupWindow.setWidth(width);
        popupWindow.showAtLocation(choose_type, Gravity.NO_GRAVITY,location[0] - choose_type.getWidth(), location[1] + choose_type.getMeasuredHeight()+(choose_type.getHeight()-choose_type.getHeight())/2);
        popupWindow.showAsDropDown(view);


    }

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

        //init constellation
//        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
//        GridView constellation = (GridView) constellationView.findViewById(R.id.constellation);
//        constellationAdapter = new ConstellationAdapter(this, Arrays.asList(constellations));
//        constellation.setAdapter(constellationAdapter);
//        TextView ok = (TextView) constellationView.findViewById(R.id.ok);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
//                mDropDownMenu.closeMenu();
//            }
//        });

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

//        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                constellationAdapter.setCheckItem(position);
//                constellationPosition = position;
//            }
//        });

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
        List<ActivityIndexModel.Act> list = new ArrayList<>();
        list.add(act);
        list.add(act2);
        list.add(act3);
        list.add(act4);
        list.add(act5);

        listView = new ListView(this);
        ActivityAdapter activityAdapter = new ActivityAdapter(MainActivity.this,list);
        listView.setAdapter(activityAdapter);
//        contentView.setText("内容显示区域");
//        contentView.setGravity(Gravity.CENTER);
//        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, listView);
    }

    @Override
    public void onClick(final String str) {
//        if(str.equals("全部活动")){
//            actPosition=0;
//        }
//        if(str.equals("校园招聘")){
//            actPosition=1;
//        }
//        if(str.equals("教育培训")){
//            actPosition=2;
//        }
//        System.out.println(actPosition + "   "+disPosition+"   "+timePosition);
//
//        //虚拟数据
//        ActivityIndexModel model2 = new ActivityIndexModel();
//        ActivityIndexModel.Act act = model2.new Act();
//        act.setName("活动");
//        act.setArea("广东东莞");
//        act.setBeginTime("2012");
//        act.setDistance("10km");
//        act.setIsCost("0");
//        act.setJoin_nums(50);
//        act.setActNums(100);
//
//        //添加数据
//        List<ActivityIndexModel.Act> list = new ArrayList<>();
//        list.add(act);
//
//        listView = new ListView(this);
//        ActivityAdapter activityAdapter = new ActivityAdapter(MainActivity.this,list);
//        listView.setAdapter(null);
//        listView.setAdapter(activityAdapter);

        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<ActivityIndexModel> call = activityService.activity(114.171994f,22.281089f,0,10,String.valueOf(actPosition),String.valueOf(disPosition),String.valueOf(timePosition));
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
//        if(str.equals("不限")){
//            disPosition=0;
//        }
//        if(str.equals("10公里以内")){
//            disPosition=1;
//        }
//        if(str.equals("20公里以内")){
//            disPosition=2;
//        }
//        if(str.equals("30公里以内")){
//            disPosition=3;
//        }
//        System.out.println(actPosition + "   "+disPosition+"   "+timePosition);
//
//        //虚拟数据
//        ActivityIndexModel model = new ActivityIndexModel();
//        ActivityIndexModel.Act act = model.new Act();
//        act.setName("活动");
//        act.setArea("广东东莞");
//        act.setBeginTime("2012");
//        act.setDistance("10km");
//        act.setIsCost("0");
//        act.setJoin_nums(50);
//        act.setActNums(100);
//
//        ActivityIndexModel.Act act2 = model.new Act();
//        act2.setName("活动2");
//        act2.setArea("广东东莞");
//        act2.setBeginTime("2012");
//        act2.setDistance("10km");
//        act2.setIsCost("1");
//        act2.setJoin_nums(50);
//        act2.setActNums(0);
//
//        //添加数据
//        List<ActivityIndexModel.Act> list = new ArrayList<>();
//        list.add(act);
//        list.add(act2);
//
//        listView = new ListView(this);
//        ActivityAdapter activityAdapter = new ActivityAdapter(MainActivity.this,list);
//        listView.setAdapter(null);
//        listView.setAdapter(activityAdapter);

        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<ActivityIndexModel> call = activityService.activity(114.171994f,22.281089f,0,10,String.valueOf(actPosition),String.valueOf(disPosition),String.valueOf(timePosition));
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
//        if(str.equals("不限")){
//            timePosition=0;
//        }
//        if(str.equals("3天以内")){
//            timePosition=1;
//        }
//        if(str.equals("7天以内")){
//            timePosition=2;
//        }
//        if(str.equals("15天以内")){
//            timePosition=3;
//        }
//        System.out.println(actPosition + "   "+disPosition+"   "+timePosition);
//
//        //虚拟数据
//        ActivityIndexModel model = new ActivityIndexModel();
//
//        ActivityIndexModel.Act act2 = model.new Act();
//        act2.setName("活动2");
//        act2.setArea("广东东莞");
//        act2.setBeginTime("2012");
//        act2.setDistance("10km");
//        act2.setIsCost("1");
//        act2.setJoin_nums(50);
//        act2.setActNums(0);
//
//        //添加数据
//        List<ActivityIndexModel.Act> list = new ArrayList<>();
//        list.add(act2);
//
//
//        listView = new ListView(this);
//        ActivityAdapter activityAdapter = new ActivityAdapter(MainActivity.this,list);
//        listView.setAdapter(null);
//        listView.setAdapter(activityAdapter);


        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<ActivityIndexModel> call = activityService.activity(114.171994f,22.281089f,0,10,String.valueOf(actPosition),String.valueOf(disPosition),String.valueOf(timePosition));
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
}
