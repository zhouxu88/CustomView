package com.example.zhouxu.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    QuXianView chartView;
    //曲线上的数据点的集合
    private List<PointDataBean> pointDataBeanList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initChartView();


    }


    private void initChartView() {
        initData();

        chartView = findViewById(R.id.customView1);
        // xy轴集合自己添加数据
        chartView.setYValues(pointDataBeanList);
    }

    private void initData() {
//        pointDataBeanList.add(new PointDataBean("12.01",34.4f));
//        pointDataBeanList.add(new PointDataBean("12.02",34.8f));
//        pointDataBeanList.add(new PointDataBean("12.03",35.1f));
//        pointDataBeanList.add(new PointDataBean("12.04",35.3f));
//        pointDataBeanList.add(new PointDataBean("12.05",35.6f));
//        pointDataBeanList.add(new PointDataBean("12.06",35.8f));
//        pointDataBeanList.add(new PointDataBean("12.07",36f));
//        pointDataBeanList.add(new PointDataBean("12.08",36.2f));
//        pointDataBeanList.add(new PointDataBean("12.09",36.5f));
//        pointDataBeanList.add(new PointDataBean("12.10",36.7f));
//        pointDataBeanList.add(new PointDataBean("12.11",37f));
//        pointDataBeanList.add(new PointDataBean("12.12",37.6f));
//        pointDataBeanList.add(new PointDataBean("12.13",38f));
//        pointDataBeanList.add(new PointDataBean("12.14",38f));
//        pointDataBeanList.add(new PointDataBean("12.15",38f));
//        pointDataBeanList.add(new PointDataBean("12.16",38f));
//        pointDataBeanList.add(new PointDataBean("12.17",38f));
//        pointDataBeanList.add(new PointDataBean("12.18",38f));
//        pointDataBeanList.add(new PointDataBean("12.19",38f));
//        pointDataBeanList.add(new PointDataBean("12.20",38f));


        pointDataBeanList.add(new PointDataBean("12.01", 35f));
        pointDataBeanList.add(new PointDataBean("12.01", 35.3f));
        pointDataBeanList.add(new PointDataBean("12.01", 35.8f));
        pointDataBeanList.add(new PointDataBean("12.01", 36.4f));
        pointDataBeanList.add(new PointDataBean("12.02", 37f));
        pointDataBeanList.add(new PointDataBean("12.02", 36.8f));
        pointDataBeanList.add(new PointDataBean("12.02", 35f));
        pointDataBeanList.add(new PointDataBean("12.02", 35.4f));
        pointDataBeanList.add(new PointDataBean("12.03", 34f));
        pointDataBeanList.add(new PointDataBean("12.03", 34.8f));
        pointDataBeanList.add(new PointDataBean("12.03", 35.4f));
        pointDataBeanList.add(new PointDataBean("12.03", 36.9f));
        pointDataBeanList.add(new PointDataBean("12.04", 38f));
        pointDataBeanList.add(new PointDataBean("12.05", 34f));
        pointDataBeanList.add(new PointDataBean("12.06", 36f));
        pointDataBeanList.add(new PointDataBean("12.07", 36f));
        pointDataBeanList.add(new PointDataBean("12.08", 36f));
        pointDataBeanList.add(new PointDataBean("12.09", 36f));
        pointDataBeanList.add(new PointDataBean("12.10", 36.7f));
        pointDataBeanList.add(new PointDataBean("12.11", 37f));
        pointDataBeanList.add(new PointDataBean("12.12", 37.6f));
        pointDataBeanList.add(new PointDataBean("12.13", 38f));
        pointDataBeanList.add(new PointDataBean("12.14", 38f));
        pointDataBeanList.add(new PointDataBean("12.15", 38f));
        pointDataBeanList.add(new PointDataBean("12.16", 38f));
        pointDataBeanList.add(new PointDataBean("12.17", 38f));
        pointDataBeanList.add(new PointDataBean("12.18", 38f));
        pointDataBeanList.add(new PointDataBean("12.19", 38f));
        pointDataBeanList.add(new PointDataBean("12.20", 38f));


    }


}
