package com.example.zhouxu.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by zhouxu on 2018/12/6.
 */

public class DrawLineChart extends View {
    private static final String TAG = "DrawLineChart";
    /**View宽度*/
    private int mViewWidth;
    /** View高度*/
    private int mViewHeight;
    /**边框线画笔*/
    private Paint mBorderLinePaint;
    /**文本画笔*/
    private Paint mTextPaint;
    /**要绘制的折线线画笔*/
    private Paint mBrokenLinePaint;
    /**折线文本画笔*/
    private Paint mBrokenLineTextPaint;
    /**圆画笔*/
    private Paint mCirclePaint;
    /**横线画笔*/
    private Paint mHorizontalLinePaint;
    /**圆的厚度*/
    private float mCircleWidth=2;
    /**圆的半径*/
    private float radius=5;
    /**边框的左边距*/
    private float mBrokenLineLeft=dip2px(30);
    /**边框的上边距*/
    private float mBrokenLineTop=dip2px(10);
    /**边框的下边距*/
    private float mBrokenLineBottom=dip2px(20);
    /**边框的右边距*/
    private float mBrokenLinerRight=dip2px(10);
    /**需要绘制的宽度*/
    private float mNeedDrawWidth;
    /**需要绘制的高度*/
    private float mNeedDrawHeight;
    /**数据值*/
    private float[] value=new float[]{-5.55f,-6.899f,-4.55f,-0.045f,21.511f,22.221f,22.330f,21.448f,21.955f,23.6612f,22,22.18883f,21.47995f};
    /**图表的最大值*/
    private float maxVlaue=27.55f;
    /**图表的最小值*/
    private float minValue=-19.12f;
    /**要计算的总值*/
    private float calculateValue;
    /**框线平均值*/
    private float averageValue;
    /**横线数量*/
    private float numberLine=5;
    /**边框线颜色*/
    private int mBorderLineColor= Color.BLACK;
    /**边框线的宽度*/
    private  int mBorderWidth=2;
    /**边框文本颜色*/
    private int mBorderTextColor=Color.GRAY;
    /**边框文本大小*/
    private float mBorderTextSize=20;
    /**边框横线颜色*/
    private int mBorderTransverseLineColor=Color.GRAY;
    /**边框横线宽度*/
    private float mBorderTransverseLineWidth=2;
    /**折线颜色*/
    private int mBrokenLineColor=Color.BLUE;
    /**折线文本颜色*/
    private  int mBrokenLineTextColor=Color.BLUE;
    /**折线宽度*/
    private  float mBrokenLineWidth=2;
    /**折线文本大小*/
    private  float mBrokenLineTextSize=15;
    /**折线圆的颜色*/
    private int mCircleColor=Color.BLUE;
    /**计算后的x，y坐标*/
    public Point[] mPoints;
    /**圆的半径*/
    public void setRadius(float radius) {
        this.radius = dip2px(radius);
    }
    /**设置宽度*/
    public  void  setCircleWidth(float circleWidth){
        this.mCircleWidth=dip2px(circleWidth);
    }
    /**设置边框左上右下边距*/
    public  void  setBrokenLineLTRB(float l,float t,float  r,float b){
        mBrokenLineLeft=dip2px(l);
        mBrokenLineTop=dip2px(t);
        mBrokenLinerRight=dip2px(r);
        mBrokenLineBottom=dip2px(b);
    }

    public int getViewWidth() {
        return mViewWidth;
    }

    public int getViewHeight() {
        return mViewHeight;
    }

    public float getCircleWidth() {
        return mCircleWidth;
    }

    public float getRadius() {
        return radius;
    }

    public float getBrokenLineLeft() {
        return mBrokenLineLeft;
    }

    public float getBrokenLineTop() {
        return mBrokenLineTop;
    }

    public float getBrokenLineBottom() {
        return mBrokenLineBottom;
    }

    public float getBrokenLinerRight() {
        return mBrokenLinerRight;
    }

    public float getNeedDrawWidth() {
        return mNeedDrawWidth;
    }

    public float getNeedDrawHeight() {
        return mNeedDrawHeight;
    }

    public Point[] getPoints() {
        return mPoints;
    }

