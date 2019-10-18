package com.mifeng.mf.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mifeng.mf.R;

/**
 * Created by Administrator on 2016/8/26.
 */
public class MFDashView extends View {
    public static final int DEFAULT_DASH_WIDTH = 100;
    public static final int DEFAULT_LINE_WIDTH = 100;
    public static final int DEFAULT_LINE_HEIGHT = 10;
    public static final int DEFAULT_LINE_COLOR = 0x9E9E9E;

    /**
     * 虚线的方向
     */
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    /**
     * 默认为水平方向
     */
    public static final int DEFAULT_DASH_ORIENTATION = ORIENTATION_HORIZONTAL;
    /**
     * 间距宽度
     */
    private float dashWidth;
    /**
     * 线段高度
     */
    private float lineHeight;
    /**
     * 线段宽度
     */
    private float lineWidth;
    /**
     * 线段颜色
     */
    private int lineColor;
    private int dashOrientation;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int widthSize;
    private int heightSize;

    public MFDashView(Context context) {
        this(context, null);
    }

    public MFDashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MFDashView);
        dashWidth = typedArray.getDimension(R.styleable.MFDashView_dashWidth, DEFAULT_DASH_WIDTH);
        lineHeight = typedArray.getDimension(R.styleable.MFDashView_lineHeight, DEFAULT_LINE_HEIGHT);
        lineWidth = typedArray.getDimension(R.styleable.MFDashView_lineWidth, DEFAULT_LINE_WIDTH);
        lineColor = typedArray.getColor(R.styleable.MFDashView_lineColor, DEFAULT_LINE_COLOR);
        dashOrientation = typedArray.getInteger(R.styleable.MFDashView_dashOrientation, DEFAULT_DASH_ORIENTATION);
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(lineHeight);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        heightSize = MeasureSpec.getSize(heightMeasureSpec - getPaddingTop() - getPaddingBottom());
        if (dashOrientation == ORIENTATION_HORIZONTAL) {
            setMeasuredDimension(widthSize, (int) lineHeight);
        } else {
            setMeasuredDimension((int) lineHeight, heightSize);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (dashOrientation) {
            case ORIENTATION_VERTICAL:
                drawVerticalLine(canvas);
                break;
            default:
                drawHorizontalLine(canvas);
        }
    }

    public void drawHorizontalLine(Canvas canvas) {
        float totalWidth = 0;
        canvas.save();
        float[] pts = {0, 0, lineWidth, 0};
        canvas.translate(0, lineHeight / 2);
        while (totalWidth <= widthSize) {
            canvas.drawLines(pts, mPaint);
            canvas.translate(lineWidth + dashWidth, 0);
            totalWidth += lineWidth + dashWidth;
        }
        canvas.restore();
    }

    public void drawVerticalLine(Canvas canvas) {
        float totalWidth = 0;
        canvas.save();
        float[] pts = {0, 0, 0, lineWidth};
        canvas.translate(lineHeight / 2, 0);
        while (totalWidth <= heightSize) {
            canvas.drawLines(pts, mPaint);
            canvas.translate(0, lineWidth + dashWidth);
            totalWidth += lineWidth + dashWidth;
        }
        canvas.restore();
    }
}
