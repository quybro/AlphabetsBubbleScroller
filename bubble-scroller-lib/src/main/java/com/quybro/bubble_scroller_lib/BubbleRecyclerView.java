package com.quybro.bubble_scroller_lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by quybro on 5/6/16.
 */
public class BubbleRecyclerView extends LinearLayout {
    private RecyclerView mRecyclerView;
    private BubbleScroller mBubbleScroller;
    public BubbleRecyclerView(Context context) {
        super(context);
        init();
    }
    public BubbleRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public BubbleRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.bubble_view, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mBubbleScroller = (BubbleScroller) findViewById(R.id.bubbleScroller);

        mBubbleScroller.setVisibility(View.VISIBLE);
        mBubbleScroller.setRecyclerView(mRecyclerView);
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public void setLayoutManager(@NonNull LinearLayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mRecyclerView.setAdapter(adapter);
    }
}
