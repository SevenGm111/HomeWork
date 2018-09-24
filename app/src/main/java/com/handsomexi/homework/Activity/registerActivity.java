package com.handsomexi.homework.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.handsomexi.homework.R;
import com.mob.MobSDK;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class registerActivity extends AppCompatActivity {

    private boolean codeResult = false;

    private static String registerPhone;
    private static String registerCode;
    private static String registerName;
    private static String registerPassword;

    private SharedPreferences.Editor editor;   //存储


    @BindView(R.id.register_account)
    EditText register_account;
    @BindView(R.id.register_password)
    EditText register_password;
    @BindView(R.id.register_phoneNumber)
    EditText register_phoneNumber;
    @BindView(R.id.check)
    Button btn_check;
    @BindView(R.id.register_code)
    EditText register_code;
    @BindView(R.id.register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        MobSDK.init(this);
        initSMSSDK();

    }

    @OnClick({R.id.check,R.id.register})
    public void onViewClike(View view){
        switch (view.getId()){
            case R.id.check:
                if (validatePhone()){
                    Log.d("registerActivity","获取验证码");
                    setEditText();
                    SMSSDK.getVerificationCode("86", registerPhone);//发送短信验证码到手机号  86表示的是中国
                    timer.start();  //使用计时器，设置验证码的时间限制
                }


                break;
                //点击注册按钮
            case R.id.register:
                setEditText();       //获取手机号码和验证码
                SMSSDK.submitVerificationCode("86", registerPhone,registerCode);//提交验证码  在eventHandler里面查看验证结果  code为验证码
                //判断用户名和密码是否符合标准
                //if ()
                break;
    }
    }

    /**
     * 验证用户的其他信息
     * 这里验证两次密码是否一致 以及验证码判断
     */
    private void submitInfo() {


        //如果验证码正确，保存用户信息，并把用户名和密码发到登录界面
        if (codeResult){
            codeResult = false;
            editor = getSharedPreferences("userData",MODE_PRIVATE).edit();
            registerName = register_account.getText().toString();
            registerPassword = register_password.getText().toString();
            editor.putString("userName",registerName);
            editor.putString("userPassword",registerPassword);
            editor.apply();
            //把用户名和密码发到登录界面
            Intent intent = new Intent();
            intent.putExtra("name",registerName);
            intent.putExtra("password",registerPassword);
            setResult(RESULT_OK,intent);
            finish();

        }
    }

    @Override
    public void onBackPressed() {
        //把用户名和密码发到登录界面
        Intent intent = new Intent();
        intent.putExtra("name",registerName);
        intent.putExtra("password",registerPassword);
        setResult(RESULT_OK,intent);
        finish();
    }

    /**
     * 使用计时器来限定验证码
     * 在发送验证码的过程 不可以再次申请获取验证码 在指定时间之后没有获取到验证码才能重新进行发送
     * 这里限定的时间是60s
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btn_check.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            btn_check.setEnabled(true);
            btn_check.setText("获取验证码");
            codeResult = false;
        }
    };

    /**
     * 验证手机号码是否符合要求，11位 并且没有注册过(以后再写)
     *
     * @return 是否符合要求
     */
    private boolean validatePhone() {
        String phone = register_phoneNumber.getText().toString().trim();
        return true;
    }

    private void setEditText() {
        registerPhone = register_phoneNumber.getText().toString();
        registerCode = register_code.getText().toString();              //获取输入的验证码
    }

    private void initSMSSDK() {
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        if (result == SMSSDK.RESULT_COMPLETE ){
                            codeResult = true;          //提交的验证码是正确的
                            submitInfo();
                        } else {
                            Toast.makeText(registerActivity.this, "验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
                            register_code.setText("");
                        }
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        if (result == SMSSDK.RESULT_COMPLETE ){
                        } else {
                        }
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();         //销毁
    }
}
