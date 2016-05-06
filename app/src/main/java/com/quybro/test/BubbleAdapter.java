package com.quybro.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quybro.bubble_scroller_lib.BubbleScroller;
import com.quybro.bubble_scroller_lib.BubbleScrollerAdapter;

import java.util.ArrayList;

/**
 * Created by quybro on 5/3/16.
 */
public class BubbleAdapter extends BubbleScrollerAdapter<BubbleAdapter.ItemHolder>{
    private final String mPattern = "ThequickbrownfoxjumpsoverthelazydogThequickbrownfoxjumpsoverthelazydog";
    private ArrayList<String> mNames = new ArrayList<>();
    public BubbleAdapter(){
        for (int i = 0; i < mPattern.length() - 1; i++) {
            mNames.add(mPattern.substring(i, mPattern.length() - 1));
        }
    }
    @Override
    public BubbleAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample, null));
    }

    @Override
    public void onBindViewHolder(BubbleAdapter.ItemHolder holder, int position) {
        holder.title.setText(mNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mNames.size() - 1;
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        return mNames.get(pos).substring(0, 1);
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        public ItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
