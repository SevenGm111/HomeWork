package com.handsomexi.homework.Util;

import android.os.Environment;

import com.handsomexi.homework.Bean.HomeWorkBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Util {
    public static void mkDirs(){
        File file = getWrongFile();
        if(!file.exists())
            file.mkdirs();
    }
    public static File getWrongFile(){
        return  new File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/Wrong");
    }
    public static HomeWorkBean getQueryAllBean(){
        return  new HomeWorkBean("","全部",0,0,0);
    }
    public static List<Integer> intArray2List(int[] a){
        List<Integer> integers = new ArrayList<>();
        for (int i :a){
            integers.add(i);
        }
        return integers;
    }
    public static String long2Date(long time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        String year = cal.get(Calendar.YEAR)+"年";
        String month = cal.get(Calendar.MONTH)+1 + "月";
        String day = cal.get(Calendar.DATE) +"日";
        return year + month +day;
    }
}
