package com.mifeng.mf.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import com.mifeng.mf.R;

public class MFStickyNavLayout extends LinearLayout {
    private static final String TAG = "StickyNavLayout";
    private View mTop;
    private View mNav;
    private ViewPager mViewPager;
    private int mTopViewHeight;
    private ViewGroup mInnerScrollView;
    private boolean isTopHidden = false;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;
    private float mLastY;
    private boolean mDragging;
    private boolean isStickNav;
    private boolean isInControl = false;
    private int stickOffset;
    private int mViewPagerMaxHeight;
    private int mTopViewMaxHeight;
    private SmartRefreshLayout smartRefreshLayout;
    private List<View> viewList;
    public MFStickyNavLayout(Context context) {
        this(context, null);
    }

    public MFStickyNavLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MFStickyNavLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StickNavLayout);
        isStickNav = a.getBoolean(R.styleable.StickNavLayout_isStickNav, false);
        stickOffset = a.getDimensionPixelSize(R.styleable.StickNavLayout_stickOffset, 0);
        a.recycle();

        mScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public void setIsStickNav(boolean isStickNav) {
        this.isStickNav = isStickNav;
    }

    public void setStickNavAndScrollToNav() {
        this.isStickNav = true;
        scrollTo(0, mTopViewHeight);
    }

    public void setTopViewHeight(int height) {
        mTopViewHeight = height;
        mTopViewHeight -= stickOffset;
        if (isStickNav) {
            scrollTo(0, stickOffset);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = findViewById(R.id.id_stickynavlayout_topview);
        mNav = findViewById(R.id.id_stickynavlayout_indicator);
        View view = findViewById(R.id.id_stickynavlayout_viewpager);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException("id_stickynavlayout_viewpager show used by ViewPager !");
        } else if (mTop instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) mTop;
            if (viewGroup.getChildCount() >= 2) {
                throw new RuntimeException("if the TopView(android:id=\"R.id.id_stickynavlayout_topview\") is a ViewGroup(ScrollView,LinearLayout,FrameLayout, ....) ,the children count should be one  !");
            }
        }
        mViewPager = (ViewPager) view;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        int height = getMeasuredHeight() - mNav.getMeasuredHeight();
        mViewPagerMaxHeight = (height >= mViewPagerMaxHeight ? height : mViewPagerMaxHeight);
        params.height = height - stickOffset;
        mViewPager.setLayoutParams(params);

        int topHeight = mTop instanceof ViewGroup ? ((ViewGroup) mTop).getChildAt(0).getMeasuredHeight() : mTop.getMeasuredHeight();
        ViewGroup.LayoutParams topParams = mTop.getLayoutParams();

        mTopViewMaxHeight = (topHeight >= mTopViewMaxHeight ? topHeight : mTopViewMaxHeight);
        topParams.height = topHeight;
        mTop.setLayoutParams(topParams);
        mTopViewHeight = topParams.height - stickOffset;


        isTopHidden = getScrollY() == mTopViewHeight;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        final ViewGroup.LayoutParams params = mTop.getLayoutParams();
        mTop.post(new Runnable() {
            @Override
            public void run() {
                if (mTop instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) mTop;
                    int height = viewGroup.getChildAt(0).getHeight();
                    mTopViewHeight = height - stickOffset;
                    params.height = height;
                    mTop.setLayoutParams(params);
                    mTop.requestLayout();
                } else {
                    mTopViewHeight = mTop.getMeasuredHeight() - stickOffset;
                }
                if (null != mInnerScrollView) {
                    Log.d(TAG, "mInnerScrollViewHeight:" + mInnerScrollView.getMeasuredHeight());
                }
                if (isStickNav) {
                    scrollTo(0, mTopViewHeight);
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentScrollView();
                if (mInnerScrollView instanceof RecyclerView) {
                    RecyclerView rv = (RecyclerView) mInnerScrollView;
                    if (rv.getLayoutManager() == null) {
                        throw new IllegalStateException("RecyclerView does not have LayoutManager instance.");
                    }
                    View view = rv.getChildAt(0);
                    if (!isInControl && (view != null && view.getTop() <= 0) && isTopHidden && dy > 0) {
                        isInControl = true;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        MotionEvent ev2 = MotionEvent.obtain(ev);
                        dispatchTouchEvent(ev);
                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        isSticky = true;
                        return dispatchTouchEvent(ev2);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                float distance = y - mLastY;
                if (isSticky && Math.abs(distance) <= mTouchSlop) {
                    isSticky = false;
                    return true;
                } else {
                    isSticky = false;
                    return super.dispatchTouchEvent(ev);
                }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isSticky;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentScrollView();
                if (Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                    if (mInnerScrollView instanceof RecyclerView) {
                        RecyclerView recyclerView = (RecyclerView) mInnerScrollView;
                        View view = recyclerView.getChildAt(0);
                        if (!isTopHidden || ((view != null && view.getTop() == 0) && isTopHidden && dy > 0)) {
                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDragging = false;
                recycleVelocityTracker();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void getCurrentScrollView() {
        int currentItem = mViewPager.getCurrentItem();
        View view = viewList.get(currentItem);
        if (view != null) {
            mInnerScrollView = (view.findViewById(R.id.id_stickynavlayout_innerscrollview));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentScrollView();
                if (!mDragging && Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                }
                if (mDragging) {
                    scrollBy(0, (int) -dy);
                    if (getScrollY() == mTopViewHeight && dy < 0) {
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        isInControl = false;
                        isSticky = true;
                    } else {
                        RecyclerView recyclerView = (RecyclerView)mInnerScrollView;
                        View view = recyclerView.getChildAt(0);
                        if (view != null && view.getTop() == 0) {
                            isSticky = false;
                        }
                    }
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                recycleVelocityTracker();
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY);
                }
                recycleVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }


    public void scrollToTop() {
        scrollTo(0,mTopViewHeight);
    }
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }

        isTopHidden = getScrollY() == mTopViewHeight;

        if (listener != null) {
            listener.isStick(isTopHidden);
            listener.scrollPercent((float) getScrollY() / (float) mTopViewHeight);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (smartRefreshLayout != null) {
            if (y <= 10) {
                smartRefreshLayout.setEnableRefresh(true);
            }else {
                smartRefreshLayout.setEnableRefresh(false);
            }
        }
        if (scrollListener != null) {
            scrollListener.onScrollChanged(x, y, oldx, oldy);
        }
    }
    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }



    public void setPagerViewList(List<View> viewList) {
        this.viewList = viewList;
    }
    public void setSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
    }
    public int getStickOffset() {
        return stickOffset;
    }

    public void setStickOffset(int stickOffset) {
        this.stickOffset = stickOffset;
    }

    private onStickStateChangeListener listener;

    public interface onStickStateChangeListener {
        void isStick(boolean isStick);
        void scrollPercent(float percent);
    }
    public void setOnStickStateChangeListener(onStickStateChangeListener listener) {
        this.listener = listener;
    }

    private scrollViewListener scrollListener;

    public interface scrollViewListener {
        void onScrollChanged(int x, int y, int oldx, int oldy);
    }

    public void setScrollListener(scrollViewListener scrollListener) {
        this.scrollListener = scrollListener;
    }
}
