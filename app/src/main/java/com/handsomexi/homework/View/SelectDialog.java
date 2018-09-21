package com.handsomexi.homework.View;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.handsomexi.homework.Adapter.SpinnerAdapter;
import com.handsomexi.homework.R;
import com.handsomexi.homework.Util.SqlUtil;
import com.handsomexi.homework.Util.Toasty;
import com.handsomexi.homework.Util.Util;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class SelectDialog  {
    private int stats = 0;
    private Context context;
    public AppCompatSpinner spinner1,spinner2,spinner3;
    private String[] array1,array2,array3;
    public SelectDialog(Context context,String path) {
        this.context = context;
        array1 = context.getResources().getStringArray(R.array.default_subject);   //把科目放进集合中
        array2 = context.getResources().getStringArray(R.array.default_shcoolyear); //把年级放进集合中
        array3 = context.getResources().getStringArray(R.array.default_semester);   //把学期放进集合中

        new AlertDialog.Builder(context)             //弹出对话框
                .setTitle("选择标签")
                .setOnCancelListener(dialog -> {     //怎么做到的
                    if(stats==0) {
                        new File(path).delete();
                        Toasty.Info("取消保存");
                    }
                    else {
                        String subject = getItem(spinner1);
                        String schoolYear = getItem(spinner2);
                        String Sem = getItem(spinner3);

                        SqlUtil.save(path,subject,schoolYear,Sem);   //保存到数据库中
                        EventBus.getDefault().post(Util.getQueryAllBean());     //发送事件
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
        spinner1.setAdapter(new SpinnerAdapter(context,array1));
        spinner2.setAdapter(new SpinnerAdapter(context,array2));
        spinner3.setAdapter(new SpinnerAdapter(context,array3));
        return view;
    }
    private String getItem(AppCompatSpinner spinner){
        return spinner.getSelectedItem().toString();
    }

}
