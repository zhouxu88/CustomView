package com.example.zhouxu.customview;

/**
 * Created by zhouxu on 2018/12/6.
 */

public class PointDataBean {
    private String time;
    private float value;
    private float x;
    private float y;

    public PointDataBean() {

    }

    public PointDataBean(String time, float value) {
        this.time = time;
        this.value = value;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