    /**数据data*/
    public void setValue(float[] value) {
        this.value = value;
    }
    /**图表显示最大值*/
    public void setMaxVlaue(float maxVlaue) {
        this.maxVlaue = maxVlaue;
    }
    /**图表显示最小值*/
    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }
    /**图表横线数量*/
    public void setNumberLine(float numberLine) {
        this.numberLine = numberLine;
    }
    /**边框线颜色*/
    public void setBorderLineColor(int borderLineColor) {
        mBorderLineColor = borderLineColor;
    }
    /**边框文本颜色*/
    public void setBorderTextColor(int borderTextColor) {
        mBorderTextColor = borderTextColor;
    }
    /**边框文本大小*/
    public void setBorderTextSize(float borderTextSize) {
        mBorderTextSize = dip2px(borderTextSize);
    }
    /**框线横线 颜色*/
    public void setBorderTransverseLineColor(int borderTransverseLineColor) {
        mBorderTransverseLineColor = borderTransverseLineColor;
    }
    /**边框内折线颜色*/
    public void setBrokenLineColor(int brokenLineColor) {
        mBrokenLineColor = brokenLineColor;
    }
    /** 折线文本颜色*/
    public void setBrokenLineTextColor(int brokenLineTextColor) {
        mBrokenLineTextColor = brokenLineTextColor;
    }
    /**折线文本大小*/
    public void setBrokenLineTextSize(float brokenLineTextSize) {
        mBrokenLineTextSize = dip2px(brokenLineTextSize);
    }
    /**折线圆颜色*/
    public void setCircleColor(int circleColor) {
        mCircleColor = dip2px(circleColor);
    }
    /**边框线宽度*/
    public void setBorderWidth(float borderWidth) {
        mBorderWidth = dip2px(borderWidth);
    }
    /**边框横线宽度*/
    public void setBorderTransverseLineWidth(float borderTransverseLineWidth) {
        mBorderTransverseLineWidth = dip2px(borderTransverseLineWidth);
    }
    /**折线宽度*/
    public void setBrokenLineWidth(float brokenLineWidth) {
        mBrokenLineWidth = dip2px(brokenLineWidth);
    }

    /**获取框线画笔*/
    public Paint getBorderLinePaint() {
        return mBorderLinePaint;
    }

    /**获取边框文本画笔*/
    public Paint getTextPaint() {
        return mTextPaint;
    }

    /**获取折线画笔*/
    public Paint getBrokenLinePaint() {
        return mBrokenLinePaint;
    }

    /**获取折线文本画笔*/
    public Paint getBrokenLineTextPaint() {
        return mBrokenLineTextPaint;
    }

    /**获取圆画笔*/
    public Paint getCirclePaint() {
        return mCirclePaint;
    }

    /**获取边框横线画笔*/
    public Paint getHorizontalLinePaint() {
        return mHorizontalLinePaint;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    private  int dip2px( float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public DrawLineChart(Context context) {
        super(context);
        initPaint();

    }

    public DrawLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();

        /**计算总值*/
        calculateValue=maxVlaue-minValue;

        initNeedDrawWidthAndHeight();

        /**计算框线横线间隔的数据平均值*/
        averageValue = calculateValue/(numberLine-1);

    }


    /**初始化绘制折线图的宽高*/
    private void initNeedDrawWidthAndHeight(){
        mNeedDrawWidth = mViewWidth-mBrokenLineLeft-mBrokenLinerRight;
        mNeedDrawHeight = mViewHeight-mBrokenLineTop-mBrokenLineBottom;
    }
    /**初始化画笔*/
    private void initPaint() {

        /**初始化边框文本画笔*/
        if(mTextPaint==null){
            mTextPaint=new Paint();
            initPaint(mTextPaint);
        }
        mTextPaint.setTextSize(mBorderTextSize);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setColor(Color.GRAY);
        /**初始化边框线画笔*/
        if(mBorderLinePaint==null){
            mBorderLinePaint=new Paint();
            initPaint(mBorderLinePaint);
        }

        mBorderLinePaint.setTextSize(mBorderTextSize);
        mBorderLinePaint.setStrokeWidth(mBorderWidth);
        mBorderLinePaint.setColor(mBorderLineColor);

        /**初始化折线画笔*/
        if(mBrokenLinePaint==null){
            mBrokenLinePaint=new Paint();
            initPaint(mBrokenLinePaint);

        }

        mBrokenLinePaint.setStrokeWidth(mBrokenLineWidth);
        mBrokenLinePaint.setColor(mBrokenLineColor);

        if(mCirclePaint==null){
            mCirclePaint=new Paint();
            initPaint(mCirclePaint);
        }
        mCirclePaint.setStrokeWidth(mCircleWidth);
        mCirclePaint.setColor(mCircleColor);

        /**折线文本画笔*/
        if (mBrokenLineTextPaint == null){
            mBrokenLineTextPaint=new Paint();
            initPaint(mBrokenLineTextPaint);
        }
        mBrokenLineTextPaint.setTextAlign(Paint.Align.CENTER);
        mBrokenLineTextPaint.setColor(mBrokenLineTextColor);
        mBrokenLineTextPaint.setTextSize(mBrokenLineTextSize);

        /**横线画笔*/
        if (mHorizontalLinePaint == null){
            mHorizontalLinePaint=new Paint();
            initPaint(mHorizontalLinePaint);
        }

        mHorizontalLinePaint.setStrokeWidth(mBorderTransverseLineWidth);
        mHorizontalLinePaint.setColor(mBorderTransverseLineColor);
    }

    /**初始化画笔默认属性*/
    private void initPaint(Paint paint){
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPoints = getPoints(value,mNeedDrawHeight,mNeedDrawWidth,calculateValue,minValue,mBrokenLineLeft,mBrokenLineTop);
        /**绘制边框线和边框文本*/
        DrawBorderLineAndText(canvas);
        /**根据数据绘制线*/
        DrawLine(canvas);
        /**绘制圆*/
        DrawLineCircle(canvas);

    }
    /**绘制线上的圆*/
    public void DrawLineCircle(Canvas canvas) {
        for (int i = 0; i <mPoints.length ; i++) {
            Point point=mPoints[i];
            mCirclePaint.setColor(Color.WHITE);
            mCirclePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(point.x,point.y,radius,mCirclePaint);

            mCirclePaint.setColor(mCircleColor);
            mCirclePaint.setStyle(Paint.Style.STROKE);
            /**
             * drawCircle(float cx, float cy, float radius, Paint paint)
             * cx 中间x坐标
             * xy 中间y坐标
             * radius 圆的半径
             * paint 绘制圆的画笔
             * */
            canvas.drawCircle(point.x,point.y,radius,mCirclePaint);
        }
    }

    /**根据值绘制折线*/
    public void DrawLine(Canvas canvas) {
        Path mPath=new Path();
        for (int i = 0; i < mPoints.length; i++) {
            Point point=mPoints[i];
            if(i==0){
                mPath.moveTo(point.x,point.y);
            }else {
                mPath.lineTo(point.x,point.y);
            }
            canvas.drawText(value[i]+"",point.x,point.y-radius,mBrokenLineTextPaint);
        }
        canvas.drawPath(mPath,mBrokenLinePaint);
    }

    /**绘制边框线和边框文本*/
    private void DrawBorderLineAndText(Canvas canvas) {
        /**对应的属性
         * drawLine(float startX, float startY, float stopX, float stopY, Paint paint);
         * startX   开始的x坐标
         * startY   开始的y坐标
         * stopX    结束的x坐标
         * stopY    结束的y坐标
         * paint    绘制该线的画笔
         * */

        /**绘制边框竖线*/
        canvas.drawLine(mBrokenLineLeft,mBrokenLineTop-dip2px(5),mBrokenLineLeft,mViewHeight-mBrokenLineBottom,mBorderLinePaint);
        /**绘制边框横线*/
        canvas.drawLine(mBrokenLineLeft,mViewHeight-mBrokenLineBottom,mViewWidth,mViewHeight-mBrokenLineBottom,mBorderLinePaint);


        /**绘制边框分段横线与分段文本*/
        float averageHeight=mNeedDrawHeight/(numberLine-1);

        for (int i = 0; i < numberLine; i++) {
            float nowadayHeight= averageHeight*i;
            float v=averageValue*(numberLine-1-i)+minValue;

            /**最后横线无需绘制，否则会将边框横线覆盖*/
            if(i!=numberLine-1) {
                canvas.drawLine(mBrokenLineLeft, nowadayHeight + mBrokenLineTop, mViewWidth, nowadayHeight + mBrokenLineTop, mHorizontalLinePaint);
            }
            canvas.drawText(floatKeepTwoDecimalPlaces(v)+"",mBrokenLineLeft-dip2px(2),nowadayHeight+mBrokenLineTop,mTextPaint);
        }

        /**竖线*/
        for (int i = 1; i < mPoints.length; i++) {
            //canvas.drawLine(mPoints[i].x,mBrok enLineTop,mPoints[i].x,mBrokenLineTop+mNeedDrawHeight,mBorderLinePaint);
        }
    }
    /**保留2位小数*/
    private String  floatKeepTwoDecimalPlaces(float f){
        DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p=decimalFormat.format(f);
        return p;
    }


    /**根据值计算在该值的 x，y坐标*/
    public Point[] getPoints(float[] values, float height, float width, float max ,float min, float left,float top) {
        float leftPading = width / (values.length-1);//绘制边距
        Point[] points = new Point[values.length];
        for (int i = 0; i < values.length; i++) {
            double value = values[i]-min;
            //计算每点高度所以对应的值
            double mean = (double) max/height;
            //获取要绘制的高度
            float drawHeight = (float) (value / mean);
            int pointY = (int) (height+top  - drawHeight);
            int pointX = (int) (leftPading * i + left);
            Point point = new Point(pointX, pointY);
            points[i] = point;
        }
        return points;
    }
}


