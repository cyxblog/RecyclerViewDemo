package com.example.recyclerviewdemo.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.bean.MultiTypeBean;

import java.util.List;

public class MultiTypeAdapter extends RecyclerView.Adapter {

    //定义三种条目类型
    public static final int TYPE_FULL_IMAGE = 0;
    public static final int TYPE_RIGHT_IMAGE = 1;
    public static final int TYPE_THREE_IMAGES = 2;

    private final List<MultiTypeBean> mData;

    public MultiTypeAdapter(List<MultiTypeBean> data){
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == TYPE_FULL_IMAGE){
            view = View.inflate(parent.getContext(), R.layout.item_type_full_img, null);
            return new FullImageHolder(view);
        }else if(viewType == TYPE_RIGHT_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_type_right_img, null);
            return new RightImageHolder(view);
        }else{
            view = View.inflate(parent.getContext(), R.layout.item_type_three_img, null);
            return new ThreeImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    //复写getItemViewType，返回条目类型
    @Override
    public int getItemViewType(int position) {
        MultiTypeBean multiTypeBean = mData.get(position);
        return multiTypeBean.type;
    }

    private class FullImageHolder extends RecyclerView.ViewHolder{

        public FullImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class RightImageHolder extends RecyclerView.ViewHolder{

        public RightImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class ThreeImageHolder extends RecyclerView.ViewHolder{

        public ThreeImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
