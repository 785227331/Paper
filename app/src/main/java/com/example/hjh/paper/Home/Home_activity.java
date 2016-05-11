package com.example.hjh.paper.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initViewPage();
    }

    private void initViewPage() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpage_home);
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

        findViewById(R.id.img_download).setBackgroundColor(getResources().getColor(R.color.white));
        findViewById(R.id.img_personal).setBackgroundColor(getResources().getColor(R.color.white));
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentslist,title_list));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //设置标题页的转换
            }
            @Override
            public void onPageSelected(int position) {
                findViewById(R.id.img_search).setBackgroundColor(getResources().getColor(R.color.white));
                findViewById(R.id.img_download).setBackgroundColor(getResources().getColor(R.color.white));
                findViewById(R.id.img_personal).setBackgroundColor(getResources().getColor(R.color.white));
                switch (position){
                    case 0:
                        findViewById(R.id.img_search).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 1:
                        findViewById(R.id.img_download).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 2:
                        findViewById(R.id.img_personal).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
