package com.handsomexi.homework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.handsomexi.homework.Util.ImageUtil;
import com.handsomexi.homework.Util.PermissionUtil;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.main_add)
    ImageView add;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    void initView(){
        add.setOnClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (PermissionUtil.allGranted()) {
            initView();
        } else
            PermissionUtil.checkPermissions(this);
    }

    @Override
    public void onClick(View v) {
        ImageUtil.openMatisse(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(PermissionUtil.allGranted()){
            initView();
        }else {
            PermissionUtil.checkPermissions(this);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 666:{
                if(resultCode == RESULT_OK)
                    ImageUtil.Crop(Matisse.obtainResult(data).get(0),this);
                break;
            }
            case 888:{
                if(resultCode == -1){
                    final Uri resultUri = UCrop.getOutput(data);
                    Toasty.Info("保存成功");
                }
                else if (resultCode ==UCrop.RESULT_ERROR){
                    Toasty.Info("裁剪失败");
                }
                break;
            }
        }
    }

}
