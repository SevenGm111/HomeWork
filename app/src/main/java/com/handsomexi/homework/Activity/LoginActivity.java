package com.handsomexi.homework.Activity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handsomexi.homework.R;
import com.handsomexi.homework.Util.showClearUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private boolean isExit = false;



    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static  String loginName;
    private static String loginPassword;

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
    @BindView(R.id.rember)
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
        init();
    }

    private void init() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRember = preferences.getBoolean("remember_info",false);
        if (isRember){
            //将账号和密码都设到文本框中
            loginName = preferences.getString("userName","");
            loginPassword = preferences.getString("userPassword","");
            etName.setText(loginName);
            etPassword.setText(loginPassword);
            rember.setChecked(true);

        }
        //显示清空按钮并清空editText
        showClearUtil.addClearListener(etName,nameClear);
        showClearUtil.addClearListener(etPassword,passwordClear);
    }


    @OnClick({R.id.forget,R.id.rember,R.id.btn_login,R.id.btn_register})
    public void onViewClike(View view){
        switch (view.getId()){
            case R.id.forget:
                //找回密码
                Toast.makeText(this, "该功能正在开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_login:
                getUser();
                if (isExit){
                    Intent intentMainActivity = new Intent(this,MainActivity.class);
                    startActivity(intentMainActivity);
                    finish();
                }


                break;
            case R.id.btn_register:
                Intent intent = new Intent(this,registerActivity.class);  //跳转到注册界面
                startActivityForResult(intent,1);
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    //将注册界面的用户名和密码填进文本框
                    String register_login_Name = data.getStringExtra("name");
                    String register_login_Password = data.getStringExtra("password");
                    etName.setText(register_login_Name);
                    etPassword.setText(register_login_Password);
                }
                break;
        }
    }

    private void getUser() {
        preferences = getSharedPreferences("userData",MODE_PRIVATE);
        String account = preferences.getString("userName","");
        String password = preferences.getString("userPassword","");
        //文本框上的
        String userName = etName.getText().toString();
        String userPassword = etPassword.getText().toString();
        if(userName.equals(account) && (userPassword.equals(password))){
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            if (rember.isChecked()){  //检查复选框是否被选中
                //记住密码
                remberPassword();
                Toast.makeText(this, "密码已保存", Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity","userName is" + account );
                Log.d("LoginActivity","uerPassword is" + password );

            }
            isExit = true;
            //Intent intent = new Intent(this,MainActivity.class);
            //startActivity(intent);
        } else {
            Toast.makeText(this, "用户账号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etPassword.setText("");
        }
    }

    private void remberPassword() {
        editor = getSharedPreferences("userData",MODE_PRIVATE).edit();
        loginName = etName.getText().toString();
        loginPassword = etPassword.getText().toString();
        editor.putString("userName",loginName);
        editor.putString("userPassword",loginPassword);
        editor.putBoolean("remember_info",true);
        editor.apply();

    }


}
