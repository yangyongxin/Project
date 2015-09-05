package com.yyx.teawiki.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.yyx.teawiki.R;
import com.yyx.teawiki.activity.HomeActivity;
import com.yyx.teawiki.beans.Lore;
import com.yyx.teawiki.fragment.ContentFragment;
import com.yyx.teawiki.http.TeawikiApi;
import com.yyx.teawiki.utils.BitmapCache;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by yangyongxin on 15/8/31.
 */
public class ContentAdapter extends BaseAdapter {
    private List<Lore> data;
    private Context context;
    private final ImageLoader mImageLoader;

    public ContentAdapter(List<Lore> data, Context context) {
        this.data = data;
        this.context = context;
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        BitmapCache bitmapCache=new BitmapCache();
        mImageLoader = new ImageLoader(mRequestQueue, bitmapCache);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.frag_con_list_item, null);
            holder=new ViewHolder();
            holder.iv_img = (ImageView) convertView.findViewById(R.id.item_iv_img);
            holder.tv_title = (TextView) convertView.findViewById(R.id.item_tv_title);
            holder.item_tv_count = (TextView) convertView.findViewById(R.id.item_tv_count);
            holder.item_tv_time = (TextView) convertView.findViewById(R.id.item_tv_time);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        Lore lore = data.get(position);
        holder.tv_title.setText(lore.getTitle());
        holder.item_tv_count.setText(lore.getCount()+"访问");
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        holder.item_tv_time.setText(dateFormat.format(lore.getTime()));
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.iv_img,
                android.R.drawable.ic_menu_rotate,android.R.drawable.ic_delete);
        mImageLoader.get(TeawikiApi.API_LORE_IMAGE + lore.getImg(), listener);

        return convertView;
    }

    class ViewHolder{
        ImageView iv_img;
        TextView tv_title;
        TextView item_tv_count;
        TextView item_tv_time;

    }
}
