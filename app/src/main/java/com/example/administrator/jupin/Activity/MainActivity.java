package com.example.administrator.jupin.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jupin.ConstellationAdapter;
import com.example.administrator.jupin.FilterAdapter;
import com.example.administrator.jupin.GirdDropDownAdapter;
import com.example.administrator.jupin.ListDropDownAdapter;
import com.example.administrator.jupin.R;
import com.example.administrator.jupin.RestService;
import com.example.administrator.jupin.api.ActivityService;
import com.example.administrator.jupin.model.ActivityIndexModel;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private int[] location = new int[2];

    private String headers[] = {"全部活动","距离","时间","star"};
    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private String sexs[] = {"不限", "男", "女"};
    private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};
    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;
    private int constellationPosition = 0;

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

        choose_type = (RelativeLayout)findViewById(R.id.choose_type);
        choose_dis = (RelativeLayout)findViewById(R.id.choose_dis);
        choose_time = (RelativeLayout)findViewById(R.id.choose_time);

        //mask = (View)findViewById(R.id.mask);

        choose_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<String>();
                list.add("全部活动");
                list.add("校园招聘");
                list.add("教育培训");
                showPopupWindow(v,list,0);

                mDropDownMenu = (DropDownMenu)findViewById(R.id.dropDownMenu);
                final ListView  cityView = new ListView(MainActivity.this);
                FilterAdapter adapter = new FilterAdapter(MainActivity.this,list,0);
                cityView.setAdapter(adapter);
            }
        });

        choose_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<String>();
                list.add("不限");
                list.add("10公里以内");
                list.add("20公里以内");
                list.add("30公里以内");
                list.add("50公里以内");
                showPopupWindow(v,list,1);
            }
        });

        choose_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<String>();
                list.add("不限");
                list.add("3天以内");
                list.add("7天以内");
                list.add("15天以内");
                showPopupWindow(v,list,2);
            }
        });

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
        cityAdapter = new GirdDropDownAdapter(this, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = (GridView) constellationView.findViewById(R.id.constellation);
        constellationAdapter = new ConstellationAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = (TextView) constellationView.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                mDropDownMenu.closeMenu();
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constellationAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });

        //init context view
        ListView contentView = new ListView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        contentView.setText("内容显示区域");
//        contentView.setGravity(Gravity.CENTER);
//        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }
}
