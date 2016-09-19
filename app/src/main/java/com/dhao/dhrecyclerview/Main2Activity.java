package com.dhao.dhrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhao.library.DHItemDecoration;
import com.dhao.library.DHRecyclerView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private DHRecyclerView mRecyclerView;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mRecyclerView= (DHRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DHItemDecoration());

        final MyAdapter myAdapter=new MyAdapter();
        for(int i=0;i<15;i++){
            myAdapter.data.add("先定个能实现的小目标,比方说先挣它一个亿"+i);
        }

        View view=View.inflate(this,R.layout.view_more_progress,null);
        mRecyclerView.addFooterView(view);

        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setOnLoadMoreListener(new DHRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<5;i++){
                            myAdapter.data.add(i+"ok");
                        }
                        myAdapter.notifyDataSetChanged();
                        mRecyclerView.setLoadMoreComplete();
                        if (myAdapter.getItemCount()>40){
                            mRecyclerView.setNoMore();
                        }
                    }
                },3000);
            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        public ArrayList<String> data=new ArrayList<>();
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item,parent,false);

            return new MyViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.txt.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt= (TextView) itemView.findViewById(R.id.txt);
        }
    }
}
