package com.dhao.library;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DongHao on 2016/9/19.
 * Description:
 */
public class DHRecyclerView extends RecyclerView {
    private boolean isLoadMore;
    private boolean isNoMore;
    private View footerView;
    private DHAdapter mAdapter;
    private ArrayList<View> footers = new ArrayList<>();
    private OnLoadMoreListener onLoadMoreListener;

    public DHRecyclerView(Context context) {
        super(context);
    }

    public DHRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DHRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        onLoadMoreListener = listener;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);

        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //判断是否最后一item个显示出来
                LayoutManager layoutManager = getLayoutManager();

                //可见的item个数
                int visibleChildCount = layoutManager.getChildCount();
                if (visibleChildCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoadMore & !isNoMore) {
                    View lastVisibleView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int lastVisiblePosition = recyclerView.getChildLayoutPosition(lastVisibleView);
                    if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                        footerView.setVisibility(VISIBLE);
                        isLoadMore = true;
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                    } else {
                        footerView.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (footers.size() == 0) {
            LinearLayout footerLayout = new LinearLayout(getContext());
            footerLayout.setGravity(Gravity.CENTER);
            footerLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            footers.add(footerLayout);
            footerLayout.setPadding(0, 20, 0, 20);
            footerLayout.addView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall));

            TextView text = new TextView(getContext());
            text.setText("正在加载...");
            footerLayout.addView(text);

            footerView = footerLayout;
            footerView.setVisibility(GONE);
        } else {
            footerView = footers.get(0);
            footerView.setVisibility(GONE);
        }
        mAdapter = new DHAdapter(footers, adapter);

        super.setAdapter(mAdapter);
    }

    public void addFooterView(View view) {
        footers.clear();
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        footers.add(view);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreComplete() {
        footerView.setVisibility(GONE);
        isLoadMore = false;
    }

    public void setNoMore() {
        footerView.setVisibility(GONE);
        footerView=null;
        footers.clear();
        isLoadMore = false;
        isNoMore = true;
    }
}
