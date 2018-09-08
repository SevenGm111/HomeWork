package com.handsomexi.homework.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.handsomexi.homework.Bean.HomeWorkBean;
import com.handsomexi.homework.R;
import com.handsomexi.homework.Util.SqlUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WrongFragment extends Fragment {
    @BindView(R.id.wrong_refresh_listview)
    SwipeMenuListView listview;
    @BindView(R.id.wrong_refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;

    List<HomeWorkBean> beans;
    WrongListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_wrongfragment, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        beans = SqlUtil.query("全部",0,0);
        adapter = new WrongListAdapter();
        listview.setAdapter(adapter);


    }

    @Subscribe
    public void onEvent(HomeWorkBean bean) {
        beans = SqlUtil.query(bean.getSubject(), bean.getSchoolYear(), bean.getSemester());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
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
            if(null == view){
                view = getActivity().getLayoutInflater().inflate(R.layout.item_wrong, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }else
                holder = (ViewHolder) view.getTag();
            Picasso.get().load(new File(beans.get(i).getImagePath())).into(holder.itemWrongImg);

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


}
