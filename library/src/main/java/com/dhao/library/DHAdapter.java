package com.dhao.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by DongHao on 2016/9/19.
 * Description:
 */
public class DHAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<View> footViews=new ArrayList<>();
    public RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    public DHAdapter(ArrayList<View> footViews, RecyclerView.Adapter adapter) {
        this.footViews = footViews;
        this.adapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==RecyclerView.INVALID_TYPE-1){
            return new RecyclerView.ViewHolder(footViews.get(0)){};
        }
        return adapter.createViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemCount()-position>footViews.size()){
            adapter.onBindViewHolder(holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount()-position<=footViews.size()){
            return RecyclerView.INVALID_TYPE-1;
        }
        return adapter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    public int getCount(){
        int count=footViews.size();
        if(adapter!=null){
            count+=adapter.getItemCount();
        }
        return count;
    }
}
