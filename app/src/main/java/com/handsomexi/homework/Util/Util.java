package com.handsomexi.homework.Util;

import android.os.Environment;

import com.handsomexi.homework.Bean.HomeWorkBean;

import java.io.File;

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
}
