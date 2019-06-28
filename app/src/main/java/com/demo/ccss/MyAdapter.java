package com.demo.ccss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * create by 成君 943193747@qq.com
 * on 2019/6/26  18:33
 */
public class MyAdapter extends ArrayAdapter<User> {

    int resource;

    //构造方法传递三个参数,分别为:上下文,列表子布局和列表所需数据
    public MyAdapter(Context context, int resource, List<User> list){
        //继承父类构造
        super(context,resource,list);
        //后面会用到
        this.resource = resource;
    }

    //加载适配器时会调用此方法
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获得当前显示项的User对象
        User user = getItem(position);
        ViewHolder holder;
        //若是第一次加载页面则调用此方法初始化
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource,parent,false);

            holder = new ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.item_name);
            holder.tv_phone = convertView.findViewById(R.id.item_phone);
            holder.tv_sort = convertView.findViewById(R.id.item_sort);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //将数据传递给控件并显示
        holder.tv_name.setText(user.getName());
        holder.tv_phone.setText(user.getPhone());
        holder.tv_sort.setText(user.getSort());

        return convertView;
    }

    //缓存类,用于存放已存在的对象
    class ViewHolder{
        TextView tv_name;
        TextView tv_phone;
        TextView tv_sort;
    }
}