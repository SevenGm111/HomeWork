package com.handsomexi.homework.View;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.handsomexi.homework.Bean.HomeWorkBean;
import com.handsomexi.homework.R;
import com.handsomexi.homework.Util.SqlUtil;
import com.handsomexi.homework.Util.Toasty;
import com.handsomexi.homework.Util.Util;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class SelectDialog  {
    private int stats = 0;
    private Context context;
    private AppCompatSpinner spinner1,spinner2,spinner3;
    String[] array1,array2,array3;
    int[] intarray;
    String sub;
    int schoolyear,sem;
    public SelectDialog(Context context,String path) {
        this.context = context;
        array1 = context.getResources().getStringArray(R.array.default_subject);
        array2 = context.getResources().getStringArray(R.array.default_shcoolyear);
        array3 = context.getResources().getStringArray(R.array.default_semester);
        intarray = context.getResources().getIntArray(R.array.default_shcoolyear_int);
        sub = array1[0];
        schoolyear = intarray[0];
        sem = 1;

        new AlertDialog.Builder(context)
                .setTitle("选择标签")
                .setOnCancelListener(dialog -> {
                    if(stats==0) {
                        new File(path).delete();
                        Toasty.Info("取消保存");
                    }
                    else {
                        SqlUtil.save(path,sub,schoolyear,sem);
                        EventBus.getDefault().post(Util.getQueryAllBean());
                        Toasty.Info("保存成功");
                    }
                })
                .setView(getView())
                .setPositiveButton("确定", (dialog, which) -> {
                    stats = 1;
                    dialog.cancel();
                })
                .setNegativeButton("取消",(dialog, which) -> dialog.cancel())
                .show();
    }
    private View getView(){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_selectdialog,null);
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        spinner3 = view.findViewById(R.id.spinner3);
        spinner1.setAdapter(new Adapter(array1));
        spinner2.setAdapter(new Adapter(array2));
        spinner3.setAdapter(new Adapter(array3));
        spinner1.setOnItemSelectedListener(new Listener(1));
        spinner2.setOnItemSelectedListener(new Listener(2));
        spinner3.setOnItemSelectedListener(new Listener(3));
        return view;
    }

    class Adapter extends BaseAdapter {
        String[] array;
        Adapter(String[] array){
            this.array = array;
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
    class Listener implements AdapterView.OnItemSelectedListener{
        int type;
        Listener(int type){
            this.type = type;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.e("type"+type,"viewid:"+view.getId()+"position"+position+"id"+id);
            switch (type){
                case 1:{
                    sub = array1[position];
                    break;
                }
                case 2:{
                    schoolyear = intarray[position];
                    break;
                }
                case 3:{
                    sem = position +1;
                }
            }
            Log.e("select",sub+schoolyear+"   "+sem);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }



}
