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

import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by achaves on 13-05-2016.
 *
 */
public class Plot extends AsyncTask<Object, Void, Void> {

    private final String TAG = "Plot";

    private ArrayList<SeriesXY> seriesList = new ArrayList<>();

    private Grid grid;

    public Plot() {}

    public ArrayList<SeriesXY> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(ArrayList<SeriesXY> seriesList) {
        this.seriesList = seriesList;
    }

    /** @return the maximum x coordinate of all maximums in all series */
    public Double getXAbsoluteMax(){
        double xMaxValue = Double.NEGATIVE_INFINITY;

        if (getSeriesList().size() > 0) {
            for (SeriesXY series : getSeriesList()) {
                if (series.size() > 0) {
                    double maxValue = series.getXMaxValue();
                    if (maxValue > xMaxValue) {
                        xMaxValue = maxValue;
                    }
                }
            }
        } else {
            Log.e(TAG, "No data found");
        }
        return xMaxValue;
    }

    public Double getXAbsoluteMin() {
        double xMinValue = Double.POSITIVE_INFINITY;

        if (getSeriesList().size() > 0) {
            for (SeriesXY series : getSeriesList()) {
                if (series.size() > 0) {
                    double minValue = series.getXMinValue();
                    if (minValue < xMinValue) {
                        xMinValue = minValue;
                    }
                }
            }
        } else {
            Log.e(TAG, "No data found");
        }

        return xMinValue;
    }

    /** @return the maximum y coordinate of all maximums in all series */
    public Double getYAbsoluteMax(){
        double yMaxValue = Double.NEGATIVE_INFINITY;

        if (getSeriesList().size() > 0) {
            for (SeriesXY series : getSeriesList()) {
                if (series.size() > 0) {
                    double maxValue = series.getYMaxValue();
                    if (maxValue > yMaxValue) {
                        yMaxValue = maxValue;
                    }
                }
            }
        } else {
            Log.e(TAG, "No data found");
        }
        return yMaxValue;
    }

    public Double getYAbsoluteMin() {
        double yMinValue = Double.POSITIVE_INFINITY;

        if (getSeriesList().size() > 0) {
            for (SeriesXY series : getSeriesList()) {
                if (series.size() > 0) {
                    double minValue = series.getYMinValue();
                    if (minValue < yMinValue) {
                        yMinValue = minValue;
                    }
                }
            }
        } else {
            Log.e(TAG, "No data found");
        }
        return yMinValue;
    }

    public void addSeries(SeriesXY series) {
        seriesList.add(series);
    }

    public void plotPoint(PointPlot point, Paint paint) {
        paint.setColor(point.getColor());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (seriesList.size() == 0) {
            throw new IllegalArgumentException("No data to plot");
        }
    }

    @Override
    protected Void doInBackground(Object... params) {

        // not overruling a user defined maximum for the X axis
        if (Double.isInfinite(getGrid().getXAxis().getMaxAxisRange())) {
            getGrid().getXAxis().setMaxAxisRange(getXAbsoluteMax());
        }
        // not overruling a user defined minimum for the X axis
        if (Double.isInfinite(getGrid().getXAxis().getMinAxisRange())) {
            getGrid().getXAxis().setMinAxisRange(getXAbsoluteMin());
        }
        // not overruling a user defined maximum for the Y axis
        if (Double.isInfinite(getGrid().getYAxis().getMaxAxisRange())) {
            getGrid().getYAxis().setMaxAxisRange(getYAbsoluteMax());
        }
        // not overruling a user defined minimum for the Y axis
        if (Double.isInfinite(getGrid().getYAxis().getMinAxisRange())) {
            getGrid().getYAxis().setMinAxisRange(getYAbsoluteMin());
        }

        gridDefinition();
        plotDefinition();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (getGrid().getImageView() == null) {
            Log.w(TAG, "assigned image view is NULL\n" +
                    "after plot() use getBitmap() ex: \n" +
                    "plot();" +
                    "someImage.setImageBitmap(someGrid.getBitmap());");
        } else {
            getGrid().getImageView().setImageBitmap(getGrid().getBitmap());
        }
    }

    public void plot(){
        this.execute(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void gridDefinition(){
        getGrid().apply(); // defines axis, rulers and labels
    }

    public void plotDefinition(){
        if (getSeriesList().size() > 0) {
            for (SeriesXY series : getSeriesList()) {

                ChartLineType lineType = series.getLineType();

                switch (lineType){
                    case LINE:
                        getGrid().plotSeriesLine(series);
                        break;
                    case DASHED_LINE:
                        getGrid().plotSeriesDashedLine(series);
                        break;
                    case BEZIER_QUADRATIC:
                        getGrid().plotSeriesBezierQuadratic(series);
                        break;
                    case BEZIER_CUBIC:
                        getGrid().plotSeriesBezierCubic(series);
                        break;
                    case POINTS:
                        getGrid().plotSeriesPoints(series);
                }

                if (series.isToWritePointCoordinate()) { // the point coordinate
                    getGrid().writeSeriesPointCoordinate(series);
                }

                if (series.isToWritePointLabel()) { // the point label
                    getGrid().writeSeriesPointText(series);
                }
            }
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
