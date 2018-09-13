package com.handsomexi.homework.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handsomexi.homework.Activity.ImageViewActivity;
import com.handsomexi.homework.Adapter.SpinnerAdapter;
import com.handsomexi.homework.Bean.HomeWorkBean;
import com.handsomexi.homework.R;
import com.handsomexi.homework.Util.SqlUtil;
import com.handsomexi.homework.Util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WrongFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Unbinder unbinder;

    List<HomeWorkBean> beans;
    WrongListAdapter adapter;


    @BindArray(R.array.all_subject)
    String[] a1;
    @BindArray(R.array.all_shcoolyear)
    String[] a2;
    @BindArray(R.array.all_semester)
    String[] a3;

    @BindView(R.id.spinner3)
    Spinner spinner3;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.wrong_refresh_listview)
    ListView listview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wrong, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListview();
        setSpinner();


    }

    void setListview() {
        beans = SqlUtil.query("全部", "全部", "全部");
        adapter = new WrongListAdapter();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), ImageViewActivity.class);
            intent.putExtra("path", beans.get(position).getImagePath());
            startActivity(intent);
        });
    }

    void setSpinner() {
        spinner1.setAdapter(new SpinnerAdapter(getContext(), a1));
        spinner2.setAdapter(new SpinnerAdapter(getContext(), a2));
        spinner3.setAdapter(new SpinnerAdapter(getContext(), a3));
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String subject = getItem(spinner1);
        String schoolYear = getItem(spinner2);
        String sem = getItem(spinner3);
        beans = SqlUtil.query(subject,schoolYear,sem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class WrongListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public Object getItem(int position) {
            return beans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            ViewHolder holder;
            if (null == view) {
                view = getActivity().getLayoutInflater().inflate(R.layout.item_wrong, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else
                holder = (ViewHolder) view.getTag();
            HomeWorkBean bean = beans.get(i);
            Glide.with(WrongFragment.this)
                    .load(new File(bean.getImagePath()))
                    .asBitmap()
                    .thumbnail( 0.4f )
                    .centerCrop()
                    .into(holder.itemWrongImg);
            holder.itemWrongInfo.setText(bean.getSubject() + "\n\n" + bean.getSchoolYear() + bean.getSemester());
            holder.itemWrongTime.setText(Util.long2Date(bean.getTime()));

            return view;
        }

        class ViewHolder {
            @BindView(R.id.item_wrong_img)
            ImageView itemWrongImg;
            @BindView(R.id.item_wrong_info)
            TextView itemWrongInfo;
            @BindView(R.id.item_wrong_time)
            TextView itemWrongTime;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change(HomeWorkBean bean){
        beans = SqlUtil.query(bean.getSubject(),bean.getSchoolYear(),bean.getSemester());
        adapter.notifyDataSetChanged();
    }

    private String getItem(Spinner spinner){
        return spinner.getSelectedItem().toString();
    }

}
