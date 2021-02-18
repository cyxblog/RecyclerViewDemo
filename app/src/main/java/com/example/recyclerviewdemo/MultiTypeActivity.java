package com.example.recyclerviewdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.adapter.MultiTypeAdapter;
import com.example.recyclerviewdemo.bean.Data;
import com.example.recyclerviewdemo.bean.MultiTypeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<MultiTypeBean> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);

        //找到控件
        recyclerView = findViewById(R.id.multi_type_view);

        //准备数据
        initData();

        //创建和设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //创建适配器
        MultiTypeAdapter adapter = new MultiTypeAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        //准备数据
        data = new ArrayList<>();
        //随机产生条目类型
        Random random = new Random();

        for (int i = 0; i < Data.icons.length; i++) {
            MultiTypeBean dataItem = new MultiTypeBean();
            dataItem.icon = Data.icons[i];
            dataItem.type = random.nextInt(3);
            data.add(dataItem);
        }

    }
}
