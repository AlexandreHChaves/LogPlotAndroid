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

import android.util.Log;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

/**
 * Created by achaves on 10-05-2016.
 *
 */
public class SeriesXY implements Cloneable{

    private final String TAG = "SeriesXY";

    private ArrayList<PointPlot> pointsList = new ArrayList<>();
    /** strait line, points, smooth line, ... */
    private ChartLineType lineType = ChartLineType.LINE;
    private int lineColor;
    /** line width in dp units */
    private float lineWidth = 0f;

    private double[] xValues;
    private double[] yValues;

    private boolean isToWritePointText = false;
    private boolean isToWritePointCoordinate = false;

    private String name;

    /**
     * This builds a new empty series. Afterwards you will need to input data
     * with setData(x[], y[]); setPointsList(PointPlot[]); setxValues(x[]) and setyValues(y[]) with
     * makeSeries() after; addPoint(PointPlot)
     */
    public SeriesXY() {
        setLineColor(ChartColor.StandardColor.line);
    }

    /**
     * This builds
     * @param xValues data values for the x axis
     * @param yValues data values for the y axis
     */
    public SeriesXY(double[] xValues, double[] yValues) {
        setLineColor(ChartColor.StandardColor.line);
        setData(xValues, yValues);
    }

    public SeriesXY(ArrayList<PointPlot> pointsList){
        this.pointsList = pointsList;
    }

    private SeriesXY(SeriesXY series) {
        this.setLineType(series.getLineType());
        this.setToWritePointCoordinate(series.isToWritePointCoordinate());
        this.setToWritePointText(series.isToWritePointText());
        this.setLineColor(series.getLineColor());
        this.setLineWidth(series.getLineWidth());
        this.setName(series.getName());

        for (PointPlot point : series.getPointsList()) {
            addPoint(point.clone());
        }

        if (series.getxValues() != null) {
            setxValues(series.getxValues().clone());
            setyValues(series.getyValues().clone());
        }
    }

    @Override
    public SeriesXY clone() {
        try {
            super.clone(); // calls the parent clone but doesn't do nothing with it
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new SeriesXY(this);
    }

    public void setLineType(ChartLineType lineType) {
        this.lineType = lineType;
    }

    public ChartLineType getLineType() {
        return lineType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPointsList(PointPlot[] pointsArray) {
        this.pointsList.toArray(pointsArray);
    }

    public void setPointsList(ArrayList<PointPlot> pointsList) {
        this.pointsList = pointsList;
    }

    public void setData (double[] x, double[] y) {
        Log.d(getClass().getName(), "..... convertArrayToPoints .....");
        if (x.length != y.length) {
            Log.e(getClass().getName(), "wrong dimension array between X and Y");
            return;
        } else if (x.length == 0) {
            Log.e(getClass().getName(), "no elements exists in the arrays");
            return;
        }
        setxValues(x);
        setyValues(y);
        makeSeries();
    }

    public void setPoint(int index, PointPlot point) {
        if (index <= pointsList.size() || index >= 0) {
            pointsList.add(index, point);
        } else {
            Log.e(getClass().getName(), "index out of bounds");
        }
    }

    public void addPoint(PointPlot point) {
        pointsList.add(point);
    }

    /** @return  a point in the serie or null if index is out of bounds */
    public PointPlot getPoint(int index) {
        Log.i(getClass().getName(), "......... setPoint .......");
        if (index <= pointsList.size() || index >= 0) {
            return pointsList.get(index);
        } else {
            Log.e(getClass().getName(), "index out of bounds");
            return null;
        }
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * @return lineWidth in dp units
     */
    public float getLineWidth() {
        return lineWidth;
    }

    /**
     * @param lineWidth line width in dp units
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public ArrayList<PointPlot> getPointsList(){
        return pointsList;
    }

    public int size(){
        return pointsList.size();
    }

    public Double getXMaxValue(){

        Double xMaxValue = Double.NEGATIVE_INFINITY;

        if (getPointsList().size() > 1) {
            for (PointPlot point : getPointsList()) {
                if (point.getX() > xMaxValue) {
                    xMaxValue = point.getX();
                }
            }
        }
        return xMaxValue;
    }

    public Double getXMinValue() {
        Double xMinValue = Double.POSITIVE_INFINITY;

        if (getPointsList().size() > 0) {
            for (PointPlot point : getPointsList()) {
                if (point.getY() < xMinValue) {
                    xMinValue = point.getX();
                }
            }
        }
        return xMinValue;
    }

    public Double getYMaxValue(){

        Double yMaxValue = Double.NEGATIVE_INFINITY;

        if (getPointsList().size() > 1) {
            for (PointPlot point : getPointsList()) {
                if (point.getX() > yMaxValue) {
                    yMaxValue = point.getX();
                }
            }
        }
        return yMaxValue;
    }

    public Double getYMinValue() {
        Double yMinValue = Double.NEGATIVE_INFINITY;
        if (getPointsList().size() > 1) {
            for (PointPlot point : getPointsList()) {
                if (point.getX() > yMinValue) {
                    yMinValue = point.getX();
                }
            }
        }
        return yMinValue;
    }

    /***
     * Internally this passes the x and y array values previously inserted into PointPlot ArrayList
     * @return a SeriesXY object
     */
    public SeriesXY makeSeries(){
        if (xValues != null && yValues != null) {

            int xLength = xValues.length;
            int yLength = yValues.length;

            if (xLength > 0 && yLength > 0) {
                if (xLength == yLength) {
                    for (int i = 0; i < xLength; i++) {
                        addPoint(new PointPlot(xValues[i], yValues[i]));
                    }
                }
            }
        }

        return this;
    }

    /** You can only set color in points AFTER the series have been added
     * @param color color to set on all points of the series
     */
    public void setPointColor(int color){
        for (PointPlot point : this.pointsList) {
            point.setColor(color);
        }
    }

    /** You can only set size in points AFTER the series have been added
     * @param size set size in dp units on all points of the series
     */
    public void setPointSize(int size) {
        for (PointPlot point : pointsList) {
            point.setSize(size);
        }
    }

    public double[] getxValues() {
        return xValues;
    }

    public void setxValues(double[] xValues) {
        this.xValues = xValues;
    }

    public double[] getyValues() {
        return yValues;
    }

    public void setyValues(double[] yValues) {
        this.yValues = yValues;
    }

    public boolean isToWritePointText() {
        return isToWritePointText;
    }

    public void setToWritePointText(boolean toWritePointText) {
        isToWritePointText = toWritePointText;
    }

    public boolean isToWritePointCoordinate() {
        return isToWritePointCoordinate;
    }

    public void setToWritePointCoordinate(boolean toWritePointCoordinate) {
        isToWritePointCoordinate = toWritePointCoordinate;
    }
}
