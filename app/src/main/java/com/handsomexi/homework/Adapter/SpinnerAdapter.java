package com.handsomexi.homework.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.handsomexi.homework.R;

public class SpinnerAdapter extends BaseAdapter {
    private String[] array;
    private Context context;
    public SpinnerAdapter(Context context,String[] array){
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public Object getItem(int position) {
        return array[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        if(null == view){
            view = LayoutInflater.from(context).inflate(R.layout.item_spinner,null);
            holder = new ViewHolder();
            holder.textView = view.findViewById(R.id.item_spinner_text);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(array[i]);

        return view;
    }
    class ViewHolder{
        TextView textView;
    }
}
