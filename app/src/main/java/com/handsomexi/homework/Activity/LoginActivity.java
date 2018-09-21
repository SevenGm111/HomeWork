package com.handsomexi.homework.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.handsomexi.homework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    //初始化登录界面各控件
    @BindView(R.id.et_userName)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_unameClear)
    ImageView nameClear;
    @BindView(R.id.iv_pwdClear)
    ImageView passwordClear;
    @BindView(R.id.forget)
    TextView forget;
    @BindView(R.id.cb_checkbox)
    CheckBox rember;
    @BindView(R.id.btn_login)
    Button btn_Login;
    @BindView(R.id.btn_register)
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);           //
    }

    //显示删除按钮并响应删除操作
    @OnClick({R.id.et_userName,R.id.et_password,R.id.iv_unameClear,R.id.iv_pwdClear})
    public void onViewClike2(View view){
        switch(view.getId()) {
            case R.id.et_userName:
                showDelectImage(etName, nameClear);  //显示删除按钮
                break;
            case R.id.et_password:
                showDelectImage(etPassword, passwordClear);
                break;
            case R.id.iv_unameClear:
                etName.setText("");
                break;
            case R.id.iv_pwdClear:
                etPassword.setText("");
                break;
        }

    }

    @OnClick({R.id.forget,R.id.cb_checkbox,R.id.btn_login,R.id.btn_register})
    public void onViewClike(View view){




    }



    public void showDelectImage(EditText editText,ImageView imageView){
        //获取输入框的值，如果大于0显示删除按钮
        String s = editText.getText().toString() + "";
        if (s.length() > 0) {
            imageView.setVisibility(View.VISIBLE);
        } else{
            imageView.setVisibility(View.INVISIBLE);
        }

    }
}
