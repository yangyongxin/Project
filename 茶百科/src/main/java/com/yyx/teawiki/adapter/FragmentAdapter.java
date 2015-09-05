package com.yyx.teawiki.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yyx.teawiki.activity.HomeActivity;
import com.yyx.teawiki.beans.Loreclass;
import com.yyx.teawiki.fragment.ContentFragment;

import java.util.List;

/**
 * Created by yangyongxin on 15/8/31.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private  List<Loreclass> data;
    public FragmentAdapter(FragmentManager fm,List<Loreclass> data) {
        super(fm);
        this.data=data;
    }

    @Override
    public Fragment getItem(int position) {
        ContentFragment fragment=new ContentFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("id",  data.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getTitle();
    }
}
