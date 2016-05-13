package com.example.hjh.paper.Home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hjh.paper.Home.Download.Download_Control;
import com.example.hjh.paper.R;

import java.util.ArrayList;

/**
 * Created by HJH on 2016/4/10.
 */
public class Home_activity extends AppCompatActivity{
    private ArrayList<Fragment> fragmentslist;
    private ArrayList<String> title_list = new ArrayList<>();
    private long exitTime = 0;//最后一次点击返回键的时间
    private static ViewPager v ;
    private  ViewPager viewPager;

    private Button home;
    private Button download;
    private Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        init();
        initViewPage();
    }

    private void init() {
        home = (Button) findViewById(R.id.btn_home);
        download = (Button) findViewById(R.id.btn_down);
        set = (Button) findViewById(R.id.btn_set);

        Drawable dra_home = getResources().getDrawable(R.drawable.ic_home_press);
        dra_home.setBounds(0, 0, 40, 40);
        home.setCompoundDrawables(null, dra_home, null, null);

        Drawable dra_download = getResources().getDrawable(R.drawable.ic_download_normal);
        dra_download.setBounds(0, 0, 40, 40);
        download.setCompoundDrawables(null, dra_download, null, null);

        Drawable dra_set = getResources().getDrawable(R.drawable.ic_set_normal);
        dra_set.setBounds(0, 0, 40, 40);
        set.setCompoundDrawables(null, dra_set, null, null);



        //设置点击事件
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable dra_home2 = getResources().getDrawable(R.drawable.ic_home_press);
                dra_home2.setBounds(0, 0, 40, 40);
                home.setCompoundDrawables(null, dra_home2, null, null);

                Drawable dra_download2 = getResources().getDrawable(R.drawable.ic_download_normal);
                dra_download2.setBounds(0, 0, 40, 40);
                download.setCompoundDrawables(null, dra_download2, null, null);

                Drawable dra_set2 = getResources().getDrawable(R.drawable.ic_set_normal);
                dra_set2.setBounds(0, 0, 40, 40);
                set.setCompoundDrawables(null, dra_set2, null, null);

                Home_activity.setposition(0);
                Log.i("abc","ssssss");
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable dra_home2 = getResources().getDrawable(R.drawable.ic_home_normal);
                dra_home2.setBounds(0, 0, 40, 40);
                home.setCompoundDrawables(null, dra_home2, null, null);

                Drawable dra_download2 = getResources().getDrawable(R.drawable.ic_download_press);
                dra_download2.setBounds(0, 0, 40, 40);
                download.setCompoundDrawables(null, dra_download2, null, null);

                Drawable dra_set2 = getResources().getDrawable(R.drawable.ic_set_normal);
                dra_set2.setBounds(0, 0, 40, 40);
                set.setCompoundDrawables(null, dra_set2, null, null);

                Home_activity.setposition(1);
                Log.i("abc","ssssss");
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable dra_home2 = getResources().getDrawable(R.drawable.ic_home_normal);
                dra_home2.setBounds(0, 0, 40, 40);
                home.setCompoundDrawables(null, dra_home2, null, null);

                Drawable dra_download2 = getResources().getDrawable(R.drawable.ic_download_normal);
                dra_download2.setBounds(0, 0, 40, 40);
                download.setCompoundDrawables(null, dra_download2, null, null);

                Drawable dra_set2 = getResources().getDrawable(R.drawable.ic_set_press);
                dra_set2.setBounds(0, 0, 40, 40);
                set.setCompoundDrawables(null, dra_set2, null, null);

                Home_activity.setposition(2);
                Log.i("abc","ssssss");
            }
        });
    }

    private void initViewPage() {
        viewPager = (ViewPager) findViewById(R.id.viewpage_home);
        v = viewPager;
        fragmentslist = new ArrayList<>();

        Fragment search = new Search();
        Fragment personal = new Personal();

        fragmentslist.add(search);
        fragmentslist.add(Download_Control.download_control);
        fragmentslist.add(personal);

        title_list.add("1");
        title_list.add("2");
        title_list.add("3");

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentslist,title_list));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //设置标题页的转换

            }
            @Override
            public void onPageSelected(int position) {
                Drawable dra_home = getResources().getDrawable(R.drawable.ic_home_normal);
                dra_home.setBounds(0, 0, 40, 40);
                home.setCompoundDrawables(null, dra_home, null, null);

                Drawable dra_download = getResources().getDrawable(R.drawable.ic_download_normal);
                dra_download.setBounds(0, 0, 40, 40);
                download.setCompoundDrawables(null, dra_download, null, null);

                Drawable dra_set = getResources().getDrawable(R.drawable.ic_set_normal);
                dra_set.setBounds(0, 0, 40, 40);
                set.setCompoundDrawables(null, dra_set, null, null);

                //将选中的设置为选择状态
                Drawable dra_select;
                switch (position){
                    case 0:
                        Drawable dra_home2 = getResources().getDrawable(R.drawable.ic_home_press);
                        dra_home2.setBounds(0, 0, 40, 40);
                        home.setCompoundDrawables(null, dra_home2, null, null);
                        break;
                    case 1:
                        Drawable dra_download2 = getResources().getDrawable(R.drawable.ic_download_press);
                        dra_download2.setBounds(0, 0, 40, 40);
                        download.setCompoundDrawables(null, dra_download2, null, null);
                        break;
                    case 2:
                        Drawable dra_set2 = getResources().getDrawable(R.drawable.ic_set_press);
                        dra_set2.setBounds(0, 0, 40, 40);
                        set.setCompoundDrawables(null, dra_set2, null, null);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //监听返回键，实现双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void setposition(int position){
        v.setCurrentItem(position);
    }

}
