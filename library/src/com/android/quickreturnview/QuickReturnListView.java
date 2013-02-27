package com.android.quickreturnview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ListView;

public class QuickReturnListView extends ListView {

    public View quickReturnView;
    private int mLastOffset;
    private int mCachedVerticalScrollRange;
    private int mQuickReturnHeight;
    private int mScrollY;
    private boolean noAnimation;
    private boolean isOnScreen = true;
    private TranslateAnimation anim;
    private QuickReturnViewType returnViewType;
    private OnScrollListener listener;

    public QuickReturnListView(Context context) {
        super(context);
    }

    public QuickReturnListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuickReturnListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setQuickReturnView(final View quickReturnView) {
        this.quickReturnView = quickReturnView;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mCachedVerticalScrollRange = -1;
                mQuickReturnHeight = quickReturnView.getHeight();
                mCachedVerticalScrollRange = computeVerticalScrollRange();
            }
        });
    }

    @Override
    public void setOnScrollListener(final OnScrollListener l) {
        listener = l;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (listener != null) {
                    listener.onScrollStateChanged(absListView, i);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                if (listener != null) {
                    listener.onScroll(absListView, i, i2, i3);
                }

                Boolean isScrollUp = null;
                if (mCachedVerticalScrollRange != -1) {
                    int computedScrollY = getComputedScrollY();
                    isScrollUp = mScrollY == computedScrollY ? null : mScrollY > computedScrollY;
                    mScrollY = computedScrollY;
                }

                if (isScrollUp == null) {
                    return;
                }
                if (isScrollUp && !isOnScreen && !noAnimation) {
                    noAnimation = true;
                    anim = new TranslateAnimation(0, 0, mQuickReturnHeight, 0);
                    anim.setFillAfter(true);
                    anim.setDuration(200);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isOnScreen = true;
                            noAnimation = false;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    quickReturnView.startAnimation(anim);
                } else if (!isScrollUp && isOnScreen && !noAnimation) {
                    noAnimation = true;
                    anim = new TranslateAnimation(0, 0, 0, mQuickReturnHeight);
                    anim.setFillAfter(true);
                    anim.setDuration(200);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isOnScreen = false;
                            noAnimation = false;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    quickReturnView.startAnimation(anim);
                }
            }
        });
    }

    public int getComputedScrollY() {
        return computeVerticalScrollOffset() - mLastOffset;
    }

    public void setReturnViewType(QuickReturnViewType returnViewType) {
        this.returnViewType = returnViewType;
    }
}
