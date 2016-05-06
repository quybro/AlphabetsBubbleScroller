package com.quybro.bubble_scroller_lib;

import android.support.v7.widget.RecyclerView;

/**
 * Created by quybro on 5/6/16.
 */
public abstract class BubbleScrollerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements BubbleScroller.BubbleTextGetter{
    public abstract String getTextToShowInBubble(int position);
    public abstract void onBindViewHolder(VH holder, int position) ;
}
