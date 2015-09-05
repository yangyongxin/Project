package com.yyx.teawiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yyx.teawiki.R;
import com.yyx.teawiki.db.ConfigDao;

public class WelcomeActivity extends BaseActivity {
    //private static final String KEY_FIRST = "first_install";

    private ViewPager viewPager;
    private LinearLayout ll_indicator;

    private View[] views;
    private int currentPage = 0;
    private int[] imageResourceId = new int[]{R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initViewPager();
    }

    private void initViewPager() {
        WelcomePagerAdapter adapter = new WelcomePagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(adapter);
    }

    private void initView() {
        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) findViewById(R.id.vp_welcome);
        ll_indicator = (LinearLayout) findViewById(R.id.ll_indicator);
    }

    private void initData() {
        int length = imageResourceId.length;
        views = new View[length];

        ImageView imageView = null;
        ViewGroup.LayoutParams layoutParams;
        View view = null;
        LinearLayout.LayoutParams params;
        for (int i = 0; i < length; i++) {
            imageView = new ImageView(this);
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setBackgroundResource(imageResourceId[i]);
            if (i == (length - 1)) {
                imageView.setClickable(true);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                        finish();
                    }
                });
            }
            views[i] = imageView;

            view = new View(this);
            params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 10;
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.page);
            ll_indicator.addView(view);
        }
        ll_indicator.getChildAt(currentPage).setBackgroundResource(R.drawable.page_now);
    }


    private class WelcomePagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public int getCount() {
            return imageResourceId.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views[position];
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views[position]);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            ll_indicator.getChildAt(currentPage).setBackgroundResource(R.drawable.page);
            ll_indicator.getChildAt(position).setBackgroundResource(R.drawable.page_now);
            currentPage = position;
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //PrefUtils.putBoolean(this,KEY_FIRST,false);
        ConfigDao dao = new ConfigDao(this);
        dao.add("first_install", "false");
    }
}
