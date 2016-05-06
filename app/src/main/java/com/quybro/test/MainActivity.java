package com.quybro.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.quybro.bubble_scroller_lib.BubbleRecyclerView;
import com.quybro.bubble_scroller_lib.BubbleScroller;

public class MainActivity extends AppCompatActivity {
    private BubbleRecyclerView mBubbleRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBubbleRecyclerView = (BubbleRecyclerView) findViewById(R.id.bubble_recyclerView);
        setupBubbleRecyclerView();
    }

    private void setupBubbleRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBubbleRecyclerView.setLayoutManager(layoutManager);
        mBubbleRecyclerView.setAdapter(new BubbleAdapter());
    }
}
