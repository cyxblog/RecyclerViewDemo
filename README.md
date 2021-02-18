# RecyclerViewDemo
安卓开发的RecyclerView部分
# 1 RecyclerView简介
<font size=4 > [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)是Android开发中强大的控件，可以实现ListView、GridView以及StaggeredView（瀑布流），并支持垂直滚动和水平滚动两种方式。
本文介绍这三种View的垂直和水平滚动实现方法。</font>

# 2 RecyclerView的实现
<font size=4 >RecyclerView的实现由三个步骤组成：
+ 使用布局管理器确定布局
+ 设计布局条目的外观和点击事件
+ 定义指定布局的Adapter显示数据 </font>

## 2.1 列表布局的实现
<font size=4 >

<video id="video" controls="" preload="none" >
    <source id="mp4" src=/assets/RecyclerView.mp4 type="video/mp4">
</video>

+ 设置RecyclerView样式为列表布局，并设置垂直或水平布局
```java
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
```
+ 定义Adapter，重构后的BaseAdapter，三种布局Adapter均继承之
```java
public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerViewBaseAdapter.InnerHolder> {
    private final List<ItemBean> mData;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewBaseAdapter(List<ItemBean> data) {
        this.mData = data;
    }

    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        return new InnerHolder(view);
    }

    protected abstract View getSubView(ViewGroup parent, int viewType);

    /**
     * 这个方法用来绑定holder，设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBaseAdapter.InnerHolder holder, int position) {
        // 在这里设置数据
        holder.setData(mData.get(position), position);
    }

    /**
     * 返回条目的数目
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        //设置一个接口
        this.mOnItemClickListener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private final ImageView mIcon;
        private final TextView mTitle;
        private int mPosition;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            // 找到条目控件
            mIcon = itemView.findViewById(R.id.icon);
            mTitle = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mPosition);
                    }
                }
            });
        }

        public void setData(ItemBean itemBean, int position) {
            this.mPosition = position;
            mIcon.setImageResource(itemBean.icon);
            mTitle.setText(itemBean.title);
        }
    }
}
```
```java
public class ListViewAdapter extends RecyclerViewBaseAdapter {

    public ListViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        return view;
    }
}
```

</font>

## 2.2 网格布局的实现
<font size=4 >

+ 设置RecyclerView样式为网格布局，并设置垂直或水平布局
```java
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
```
+ GridViewAdapter
```java
public class GridViewAdapter extends RecyclerViewBaseAdapter {

    public GridViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_grid_view, null);
        return view;
    }
}
```

</font>

## 2.3 瀑布流布局的实现
<font size=4 >

+ 设置RecyclerView样式为瀑布流布局，并设置垂直或水平布局
```java
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
```
+ StaggerAdapter
```java
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
```
</font>

## 多种Item样式共同显示
<font size=4 >

+ 这种布局主要考虑Adapter的定义
```java
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
```
</font>

## 完整代码见[github](https://github.com/cyxblog/RecyclerViewDemo)
