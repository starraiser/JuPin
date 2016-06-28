package com.example.administrator.jupin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hasee on 2016/6/28.
 */
public class FilterAdapter extends BaseAdapter {
    List<String> item;
    Context context;
    public LayoutInflater inflater;
    public int flag;

    public FilterAdapter(Context context,List<String> item,int flag){
        inflater = LayoutInflater.from(context);
        this.flag = flag;
        this.context = context;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println(flag);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.pop_single_group, null);
        }
        GroupViewHolder groupViewHolder = new GroupViewHolder();
        groupViewHolder.item = (TextView)convertView.findViewById(R.id.item);
        groupViewHolder.tick = (ImageView)convertView.findViewById(R.id.tick);

        groupViewHolder.item.setText(item.get(position));
        if(position == flag){
            groupViewHolder.tick.setVisibility(View.VISIBLE);
            groupViewHolder.item.setTextColor(Color.parseColor("#00FF00"));
        } else {
            groupViewHolder.tick.setVisibility(View.GONE);
            groupViewHolder.item.setTextColor(Color.parseColor("#000000"));
        }

        return convertView;
    }

    private class GroupViewHolder{
        TextView item;
        ImageView tick;
    }

}
