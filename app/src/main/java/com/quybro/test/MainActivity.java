package com.quybro.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.quybro.bubble_scroller_lib.BubbleScroller;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BubbleScroller fastScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        fastScroller = (BubbleScroller) findViewById(R.id.fastscroller);

        setLayoutManager();
        setupRecyclerView();
    }

    private void setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fastScroller.setVisibility(View.VISIBLE);
        fastScroller.setRecyclerView(recyclerView);
    }

    private void setupRecyclerView(){
        recyclerView.setAdapter(new BubbleAdapter());
    }
}
