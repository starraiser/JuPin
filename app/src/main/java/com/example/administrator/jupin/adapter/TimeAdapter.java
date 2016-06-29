package com.example.administrator.jupin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jupin.R;

import java.util.List;

/**
 * Created by hasee on 2016/6/29.
 */
public class TimeAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;
    private TimeScreenCallback callback;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public TimeAdapter(Context context, List<String> list,TimeScreenCallback callback) {
        this.callback = callback;
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_drop_down, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        }
        viewHolder.mText = (TextView)convertView.findViewById(R.id.text);
        viewHolder.tick = (ImageView)convertView.findViewById(R.id.tick);
        viewHolder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onTimeClick(viewHolder.mText.getText().toString());
                setCheckItem(position);
            }
        });
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, final ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                //viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                viewHolder.mText.setTextColor(Color.parseColor("#00dd00"));
                //callback.onClick(viewHolder.mText.getText().toString());
                //viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                viewHolder.tick.setVisibility(View.VISIBLE);
                //viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.mipmap.drop_down_checked), null);
            } else {
                viewHolder.tick.setVisibility(View.INVISIBLE);
                //viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.mText.setTextColor(Color.parseColor("#000000"));
                viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }

    static class ViewHolder {

        TextView mText;
        ImageView tick;
    }

    public interface TimeScreenCallback{
        void onTimeClick(String str);
    }

}
