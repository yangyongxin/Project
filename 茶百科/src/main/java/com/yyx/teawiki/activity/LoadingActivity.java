package com.yyx.teawiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.yyx.teawiki.R;
import com.yyx.teawiki.db.ConfigDao;

public class LoadingActivity extends BaseActivity {

    //private static final String KEY_FIRST = "first_install";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConfigDao dao=new ConfigDao(LoadingActivity.this);
                String value = dao.find("first_install");
                //boolean isfrist = PrefUtils.getBoolean(LoadingActivity.this, KEY_FIRST,true);

                if ("false".equals(value)) {
                    startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
                }else {
                    startActivity(new Intent(LoadingActivity.this, WelcomeActivity.class));
                }
                finish();
            }
        }, 3000);
    }

}
