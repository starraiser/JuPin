package com.example.administrator.jupin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.jupin.R;
import com.example.administrator.jupin.model.ActivityIndexModel;
import com.example.administrator.jupin.model.AreaModel;
import com.example.administrator.jupin.network.RestService;
import com.example.administrator.jupin.network.api.ActivityService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ProvinceActivity extends AppCompatActivity {

    private ListView listView;
    private AreaModel model;
    List<String> province = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        listView=(ListView)findViewById(R.id.city_list);
//        List<String> province = new ArrayList<String>();
//        for(int i=0;i<10;i++){
//            province.add("广东");
//        }
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.province,province);
//        listView.setAdapter(adapter);

        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<AreaModel> call = activityService.area("1");
        RestService.execute(call, new RestService.Callback<AreaModel>() {
            @Override
            public void onResponse(final AreaModel result) {
                //省份列表
                for(int i=0;i<result.getData().size();i++){
                    province.add(result.getData().get(i).getNAME());
                }
                listView.setAdapter(new ArrayAdapter<String>(ProvinceActivity.this, R.layout.province,province));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ProvinceActivity.this,CityActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position",position);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,1);
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {
                //虚拟数据
                for(int i=0;i<10;i++){
                    province.add("广东");
                }
                listView.setAdapter(new ArrayAdapter<String>(ProvinceActivity.this, R.layout.province,province));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent();
//                        intent.putExtra("city","广东");
//                        setResult(1,intent);
//                        finish();
                        Intent intent = new Intent(ProvinceActivity.this,CityActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position",position);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,1);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){
            Intent intent = new Intent();
            intent.putExtra("city",data.getStringExtra("city"));
            intent.putExtra("longitude",data.getStringExtra("longitude"));
            intent.putExtra("latitude",data.getStringExtra("latitude"));
            setResult(1,intent);
            finish();
        }
    }
}
