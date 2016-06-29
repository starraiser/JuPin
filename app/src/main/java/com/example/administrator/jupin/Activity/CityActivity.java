package com.example.administrator.jupin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.jupin.R;
import com.example.administrator.jupin.model.AreaModel;
import com.example.administrator.jupin.network.RestService;
import com.example.administrator.jupin.network.api.ActivityService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class CityActivity extends AppCompatActivity {

    private ListView listView;

    List<String> city = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        //从上级activity获取省份
        listView = (ListView)findViewById(R.id.city_list);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final int provincePosition = bundle.getInt("position");

        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<AreaModel> call = activityService.area("1");
        RestService.execute(call, new RestService.Callback<AreaModel>() {
            @Override
            public void onResponse(final AreaModel result) {
                // 城市列表
                for(int i=0;i<result.getData().get(provincePosition).getCitys().size();i++){
                    city.add(result.getData().get(provincePosition).getCitys().get(i).getNAME());
                }
                listView.setAdapter(new ArrayAdapter<String>(CityActivity.this, R.layout.province,city));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.putExtra("city",result.getData().get(provincePosition).getCitys().get(position).getNAME());
                        intent.putExtra("longitude",result.getData().get(provincePosition).getCitys().get(position).getLONGITUDE());
                        intent.putExtra("latitude",result.getData().get(provincePosition).getCitys().get(position).getLATITUDE());
                        setResult(1,intent);
                        finish();
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {
                // 虚拟数据
                for(int i=0;i<10;i++){
                    city.add("东莞");
                }
                listView.setAdapter(new ArrayAdapter<String>(CityActivity.this, R.layout.province,city));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.putExtra("city","东莞");
                        intent.putExtra("longitude","116.422773");
                        intent.putExtra("latitude","39.934772");
                        setResult(1,intent);
                        finish();
                    }
                });
            }
        });
    }
}
