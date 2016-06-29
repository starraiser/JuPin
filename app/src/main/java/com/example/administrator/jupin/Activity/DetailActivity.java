package com.example.administrator.jupin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.administrator.jupin.R;
import com.example.administrator.jupin.model.ActivityIndexModel;
import com.example.administrator.jupin.network.RestService;
import com.example.administrator.jupin.network.api.ActivityService;

import retrofit2.Call;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final TextView html = (TextView)findViewById(R.id.html);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String id = bundle.getString("id");

        String token = "1";
        String share = "1";

        // 访问接口
        ActivityService activityService = RestService.create(ActivityService.class);
        final Call<String> call = activityService.detail(id,token,share);
        RestService.execute(call, new RestService.Callback<String>() {
            @Override
            public void onResponse(String result) {
                //显示html页面
                html.setText(Html.fromHtml(result));
            }

            @Override
            public void onError(Throwable throwable) {
                // 访问出错页面
                html.setText(Html.fromHtml("<html>\n" +
                        "\n" +
                        "<head>\n" +
                        "<title>访问出错</title>\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>\n" +
                        "<p> 用户未登录。</p>\n" +
                        "<p> 无法访问。</p>\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>"));
            }
        });
    }
}
