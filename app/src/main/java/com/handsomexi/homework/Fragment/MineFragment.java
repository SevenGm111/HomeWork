package com.handsomexi.homework.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.handsomexi.homework.Activity.LoginActivity;
import com.handsomexi.homework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MineFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.login)
    Button loginButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,null);
        unbinder = ButterKnife.bind(this,view);                   //
        return  view;
    }

    @OnClick(R.id.login)
    public void onViewClicked(View view){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

    }

}
