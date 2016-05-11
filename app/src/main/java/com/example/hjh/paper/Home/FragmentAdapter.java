package com.example.hjh.paper.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HJH on 2016/4/10.
 */
public class FragmentAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> list;
    private List<String> titleList;//viewpager的标题
    public FragmentAdapter(FragmentManager fm,ArrayList<Fragment> fragList,List<String> titleList) {
        super(fm);
        this.list = fragList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {

        return super.instantiateItem(arg0, arg1);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        super.destroyItem(container, position, object);
    }
}
