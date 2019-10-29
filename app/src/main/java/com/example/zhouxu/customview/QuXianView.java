package com.example.zhouxu.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouxu on 2018/12/4.
 */

public class QuXianView extends View {

    private static final String TAG = "LineView";
    private static final float SMOOTHNESS = 0.5f;
    private int[] yLables = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42}; //固定y轴数字
    private Context mContext;
    //曲线上的数据点的集合
    private List<PointDataBean> mPointDataBeanList = new ArrayList<>();
    private int mWidth;
    private int mHeight;
    private int originX; // 原点x坐标
    private int originY; // 原点y坐标
    private int intervalX = 130; // 坐标刻度的间隔
    private int intervalY = 80; // y轴刻度的间隔
    private int paddingTop = 40;// 默认上下左右的padding
    private int paddingLeft = dip2px(80);
    private int paddingRight = dip2px(30);
    private int paddingBottom = 80;
    private int firstPointX; //第一个点x坐标
    private int xScaleHeight = dip2px(6); // x轴刻度线高度
    private int xyTextSize = sp2px(10); //xy轴文字大小
    private int textToXYAxisGap = dip2px(10); // xy轴的文字距xy线的距离
    private int lableCountY = yLables.length; // Y轴刻度个数
    private int leftRightExtra = intervalX / 3; //x轴左右向外延伸的长度
    private float minValueY = 32; // y轴最小值
    private float maxValueY = 42; // y轴最大值
    private int bigCircleR = 7; //折线图中的圆圈
    private int smallCircleR = 5; //折线图中为了避免折线穿透的圆圈

    private GestureDetector gestureDetector; //滑动手势
    private int firstMinX; // 移动时第一个点的最小x值
    private int firstMaxX; //移动时第一个点的最大x值
    private int backGroundColor = Color.parseColor("#1ECC99"); // view的背景颜色
    private Paint paintWhite, linePaint, paintRed,
            paintBack, paintText, dashPaint, touchPointPaint, textPaint;
    /**
     * 计算后的x，y坐标
     */
    private List<PointF> mPoints = new ArrayList<>();
    private List<PointF> mShowPoints = new ArrayList<>();
    private List<PointF> mControlPointList = new ArrayList<>();
    private int xIntervalPointCount = 4;
    private PointDataBean pointDataBean;


    public QuXianView(Context context) {
        this(context, null);
    }

    public QuXianView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuXianView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
        gestureDetector = new GestureDetector(context, new MyOnGestureListener());
    }

    private void initPaint() {
        paintWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintWhite.setColor(Color.WHITE);
        paintWhite.setStyle(Paint.Style.STROKE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);

        paintBack = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBack.setColor(Color.parseColor("#1ECC99"));
        paintBack.setStyle(Paint.Style.FILL);

        paintRed = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(3f);
        paintRed.setStyle(Paint.Style.STROKE);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(xyTextSize);
        paintText.setStrokeWidth(2f);

        dashPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dashPaint.setColor(Color.WHITE);
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setStrokeWidth(1f);

        touchPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        touchPointPaint.setColor(Color.WHITE);
        touchPointPaint.setStyle(Paint.Style.FILL);
        touchPointPaint.setStrokeWidth(3f);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#1ECC99"));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(16);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG, "onLayout: ");
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();

            originX = paddingLeft - leftRightExtra;
            originY = mHeight - paddingBottom;

            firstPointX = paddingLeft;
            firstMinX = mWidth - originX - (mPointDataBeanList.size() - 1) * intervalX - leftRightExtra;
            // 滑动时，第一个点x值最大为paddingLeft，在大于这个值就不能滑动了
            firstMaxX = firstPointX;
            setBackgroundColor(backGroundColor);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw: ");
        //注意：drawLine和drawY位置不能改变
        getPoints();
//        calculateControlPoint();
        drawX(canvas);
        drawLine(canvas);
        drawY(canvas);
        if (pointDataBean != null) {
            drawTouchPoint(canvas);
        }

    }

    /**
     * 画x轴
     *
     * @param canvas
     */
    private void drawX(Canvas canvas) {
        Path path = new Path();
        path.moveTo(originX, originY);
        // x轴线
        path.lineTo(mWidth - paddingRight, originY);
        canvas.drawPath(path, paintWhite);

        int viewHeight = (lableCountY - 1) * intervalY + leftRightExtra;
        String time = "";
        for (int i = 0; i < mPointDataBeanList.size(); i++) {
            // 画与y轴平行的网格线
            canvas.drawLine(firstPointX + i * intervalX, originY, firstPointX + i * intervalX, originY - viewHeight, paintWhite);
            // x轴上的文字
            canvas.drawText(mPointDataBeanList.get(i).getTime(), firstPointX + i * intervalX - getTextWidth(paintText, "17.01") / 2,
                    originY + dip2px(20), paintText);
        }


        //画与x轴平行的网格线
        Path path1 = new Path();
        for (int i = 0; i < lableCountY; i++) {
            path1.moveTo(originX, mHeight - paddingBottom - leftRightExtra - i * intervalY);
            path1.lineTo(mWidth - paddingRight, mHeight - paddingBottom - leftRightExtra - i * intervalY);
        }
        canvas.drawPath(path1, dashPaint);
    }


    /**
     * 画y轴
     *
     * @param canvas
     */
    private void drawY(Canvas canvas) {
//        canvas.save();
        Path path = new Path();
        path.moveTo(originX, originY);

        for (int i = 0; i < lableCountY; i++) {
            // y轴线
            path.lineTo(originX, mHeight - paddingBottom - leftRightExtra - i * intervalY);
            // y轴文字
            canvas.drawText(String.valueOf(yLables[i]), originX - dip2px(25),
                    mHeight - paddingBottom - leftRightExtra - i * intervalY + getTextHeight(paintText, "00.00") / 2, paintText);
        }


        //y轴最后一个刻度的位置
        int lastPointY = mHeight - paddingBottom - leftRightExtra - (lableCountY - 1) * intervalY;
        // 箭头位置，y轴最后一个点后，需要额外加上一小段，就是一个半leftRightExtra的长度
        int lastY = lastPointY - leftRightExtra - leftRightExtra / 2;
        // y轴箭头
        canvas.drawLine(originX, lastPointY, originX, lastY, paintWhite);

        canvas.drawPath(path, paintWhite);


        // 截取折线超出部分（右边）
        paintBack.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        RectF rectF = new RectF(mWidth - paddingRight, 0, mWidth, mHeight);
        canvas.drawRect(rectF, paintBack);
//        canvas.restore();
    }

    /**
     * 画曲线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
//        canvas.save();
//        float t = 0.5f;//比例
        PointF startp = new PointF();
        PointF endp = new PointF();
        Path path = new Path();
        path.moveTo(mShowPoints.get(0).x, mShowPoints.get(0).y);
        for (int i = 0; i < mShowPoints.size() - 1; i++) {
            startp = mShowPoints.get(i);
            endp = mShowPoints.get(i + 1);
            float wt = (startp.x + endp.x) / 2;
            PointF p3 = new PointF();
            PointF p4 = new PointF();
            p3.y = startp.y;
//            p3.x = startp.x + (endp.x - startp.x) * t;
            p3.x = wt;
            p4.y = endp.y;
//            p4.x = startp.x + (endp.x-startp.x) * (1 - t);
            p4.x = wt;

            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
            canvas.drawPath(path, linePaint);
        }


        //将折线超出x轴坐标的部分截取掉（左边）
        paintBack.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        RectF rectF = new RectF(0, 0, originX, mHeight);
        canvas.drawRect(rectF, paintBack);
        canvas.restore();
    }

    /**
     * 点击曲线上的点，显示View
     */
    private void drawTouchPoint(Canvas canvas) {
        float pointX = pointDataBean.getX();
        float pointY = pointDataBean.getY();
        //画背景
        int rectWidth = 68;
        int rectHeight = 32;
        int paddingBottom = 10;
        int bgPointCenterX = (int) pointX;
        int bgPointCenterY = (int) pointY - rectHeight / 2 - paddingBottom;
        int radius = rectHeight / 2;
        RectF rectF = new RectF(bgPointCenterX - rectWidth / 2, bgPointCenterY - rectHeight / 2,
                bgPointCenterX + rectWidth / 2, bgPointCenterY + rectHeight / 2);
        canvas.drawRect(rectF, touchPointPaint);
        canvas.drawCircle(bgPointCenterX - rectWidth / 2, bgPointCenterY, radius, touchPointPaint);
        canvas.drawCircle(bgPointCenterX + rectWidth / 2, bgPointCenterY, radius, touchPointPaint);

        Path path = new Path();
        path.moveTo(bgPointCenterX - 15, bgPointCenterY + rectHeight / 2);
        path.lineTo(pointX, pointY);
        path.lineTo(bgPointCenterX + 15, bgPointCenterY + rectHeight / 2);
        canvas.drawPath(path, touchPointPaint);

        //画文字
        String value = String.valueOf(pointDataBean.getValue());
        canvas.drawText(value, bgPointCenterX - getTextWidth(textPaint, value) / 2, bgPointCenterY + getTextHeight(textPaint, value) / 2, textPaint);

        //画直线
        Path path1 = new Path();
        path1.moveTo(pointX, originY);
        path1.lineTo(pointX, paddingTop + leftRightExtra);
        canvas.drawPath(path1, paintWhite);

        //画时间
        RectF rectF1 = new RectF(bgPointCenterX - rectWidth / 2, originY - rectHeight / 2,
                bgPointCenterX + rectWidth / 2, originY + rectHeight / 2);
        canvas.drawRoundRect(rectF1, 20, 20, touchPointPaint);
        //画文字
        String time = pointDataBean.getTime();
        canvas.drawText(time, bgPointCenterX - getTextWidth(textPaint, time) / 2, originY + getTextHeight(textPaint, time) / 2, textPaint);

    }


    /**
     * 手势事件
     */
    class MyOnGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            // 按下事件
            float x = e.getX();
            float y = e.getY();
            pointDataBean = new PointDataBean();
            Log.i(TAG, "onDown x-----> : " + e.getX());
            Log.i(TAG, "onDown y----- : " + y);
            for (PointDataBean dataBean : mPointDataBeanList) {
                if (dataBean.getX() - e.getX() <= 0) {
                    pointDataBean.setTime(dataBean.getTime());
                    pointDataBean.setValue(dataBean.getValue());
                    pointDataBean.setX(x);
                    pointDataBean.setY(y);

//                    pointDataBean = dataBean;
//                    pointDataBean.setX(x);
//                    pointDataBean.setY(y);
                    invalidate();
                }
            }
            return true;
        }

        // 按下停留时间超过瞬时，并且按下时没有松开或拖动，就会执行此方法
        @Override
        public void onShowPress(MotionEvent motionEvent) {
            Log.i(TAG, "onShowPress: ");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            // 单击抬起
            Log.i(TAG, "onSingleTapUp: ");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // e1：第1个ACTION_DOWN MotionEvent
            // e2：最后一个ACTION_MOVE MotionEvent

            Log.i(TAG, "onScroll: ");
            if (e1.getX() > originX && e1.getX() < mWidth - paddingRight &&
                    e1.getY() > paddingTop && e1.getY() < mHeight - paddingBottom) {
                //注意：这里的distanceX是e1.getX()-e2.getX()
                Log.i(TAG, "onScroll distanceX : " + distanceX);
                distanceX = -distanceX;
                if (firstPointX + distanceX > firstMaxX) {
                    firstPointX = firstMaxX;
                } else if (firstPointX + distanceX < firstMinX) {
                    firstPointX = firstMinX;
                } else {
                    firstPointX = (int) (firstPointX + distanceX);
                }
                invalidate();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            // 长按事件
            Log.i(TAG, "onLongPress: ");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(TAG, "onFling: ");
            return false;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        if (mPointDataBeanList.size() < 7) {
            return false;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * 设置曲线的数据源
     *
     * @param pointDataBeanList
     */
    public void setYValues(List<PointDataBean> pointDataBeanList) {
        this.mPointDataBeanList = pointDataBeanList;
        for (int i = 0; i < pointDataBeanList.size(); i++) {
            PointDataBean pointDataBean = pointDataBeanList.get(i);
            float value = pointDataBean.getValue();
            // 找出y轴的最大最小值
            if (value > maxValueY) {
                maxValueY = value;
            }
            if (value < minValueY) {
                minValueY = value;
            }
        }

    }

    /**
     * 计算数据源的坐标
     */
    private void getPoints() {
        mPoints.clear();
        mShowPoints.clear();
        float aver = (lableCountY - 1) * intervalY / (maxValueY - minValueY); //y轴最小单位的距离
        for (int i = 0; i < mPointDataBeanList.size(); i++) {
            PointDataBean pointDataBean = mPointDataBeanList.get(i);
            float value = pointDataBean.getValue();
            //x坐标间距之间包含5个点（包含2端）
            int pw = firstPointX + (i * intervalX) / 4; //数据点x坐标
            //数据点y坐标
            int ph = (int) (mHeight - paddingBottom - leftRightExtra - value * aver + minValueY * aver);
            //设置折线上数据点的坐标
            pointDataBean.setX(pw);
            pointDataBean.setY(ph);

            mPoints.add(new PointF(pw, ph));

            if (i % 4 == 0) {
                mShowPoints.add(new PointF(pw, ph));
            }

        }
    }


    public int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    /**
     * sp转换px
     */
    public int sp2px(int spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取文字的宽度
     *
     * @param paint
     * @param text
     * @return
     */
    private int getTextWidth(Paint paint, String text) {
        return (int) paint.measureText(text);
    }

    /**
     * 获取文字的高度
     *
     * @param paint
     * @param text
     * @return
     */
    private int getTextHeight(Paint paint, String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }


}
