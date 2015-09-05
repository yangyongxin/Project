package com.yyx.teawiki.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yyx.teawiki.R;
import com.yyx.teawiki.adapter.ContentAdapter;
import com.yyx.teawiki.beans.Detail;
import com.yyx.teawiki.beans.Lore;
import com.yyx.teawiki.http.TeawikiApi;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_message;
    private Detail detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_detail);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_message = (TextView) findViewById(R.id.tv_message);
        
    }

    private void initData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                TeawikiApi.API_LORE_SHOW + "?id=" + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            detail = new Detail();
                           // detail.setId(jsonObject.getLong("id"));
                            detail.setTitle(jsonObject.getString("title"));
                            detail.setMessage(jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        tv_title.setText(detail.getTitle());
                        tv_message.setText(Html.fromHtml(detail.getMessage()));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        mQueue.add(jsonObjectRequest);
    }


}
