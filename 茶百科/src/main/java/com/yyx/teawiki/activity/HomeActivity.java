package com.yyx.teawiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.yyx.teawiki.R;
import com.yyx.teawiki.adapter.FragmentAdapter;
import com.yyx.teawiki.beans.Loreclass;
import com.yyx.teawiki.http.TeawikiApi;
import com.yyx.teawiki.view.tabindicator.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends FragmentActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    private SlidingTabLayout tab_class;
    private ViewPager vp_frag_container;
    private List<Loreclass> data = new ArrayList<Loreclass>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), data);
        vp_frag_container.setAdapter(adapter);
        tab_class.setViewPager(vp_frag_container);
    }

    private void initView() {
        setContentView(R.layout.activity_home);
        tab_class = (SlidingTabLayout) findViewById(R.id.tab_class);
        vp_frag_container = (ViewPager) findViewById(R.id.vp_frag_container);
    }

    private void initData() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                TeawikiApi.API_LORE_CLASSIFY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                data.add(new Loreclass(id, title));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), data);
                        vp_frag_container.setAdapter(adapter);
                        tab_class.setViewPager(vp_frag_container);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        mQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_more:
                startActivity(new Intent(this, MoreActivity.class));
                break;
        }
        return true;
    }


}
