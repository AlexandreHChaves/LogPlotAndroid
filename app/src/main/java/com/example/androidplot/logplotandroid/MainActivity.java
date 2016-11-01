/*
 * Copyright 2016 Alexandre H.P. Chaves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidplot.logplotandroid;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import static com.example.androidplot.logplotandroid.Data.Data.getXGranulometryArray;
import static com.example.androidplot.logplotandroid.Data.Data.getYGranulometryArray;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";

    private ImageView imagePlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "............ onCreate .............");
        super.onCreate(savedInstanceState);

//        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagePlot = (ImageView) findViewById(R.id.iv_log_plot);

//        String path = "/system/fonts"; // find system font files
//        File file = new File(path);
//        File ff[] = file.listFiles();
//
//        for (File file1 : ff) {
//            Log.i(TAG, file1.getName());
//        }

//        new Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        loadPlot();
//                    }
//                },
//                100);
    }

    // you need to wait for activity window to be attached and then call getWidth() and getHeight() on ImageView.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.i(TAG, "............ onWindowFocusChanged .............");
        super.onWindowFocusChanged(hasFocus);

        if (getXGranulometryArray().length != getYGranulometryArray().length) {
            Log.e(TAG, "incompatible data lengths");
        }

        loadPlot();
    }

    private void loadPlot(){
        Log.i(TAG, "............ loadPlot .............");

        Log.i(TAG, "image width: " + imagePlot.getWidth());
        Log.i(TAG, "image height: " + imagePlot.getHeight());

        Log.d(TAG, "x array dimension: " + getXGranulometryArray().length);
        Log.d(TAG, "y array dimension: " + getYGranulometryArray().length);

        SeriesXY series = new SeriesXY(getXGranulometryArray(), getYGranulometryArray());
//        SeriesXY series = new SeriesXY(getXData(), getYData());
        series.setLineWidth(0);
        series.setLineColor(ContextCompat.getColor(this, R.color.indigo_200));
//        series.setToWritePointCoordinate(true);
//        series.setPointSize(2);

        SeriesXY series_copy = series.clone();
        series_copy.setLineType(ChartLineType.POINTS);
        series_copy.setPointSize(1);

        PointPlot p1 = new PointPlot(0.5, 0.7);
        p1.setSize(5);
        p1.setText("bla bla");
        p1.setTextVOffset(10);

        PointPlot p2 = p1.clone();
        p2.setTextVOffset(-10 - 5);

        SeriesXY s1 = new SeriesXY();
        s1.addPoint(p1);
        s1.setToWritePointLabel(true);
        s1.setLineType(ChartLineType.POINTS);

        SeriesXY s2 = new SeriesXY();
        s2.addPoint(p2);
        s2.setToWritePointLabel(true);
        s2.setLineType(ChartLineType.POINTS);

        // ############ rulers #################
        Ruler yRuler055 = new Ruler();
        yRuler055.setPosition(0.5);
        yRuler055.label.setText("median");
        yRuler055.label.setVOffset(3);
        yRuler055.label.setHOffset(3);
        yRuler055.setColor(ContextCompat.getColor(this, R.color.deep_orange_100));
        yRuler055.label.setColor(ContextCompat.getColor(this, R.color.deep_orange_300));

        Ruler xpRuler = new Ruler();
        xpRuler.setColor(ContextCompat.getColor(this, R.color.teal_300));
        xpRuler.label.setColor(ContextCompat.getColor(this, R.color.teal_600));
        xpRuler.label.setHOffset(3);
        xpRuler.label.setVOffset(3);
//        xpRuler.label.setSize(10);
        xpRuler.setPosition(0.35);

        Ruler yRuler = new Ruler();
        yRuler.setPosition(0.5);
        yRuler.label.setText("0.5");
        yRuler.setBlank(10);
        yRuler.setDash(7);
        yRuler.setColor(ContextCompat.getColor(this, R.color.amber_900));

        // ################# AXIS ##################
        AxisLog10 xAxis = new AxisLog10();
//        Axis xAxis = new AxisLinear();
//        AxisLog10 xAxis = new AxisLog10();
//        xAxis.gridRulers.label.setVOffset(-15);
        Log.d(TAG, "xAxis.gridRulers.label.getSize(): " + xAxis.gridRulers.label.getSize());
        xAxis.gridRulers.label.setVOffset(-xAxis.gridRulers.label.getSize());
        xAxis.gridRulers.label.setHOffset(-6);
        xAxis.gridRulers.setLabelSpacing(9);
        xAxis.gridRulers.setDash(7);
        xAxis.gridRulers.setBlank(10);
        xAxis.label.setVOffset(-5);
//        xAxis.getGridRulers().label.setSize(10);
//        xAxis.label.setVOffset(-12);
//        xAxis.setWidth(5);
        xAxis.setFirstMajorRulerStep(0.01, 2); // 1cm
        xAxis.label.setSize(12); // text size sp
        xAxis.label.setText("X Axis");

//        rulerStep = RulerStepFinder.findRulerStep(series.getXRange());
//        Log.i(TAG, "X axis rulerStep: " + rulerStep);
//        xAxis.setStep(rulerStep);
//        xAxis.addUserRuler(xpRuler);


        Axis yAxis = new AxisLinear();
//        Axis yAxis = new AxisLog10();
//        yAxis.label.setHOffset(-5);
        yAxis.label.setSize(12);
//        yAxis.setStep(50d);
//        rulerStep = Axis.findRulerStep(series.getYRange());
//        rulerStep =
//        Log.i(TAG, "Y axis rulerStep: " + rulerStep);
//        yAxis.rulerStep.setValue(rulerStep);
        yAxis.rulerStep = Axis.findRulerStep(series.getYRange());
        yAxis.setWidth(2);
        yAxis.label.setText("Y Axis");
        yAxis.gridRulers.label.setVOffset(-yAxis.gridRulers.label.getSize()/2 + yAxis.gridRulers.getWidth());
        yAxis.gridRulers.label.setHOffset(-20);
        yAxis.gridRulers.setLabelSpacing(3);
        yAxis.gridRulers.setDash(7);
        yAxis.gridRulers.setBlank(20);

//        yAxis.setMinAxisRange(0d);
//        yAxis.setMaxAxisRange(1d);
//        yAxis.setMinAxisRange(getYData()[0]);
//        yAxis.setMaxAxisRange(getYData()[getYData().length - 1]);
        yAxis.setMaxAxisRange(1);
//        yAxis.addUserRuler(yRuler055);
        yAxis.userRulers.addRuler(yRuler);

        // ######################## GRID ###########################
        Grid grid = new Grid(this, imagePlot);
//        grid.setBackGroundColor(ContextCompat.getColor(this, R.color.blue_50));
        grid.setTopTextBorder(10);
        grid.setRightTextBorder(10);
        grid.setXAxis(xAxis);
        grid.setYAxis(yAxis);

        Plot logPlot = new Plot();
        logPlot.setGrid(grid);
        logPlot.addSeries(series);
        logPlot.addSeries(series_copy);
//        logPlot.addSeries(s1);
//        logPlot.addSeries(s2);
//        logPlot.addSeries(series_copy);

        logPlot.plot();
    }
}
