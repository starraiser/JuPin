package com.example.administrator.jupin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountService accountService = RestService.create(AccountService.class);
        final Call<BizResult> call = accountService.fund(114.171994f,22.281089f,0,10);
        RestService.execute(call, new RestService.Callback<BizResult>() {
            @Override
            public void onResponse(BizResult result) {
                System.out.println("yes");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("no");
            }
        });
    }
}
