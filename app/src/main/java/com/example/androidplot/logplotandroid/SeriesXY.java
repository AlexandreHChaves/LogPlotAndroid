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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    private boolean isToWritePointLabel = false;
    private boolean isToWritePointCoordinate = false;

    private String name;
    /** identification of the series in the label (legend) area */
    private String label;

    /**
     * This builds a new empty series. Afterwards you will need to input data with:<br>
     * * setData(x[], y[]); <br>
     * * setPointsList(PointPlot[]);<br>
     * * setxValues(x[]) and setyValues(y[]) with makeSeries() after; <br>
     * * addPoint(PointPlot)
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
        this.setToWritePointLabel(series.isToWritePointLabel());
        this.setLineColor(series.getLineColor());
        this.setLineWidth(series.getLineWidth());
        this.setName(series.getName());
        this.setLabel(series.getLabel());

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

    /**
     * @return  identification of the series in the label (legend) area
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label identification of the series in the label (legend) area
     */
    public void setLabel(String label) {
        this.label = label;
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

    /**
     * @return Maximum value from X axis
     */
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

    /**
     *
     * @return Minimum value from X axis
     */
    public Double getXMinValue() {

        Double xMinValue = Double.POSITIVE_INFINITY;

        if (getPointsList().size() > 0) {
            for (PointPlot point : getPointsList()) {
                if (point.getX() < xMinValue) {
                    xMinValue = point.getX();
                }
            }
        }
        return xMinValue;
    }

    /**
     *
     * @return the value amplitude from minimum to maximum value in the X axis
     */
    public double getXRange() {
        return getXMaxValue() - getXMinValue();
    }

    public Double getYMaxValue(){

        Double yMaxValue = Double.NEGATIVE_INFINITY;

        if (getPointsList().size() > 1) {
            for (PointPlot point : getPointsList()) {
                if (point.getY() > yMaxValue) {
                    yMaxValue = point.getY();
                }
            }
        }
        return yMaxValue;
    }

    /**
     * @return the minimum value in the Y axis
     */
    public Double getYMinValue() {
        Double yMinValue = Double.POSITIVE_INFINITY;
        if (getPointsList().size() > 1) {
            for (PointPlot point : getPointsList()) {
                if (point.getY() < yMinValue) {
                    yMinValue = point.getY();
                }
            }
        }
        return yMinValue;
    }

    /**
     * @return the amplitude value from minimum to maximum values in the Y axis
     */
    public double getYRange() {
        return getYMaxValue() - getYMinValue();
    }

    /***
     * Internally this passes the x and y array values previously inserted into PointPlot ArrayList
     * @return a SeriesXY object
     */
    private SeriesXY makeSeries(){

        final String METHOD_NAME = "........ makeSeries() ......";

        if (xValues != null && yValues != null) {

            int xLength = xValues.length;
            int yLength = yValues.length;

            if (xLength > 0 && yLength > 0) {
                if (xLength == yLength) {
                    for (int i = 0; i < xLength; i++) {
                        addPoint(new PointPlot(xValues[i], yValues[i]));
                    }
                } else {
                    printLog(METHOD_NAME);
                    Log.e(TAG, "arrays with different dimensions");
                }
            } else {
                printLog(METHOD_NAME);
                Log.e(TAG, "at least one of the arrays is empty");
            }
        } else {
            printLog(METHOD_NAME);
            Log.e(TAG, "at least one of the arrays is NULL");
        }

        return this;
    }

    /**
     * We might want to limit the series to a section of it
     * @param xMin the minimum x value this series is allowed to have
     * @param xMax the maximum x value this series is allowed to have
     * @param yMin the minimum y value this series is allowed to have
     * @param yMax the maximum y value this series is allowed to have
     */
    public void limitTo(double xMin, double xMax, double yMin, double yMax) {

        boolean isARemovingPoint = false;
        boolean hasPointsToRemove = false;
        Set<PointPlot> pointsToRemove = new HashSet<>();

        for (int j = 0; j < getPointsList().size() - 1; j++) {

            if (getPoint(j).getX() < xMin) {
                isARemovingPoint = true;
            }

            if (getPoint(j).getX() > xMax) {
                isARemovingPoint = true;
            }

            if (getPoint(j).getY() < yMin) {
                isARemovingPoint = true;
            }

            if (getPoint(j).getY() > yMax) {
                isARemovingPoint = true;
            }

            if (isARemovingPoint) {
                pointsToRemove.add(getPointsList().get(j));
                hasPointsToRemove = true;
                isARemovingPoint = false;
            }
        }

        if (hasPointsToRemove) {
            getPointsList().removeAll(pointsToRemove);
        }
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

    /**
     * @return true if it is ordered to write the label of that point
     */
    public boolean isToWritePointLabel() {
        return isToWritePointLabel;
    }

    /**
     * @param toWritePointLabel true if you want to plot (write) the point label
     */
    public void setToWritePointLabel(boolean toWritePointLabel) {
        isToWritePointLabel = toWritePointLabel;
    }

    /**
     * @return true if it is to plot (write) the point coordinate
     */
    public boolean isToWritePointCoordinate() {
        return isToWritePointCoordinate;
    }

    /**
     * @param toWritePointCoordinate true if you want to plot (write) the point coordinate
     */
    public void setToWritePointCoordinate(boolean toWritePointCoordinate) {
        isToWritePointCoordinate = toWritePointCoordinate;
    }

    private  void printLog(String message){
        Log.i(TAG, message);
    }
}
