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
import com.example.administrator.jupin.model.ActivityIndexModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by hasee on 2016/6/28.
 */
public class ActivityAdapter extends BaseAdapter{

    List<ActivityIndexModel.Act> list;
    Context context;
    public LayoutInflater inflater;

    public ActivityAdapter(Context context, List<ActivityIndexModel.Act> list){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.single_info, null);
        }
        ActivityViewHolder activityViewHolder = new ActivityViewHolder();
        activityViewHolder.name = (TextView)convertView.findViewById(R.id.name);
        activityViewHolder.area = (TextView)convertView.findViewById(R.id.area);
        activityViewHolder.cost = (TextView)convertView.findViewById(R.id.cost);
        activityViewHolder.time = (TextView)convertView.findViewById(R.id.time);
        activityViewHolder.distance = (TextView)convertView.findViewById(R.id.distance);
        activityViewHolder.remain = (TextView)convertView.findViewById(R.id.remain);
        activityViewHolder.img = (ImageView) convertView.findViewById(R.id.img);

        activityViewHolder.name.setText(list.get(position).getName());
        activityViewHolder.time.setText(list.get(position).getBeginTime());
        activityViewHolder.area.setText(list.get(position).getArea());
        activityViewHolder.distance.setText(list.get(position).getDistance());
        if(list.get(position).getIsCost().equals("1")){
            activityViewHolder.cost.setText("收费");
        } else {
            activityViewHolder.cost.setVisibility(View.INVISIBLE);
        }
        if(list.get(position).getActNums()==0){
            activityViewHolder.remain.setText("不限名额");
            activityViewHolder.remain.setTextColor(Color.parseColor("#7bbbef"));
        } else {
            //activityViewHolder.remain.setText("111111111111");
            int temp = list.get(position).getActNums()-list.get(position).getJoin_nums();
            activityViewHolder.remain.setText("剩余"+temp+"名");
        }
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        ImageLoader.getInstance().init(configuration);
        DisplayImageOptions options = new DisplayImageOptions.Builder().build();

        ImageLoader.getInstance().displayImage(list.get(position).getImgUrl(),activityViewHolder.img,options);


        return convertView;
    }

    private class ActivityViewHolder{
        TextView name;
        TextView time;
        TextView cost;
        TextView area;
        TextView distance;
        TextView remain;
        ImageView img;
    }
}
