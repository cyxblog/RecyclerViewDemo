package com.example.recyclerviewdemo.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.bean.ItemBean;

import java.util.List;

public class StaggerAdapter extends RecyclerViewBaseAdapter {
    public StaggerAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_stagger_item, null);
        return view;
    }
}
