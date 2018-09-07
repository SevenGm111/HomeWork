package com.handsomexi.homework.Util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.Date;

public class ImageUtil {
    public static void Crop(Uri uri , Activity activity){

        Uri destinationUri = Uri.fromFile(new File(Util.getWrongFile(), new Date().getTime()+".jpeg"));
        UCrop.Options options = new UCrop.Options();
        options.setToolbarTitle("裁剪");//设置标题栏文字
        options.setCropGridStrokeWidth(2);//设置裁剪网格线的宽度(我这网格设置不显示，所以没效果)
        options.setCropFrameStrokeWidth(10);//设置裁剪框的宽度
        // 一共三个参数，分别对应裁剪功能页面的“缩放”，“旋转”，“裁剪”界面
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.SCALE);
        options.setShowCropGrid(true);  //设置是否显示裁剪网格
        options.setShowCropFrame(true); //设置是否显示裁剪边框(true为方形边框)
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));//标题字的颜色以及按钮颜色
        options.setDimmedLayerColor(Color.parseColor("#AA000000"));//设置裁剪外颜色
        options.setToolbarColor(Color.parseColor("#000000")); // 设置标题栏颜色
        options.setStatusBarColor(Color.parseColor("#000000"));//设置状态栏颜色
        options.setCropGridColor(Color.parseColor("#ffffff"));//设置裁剪网格的颜色
        options.setCropFrameColor(Color.parseColor("#ffffff"));//设置裁剪框的颜色
        options.setCompressionQuality(50);
        options.setFreeStyleCropEnabled(true);
        UCrop.of(uri, destinationUri).withOptions(options).start(activity,888);
    }
    public static void openMatisse(Activity activity){
        Matisse.from(activity)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .gridExpectedSize(300)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .capture(true) //是否提供拍照功能
                .captureStrategy(new CaptureStrategy(true,"com.handsomexi.homework.fileprovider"))//存储到哪里
                .forResult(666);
    }
}
