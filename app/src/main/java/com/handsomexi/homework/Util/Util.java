package com.handsomexi.homework.Util;

import android.os.Environment;

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
}
