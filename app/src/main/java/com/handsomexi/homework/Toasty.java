package com.handsomexi.homework;

import android.widget.Toast;

public class Toasty {
    public static void Info(String s){
        Toast.makeText(Myapp.getInstances(),s,Toast.LENGTH_SHORT).show();
    }
}
