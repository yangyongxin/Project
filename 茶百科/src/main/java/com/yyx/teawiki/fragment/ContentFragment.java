package com.yyx.teawiki.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yyx.teawiki.R;
import com.yyx.teawiki.activity.DetailActivity;
import com.yyx.teawiki.adapter.ContentAdapter;
import com.yyx.teawiki.beans.Lore;
import com.yyx.teawiki.http.TeawikiApi;
import com.yyx.teawiki.view.refreshlistview.PullToRefreshBase;
import com.yyx.teawiki.view.refreshlistview.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyongxin on 15/8/31.
 */
public class ContentFragment extends Fragment implements PullToRefreshBase.OnRefreshListener, PullToRefreshBase.OnLastItemVisibleListener {

    private List<Lore> data = new ArrayList<Lore>();
    private PullToRefreshListView lv_refresh;
    private View view;
    private ListView listView;
    private ContentAdapter adapter;
    private int id;
    private int rows = 10;
    private int page = 1;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        initData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                intent.putExtra("id",data.get(position).getId());
                startActivity(intent);

            }
        });
        return view;
    }

    private void initView() {
        view = View.inflate(getActivity(), R.layout.frag_content, null);
        lv_refresh = (PullToRefreshListView) view.findViewById(R.id.lv_refresh);
        lv_refresh.setPullToRefreshEnabled(true);
        lv_refresh.setOnRefreshListener(this);
        lv_refresh.setOnLastItemVisibleListener(this);
        listView = lv_refresh.getRefreshableView();
    }


    public void initData() {
        id = getArguments().getInt("id");
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                TeawikiApi.API_LORE_LIST + "?id=" + id + "&rows=" + rows + "&page=" + page,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray tngou = jsonObject.getJSONArray("tngou");
                            Lore lore = null;
                            for (int i = 0; i < tngou.length(); i++) {
                                JSONObject jsonObject1 = tngou.getJSONObject(i);
                                lore = new Lore();
                                lore.setId(jsonObject1.getInt("id"));
                                lore.setTitle(jsonObject1.getString("title"));
                                lore.setImg(jsonObject1.getString("img"));
                                lore.setCount(jsonObject1.getInt("count"));
                                lore.setTime(jsonObject1.getLong("time"));
                                data.add(lore);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (page == 1) {
                            adapter = new ContentAdapter(data, getActivity());
                            listView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                            lv_refresh.onRefreshComplete();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        mQueue.add(jsonObjectRequest);
    }

    @Override
    public void onRefresh() {
        // data.add(0, "onRefresh");
        adapter.notifyDataSetChanged();
        lv_refresh.onRefreshComplete();
    }

    @Override
    public void onLastItemVisible() {
        page++;
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
