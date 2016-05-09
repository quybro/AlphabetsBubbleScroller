package com.quybro.bubble_scroller_lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * Created by quybro on 5/6/16.
 */
public class BubbleScroller extends LinearLayout {
    private static final int BUBBLE_ANIMATION_DURATION = 100;
    private static final int TRACK_SNAP_RANGE = 5;

    private RecyclerView mRecyclerView;
    private final ScrollListener mScrollListener = new ScrollListener();
    private ObjectAnimator mCurrentAnimator = null;
    private TextView mScrollbarBubble;
    private View mScrollbarThumb;
    private int mHeight;

    public BubbleScroller(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public BubbleScroller(final Context context) {
        super(context);
        initialize(context);
    }

    public BubbleScroller(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(HORIZONTAL);
        setClipChildren(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.bubble_thumb, this, true);
        mScrollbarBubble = (TextView) findViewById(R.id.bubble);
        mScrollbarThumb = findViewById(R.id.thumb);
        mScrollbarBubble.setVisibility(INVISIBLE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < mScrollbarThumb.getX()) {
                    return false;
                }
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }
                if (mScrollbarBubble.getVisibility() == INVISIBLE) {
                    showBubble();
                }
                mScrollbarThumb.setSelected(true);
            case MotionEvent.ACTION_MOVE:
                final float y = event.getY();
                setBubbleAndHandlePosition(y);
                setRecyclerViewPosition(y);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mScrollbarThumb.setSelected(false);
                hideBubble();
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    private void setRecyclerViewPosition(float y) {
        if (mRecyclerView != null) {
            int itemCount = mRecyclerView.getAdapter().getItemCount();
            float proportion;
            if (mScrollbarThumb.getY() == 0) {
                proportion = 0f;
            }
            else if (mScrollbarThumb.getY() + mScrollbarThumb.getHeight() >= mHeight - TRACK_SNAP_RANGE) {
                proportion = 1f;
            }
            else {
                proportion = y / (float) mHeight;
            }
            int targetPos = getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(targetPos, 0);
            String bubbleText = ((TextGetter) mRecyclerView.getAdapter()).getText(targetPos);
            mScrollbarBubble.setText(bubbleText);
        }
    }

    private int getValueInRange(int min, int max, int value) {
        int minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }

    private void setBubbleAndHandlePosition(float y) {
        int bubbleHeight = mScrollbarBubble.getHeight();
        int handleHeight = mScrollbarThumb.getHeight();
        mScrollbarThumb.setY(getValueInRange(0, mHeight - handleHeight, (int) (y - handleHeight / 2)));
        mScrollbarBubble.setY(getValueInRange(0, mHeight - bubbleHeight - handleHeight / 2, (int) (y - bubbleHeight)));
    }

    private void showBubble() {
        mScrollbarBubble.setVisibility(VISIBLE);
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }
        mCurrentAnimator = ObjectAnimator.ofFloat(mScrollbarBubble, "alpha", 0f, 1f).setDuration(BUBBLE_ANIMATION_DURATION);
        mCurrentAnimator.start();
    }

    private void hideBubble() {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }
        mCurrentAnimator = ObjectAnimator.ofFloat(mScrollbarBubble, "alpha", 1f, 0f).setDuration(BUBBLE_ANIMATION_DURATION);
        mCurrentAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mScrollbarBubble.setVisibility(INVISIBLE);
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mScrollbarBubble.setVisibility(INVISIBLE);
                mCurrentAnimator = null;
            }
        });
        mCurrentAnimator.start();
    }

    private class ScrollListener extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView rv, int dx, int dy) {
            if (mScrollbarThumb.isSelected()) {
                return;
            }
            View firstVisibleView = mRecyclerView.getChildAt(0);
            int firstVisibleItemPosition = mRecyclerView.getChildLayoutPosition(firstVisibleView);
            int childrenCount = mRecyclerView.getChildCount();
            int lastVisibleItemPosition = firstVisibleItemPosition + childrenCount;
            int itemCount = mRecyclerView.getAdapter().getItemCount();
            int position;
            if (firstVisibleItemPosition == 0) {
                position = 0;
            }
            else if (lastVisibleItemPosition == itemCount) {
                position = itemCount;
            }
            else {
                position = (int) (((float) firstVisibleItemPosition / (((float) itemCount - (float) childrenCount))) * (float) itemCount);
            }
            float proportion = (float) position / (float) itemCount;
            setBubbleAndHandlePosition(mHeight * proportion);
        }
    }

    public interface TextGetter {
        String getText(int pos);
    }
}