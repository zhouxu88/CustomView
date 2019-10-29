package com.example.zhouxu.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouxu on 2018/12/6.
 */

public class Bezier extends View {

    private List<String> xValues = new ArrayList<>();
    private List<Float> yValues = new ArrayList<>();
    private Paint mPaint;
    private int centerX, centerY;

    private PointF start, end, control;

    public Bezier(Context context) {
        this(context, null);
    }

    public Bezier(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(centerX,centerY);
        // 绘制数据点和控制点
//        mPaint.setColor(Color.GRAY);
//        mPaint.setStrokeWidth(20);
//        canvas.drawPoint(start.x, start.y, mPaint);
//        canvas.drawPoint(end.x, end.y, mPaint);
//        canvas.drawPoint(control.x, control.y, mPaint);
//
//        // 绘制辅助线
//        mPaint.setStrokeWidth(4);
//        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
//        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint);

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();

        path.moveTo(start.x, start.y);
        path.quadTo(control.x, control.y, end.x, end.y);

        canvas.drawPath(path, mPaint);
    }


    public void setXValues(List<String> values) {
        this.xValues = values;
    }

    public void setYValues(List<Float> values) {
        this.yValues = values;
        int interval = 80;
        // 初始化数据点和控制点的位置
        start.x = centerX - 200;
        start.y = centerY;

        end.x = centerX + 200;
        end.y = centerY;

        control.x = (start.x + end.x+200) / 2;
        control.y = centerY-200;

//        mPoints = new Point[yValues.size()];
//        float aver = (lableCountY - 1) * intervalY / (maxValueY - minValueY); //y轴最小单位的距离
//        for (int i = 0; i < yValues.size(); i++) {
//            Point point = new Point();
//            point.x = firstPointX + i * intervalX;
//            point.y = (int) (mHeight - paddingBottom - leftRightExtra - yValues.get(i) * aver + minValueY * aver);
//            mPoints[i] = point;
//        }
    }
}

