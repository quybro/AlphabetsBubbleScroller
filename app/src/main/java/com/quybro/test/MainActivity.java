package com.quybro.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.quybro.bubble_scroller_lib.BubbleRecyclerView;

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
