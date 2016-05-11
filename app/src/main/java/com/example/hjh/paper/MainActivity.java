package com.example.hjh.paper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 初始化 Bmob SDK
        Bmob.initialize(this, "dfe450c5a08dc6b8bc53447eb060dacc");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this, Login_activity.class);
                mainIntent.getIntExtra("flag",0);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
