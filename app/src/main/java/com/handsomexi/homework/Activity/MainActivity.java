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
import com.handsomexi.homework.Fragment.WrongFragment;
import com.handsomexi.homework.R;
import com.handsomexi.homework.Util.ImageUtil;
import com.handsomexi.homework.Util.PermissionUtil;
import com.handsomexi.homework.Util.Toasty;
import com.handsomexi.homework.View.SelectDialog;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    //初始化底部菜单项的各控件
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
                ImageUtil.openMatisse(this);   //跳转到选择拍照界面
                break;
            case R.id.main_mine:
                mainViewpager.setCurrentItem(1);
                break;
            case R.id.main_wrong:
                mainViewpager.setCurrentItem(0);
                break;
        }
    }


    String title[] = {"错题集", "我"};


    void initView() {
        mainViewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), new Fragment[]{new WrongFragment(), new MineFragment()}));
        //设置底部的菜单项的变色
        OnPagerChangedLisenter pagerChangedLisenter = new OnPagerChangedLisenter();
        mainViewpager.addOnPageChangeListener(pagerChangedLisenter);
        pagerChangedLisenter.onPageSelected(0);  //默认为选中错题集这个选项
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (PermissionUtil.allGranted()) {    //检测权限，权限都允许了跳转到初始化方法
            initView();
        } else
            PermissionUtil.checkPermissions(this);
    }

    //请求权限的回调方法，如果请求权限失败，还是调用initView()方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtil.allGranted()) {
            initView();
        } else {
            PermissionUtil.checkPermissions(this);
        }
    }

    //拍照界面结束返回的方法，由imageUtil.openMatisse方法进入拍照界面
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
                    new SelectDialog(this,resultUri.getPath());     //裁剪完之后，跳转到选择标签界面
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

        //设置选择器，按到对应菜单项图标和文字会一起变色
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
