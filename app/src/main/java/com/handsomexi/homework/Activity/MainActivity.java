package com.handsomexi.homework.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handsomexi.homework.Adapter.FragmentAdapter;
import com.handsomexi.homework.Fragment.MineFragment;
import com.handsomexi.homework.Fragment.ProgramFragment;
import com.handsomexi.homework.R;
import com.handsomexi.homework.Util.ImageUtil;
import com.handsomexi.homework.Util.PermissionUtil;
import com.handsomexi.homework.Util.Toasty;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.main_img_wrong)
    ImageView mainImgWrong;
    @BindView(R.id.main_wrong)
    LinearLayout mainWrong;
    @BindView(R.id.main_img_mine)
    ImageView mainImgMine;
    @BindView(R.id.main_mine)
    LinearLayout mainMine;
    @BindView(R.id.main_wtext)
    TextView wrongtext;
    @BindView(R.id.main_mtext)
    TextView minetext;

    @OnClick({R.id.main_add, R.id.main_mine, R.id.main_wrong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_add:
                ImageUtil.openMatisse(this);
                break;
            case R.id.main_mine:
                mainViewpager.setCurrentItem(1);
                break;
            case R.id.main_wrong:
                mainViewpager.setCurrentItem(0);
                break;
        }
    }


    String title[] = {"错题集", "我的"};


    void initView() {
        mainViewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), new Fragment[]{new ProgramFragment(), new MineFragment()}));
        mainViewpager.addOnPageChangeListener(new OnPagerChangedLisenter());
        mainImgWrong.setBackgroundResource(R.mipmap.wrong2);
        mainImgMine.setBackgroundResource(R.mipmap.mine);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtil.allGranted()) {
            initView();
        } else {
            PermissionUtil.checkPermissions(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 666: {
                if (resultCode == RESULT_OK)
                    ImageUtil.Crop(Matisse.obtainResult(data).get(0), this);
                break;
            }
            case 888: {
                if (resultCode == -1) {
                    final Uri resultUri = UCrop.getOutput(data);
                    Toasty.Info("保存成功");
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    Toasty.Info("裁剪失败");
                }
                break;
            }
        }
    }

    class OnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setTitle(title[position]);
            if (position == 0) {
                mainImgWrong.setBackgroundResource(R.mipmap.wrong2);
                mainImgMine.setBackgroundResource(R.mipmap.mine);
                wrongtext.setTextColor(getResources().getColor(R.color.colorPrimary));
                minetext.setTextColor(getResources().getColor(R.color.actionsheet_gray));
            }else {
                mainImgWrong.setBackgroundResource(R.mipmap.wrong);
                mainImgMine.setBackgroundResource(R.mipmap.mine2);
                minetext.setTextColor(getResources().getColor(R.color.colorPrimary));
                wrongtext.setTextColor(getResources().getColor(R.color.actionsheet_gray));
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
