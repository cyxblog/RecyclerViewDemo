package com.example.recyclerviewdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.recyclerviewdemo.adapter.GridViewAdapter;
import com.example.recyclerviewdemo.adapter.ListViewAdapter;
import com.example.recyclerviewdemo.adapter.RecyclerViewBaseAdapter;
import com.example.recyclerviewdemo.adapter.StaggerAdapter;
import com.example.recyclerviewdemo.bean.Data;
import com.example.recyclerviewdemo.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mList;
    private List<ItemBean> mData;
    private RecyclerViewBaseAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到控件
        mList = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        //准备数据
        initData();
        //默认展示ListView效果
        showList(true, false);

        //处理下拉刷新
        handlerDownPullUpdate();
    }

    private void handlerDownPullUpdate() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPink, R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //执行数据刷新的操作
                //添加数据
                ItemBean data = new ItemBean();
                data.title = "我是新添加的数据";
                data.icon = R.mipmap.pic5;
                mData.add(0, data);

                //更新UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //停止刷新和更新列表
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });

    }

    /**
     * 用于初始化模拟数据
     */
    private void initData() {
        // List--->Adapter--->setAdapter--->显示数据
        //创建数据集合
        mData = new ArrayList<>();

        //创建模拟数据
        for (int i = 0; i < Data.icons.length; i++) {
            // 创建数据对象
            ItemBean data = new ItemBean();
            data.icon = Data.icons[i];
            data.title = "我是第" + i + "条";
            // 添加到集合中
            mData.add(data);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            // ListView部分
            case R.id.list_view_vertical_standard:
                Log.d(TAG, "onOptionsItemSelected: 点击了ListView垂直标准");
                showList(true, false);
                break;
            case R.id.list_view_vertical_reverse:
                Log.d(TAG, "onOptionsItemSelected: 点击了ListView垂直反向");
                showList(true, true);
                break;
            case R.id.list_view_horizontal_standard:
                Log.d(TAG, "onOptionsItemSelected: 点击了ListView水平标准");
                showList(false, false);
                break;
            case R.id.list_view_horizontal_reverse:
                Log.d(TAG, "onOptionsItemSelected: 点击了ListView水平反向");
                showList(false, true);
                break;

            // GridView部分
            case R.id.grid_view_vertical_standard:
                showGrid(true, false);
                break;
            case R.id.grid_view_vertical_reverse:
                showGrid(true, true);
                break;
            case R.id.grid_view_horizontal_standard:
                showGrid(false, false);
                break;
            case R.id.grid_view_horizontal_reverse:
                showGrid(false, true);
                break;

            //瀑布流部分
            case R.id.stagger_view_vertical_standard:
                showStagger(true, false);
                break;
            case R.id.stagger_view_vertical_reverse:
                showStagger(true, true);
                break;
            case R.id.stagger_view_horizontal_standard:
                showStagger(false, false);
                break;
            case R.id.stagger_view_horizontal_reverse:
                showStagger(false, true);
                break;

            // 多种条目类型
            case R.id.multi_type:
                //跳转到新activity
                Intent intent = new Intent(this, MultiTypeActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 实现瀑布流效果
     */
    private void showStagger(boolean isVertical, boolean isReverse) {
        //布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        //设置布局管理器
        layoutManager.setReverseLayout(isReverse);

        //设置管理器到RecyclerView
        mList.setLayoutManager(layoutManager);

        //创建适配器
        mAdapter = new StaggerAdapter(mData);
        //设置适配器
        mList.setAdapter(mAdapter);

        // 初始化点击事件
        initListener();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "你点击了第" + position + "个条目", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 用于显示GridView效果
     */
    private void showGrid(boolean isVertical, boolean isReverse) {
        //创建布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);

        //设置布局管理器
        mList.setLayoutManager(layoutManager);

        //创建适配器
        mAdapter = new GridViewAdapter(mData);
        //设置适配器
        mList.setAdapter(mAdapter);

        // 初始化点击事件
        initListener();

    }

    /**
     * 用于显示ListView效果
     */
    private void showList(boolean isVertical, boolean isReverse) {
        //设置RecyclerView样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // 设置水平or垂直
        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);
        mList.setLayoutManager(layoutManager);

        // 创建适配器
        mAdapter = new ListViewAdapter(mData);
        //设置到RecyclerView中
        mList.setAdapter(mAdapter);

        // 初始化点击事件
        initListener();

    }


}