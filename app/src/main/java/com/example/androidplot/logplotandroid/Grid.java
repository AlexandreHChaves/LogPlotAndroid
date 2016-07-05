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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.example.androidplot.logplotandroid.Utils.dpToPx;
import static com.example.androidplot.logplotandroid.Utils.spToPx;

/**
 * Created by achaves on 12-05-2016.
 *
 */
public abstract class Grid implements Applyable{

    private final String TAG = "Grid";
//    private GridBorders gridLimits;

    private float leftBorder = 0f;
    private float rightBorder = 0f;
    private float topBorder = 0f;
    private float bottomBorder = 0f;

    private float leftTextBorder = 0f;
    private float topTextBorder = 0f;
    private float rightTextBorder = 0f;
    private float bottomTextBorder = 0f;

    private Text text;

    protected Axis xAxis;
    protected Axis yAxis;
    private int backGroundColor;
    private ArrayList<Ruler> rulerList = new ArrayList<>();

    protected Canvas canvas;
    protected Paint paint;
    private Bitmap bitmap;
    private ImageView imageView;

    public Grid(Activity activity, ImageView imageView) {

        try {
            if (imageView == null) {
                throw new InstantiationException("the ImageView is NULL");
            }

            int imgWidth = imageView.getWidth();
            int imgHeight = imageView.getHeight();

            if (imgHeight == 0 || imgWidth == 0) {
                throw new InstantiationException("the image '" + imageView.toString() +"' chosen has no dimensions");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        initMetrics(activity);

        this.imageView = imageView;

        bitmap = Bitmap.createBitmap(this.imageView.getWidth(), this.imageView.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setAntiAlias(true); // smooths the drawing
        backGroundColor = ChartColor.StandardColor.background;
        text = new Text();
    }

    public Grid(Activity activity, Bitmap bitmap) {
        initMetrics(activity);
        this.bitmap = bitmap;
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setAntiAlias(true); // smooths the drawing
        backGroundColor = ChartColor.StandardColor.background;
        text = new Text();
    }

    private void initMetrics(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        SavedMetrics.Metrics.density  = metrics.density;
        SavedMetrics.Metrics.densityDpi = metrics.densityDpi;
        SavedMetrics.Metrics.metrics = metrics;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public ImageView getImageView(){
        return imageView;
    }

    public Axis getXAxis() {
        return xAxis;
    }

    public void setXAxis(Axis xAxis) {
        this.xAxis = xAxis;
    }

    public Axis getYAxis() {
        return yAxis;
    }

    public void setYAxis(Axis yAxis) {
        this.yAxis = yAxis;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public ArrayList<Ruler> getRulerList() {
        return rulerList;
    }

    public void setRulerList(ArrayList<Ruler> rulerList) {
        this.rulerList = rulerList;
    }

    public void setRuler(Ruler ruler) {
        rulerList.add(ruler);
    }

    public Ruler getRuler(int position) {
        return rulerList.get(position);
    }

    /**
     * @param leftBorder position of the left border of the grid in pixel units
     */
    public void setLeftBorder(float leftBorder) {
        this.leftBorder = leftBorder;
    }

    public float getLeftBorder() {
        return leftBorder;
    }

    /**
     * @param rightBorder position of the right border of the grid in pixel
     */
    public void setRightBorder(float rightBorder) {
        this.rightBorder = rightBorder;
    }

    public float getRightBorder() {
        return rightBorder;
    }

    /**
     * @param topBorder position of the top border of the grid in pixel
     */
    public void setTopBorder(float topBorder) {
        this.topBorder = topBorder;
    }

    public float getTopBorder() {
        return topBorder;
    }

    /**
     * @param bottomBorder position of the bottom border of the grid in pixel
     */
    public void setBottomBorder(float bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public float getBottomBorder() {
        return bottomBorder;
    }

    /** @return  The left text border of the graphic in pixel units */
    public float getLeftTextBorder() {
        return leftTextBorder;
    }

    /** @param leftTextBorder  The left text border of the graphic in pixel units */
    public void setLeftTextBorder(float leftTextBorder) {
        this.leftTextBorder = leftTextBorder;
    }

    /**
     * @return The top text border of the graphic in pixel units
     */
    public float getTopTextBorder() {
        return topTextBorder;
    }

    public void setTopTextBorder(float topTextBorder) {
        this.topTextBorder = topTextBorder;
    }

    public void setRightTextBorder(float rightTextBorder) {
        this.rightTextBorder = rightTextBorder;
    }

    public float getRightTextBorder() {
        return rightTextBorder;
    }

    public float getBottomTextBorder() {
        return bottomTextBorder;
    }

    /** set the bottom text margin border */
    public void setBottomTextBorder(float bottomTextBorder) {
        this.bottomTextBorder = bottomTextBorder;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    /**
     * @param point write into the graphic the identification label point
     */
    public void writePointText(PointPlot point) {

        if (point.getText().trim().isEmpty()) return;

        paint.setColor(point.getTextColor());
        paint.setTextSize(spToPx(point.getTextSize()));
        canvas.drawText(
                point.getText(),
                xPx(xAxis.scale(point.getX()) + dpToPx(point.getTextHOffset())),
                yPx(yAxis.scale(point.getY()) + dpToPx(point.getTextVOffset())),
//                yPx(yAxis.scale(point.getY())),
                paint
        );
    }

    /**
     * @param point write into the graphic the coordinates of the point
     */
    public void writePointCoordinate(PointPlot point) {

        paint.setColor(point.getTextColor());
        paint.setTextSize(spToPx(point.getTextSize()));
        canvas.drawText(
                point.toString(),
                xPx(xAxis.scale(point.getX()) + dpToPx(point.getTextHOffset())),
                yPx(yAxis.scale(point.getY()) + dpToPx(point.getTextVOffset())),
                paint
        );
    }

    public void writeSeriesPointCoordinate(SeriesXY series) {
        if (series.getPointsList().size() > 0) {
            for (PointPlot point : series.getPointsList()) {
                writePointCoordinate(point);
            }
        }
    }

    public void writeSeriesPointText(SeriesXY series) {
        for (PointPlot point : series.getPointsList()) {
            writePointText(point);
        }
    }

    public void plotPoint(PointPlot point) {
        paint.setColor(point.getColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(
                xPx(xAxis.scale(point.getX())),
                yPx(yAxis.scale(point.getY())),
                dpToPx(point.getSize()),
                paint);
    }

    /**
     * Plot the points in existence in series
     * @param series object containing an array list of PointPlot
     */
    public void plotSeriesPoints(SeriesXY series) {
        for (PointPlot point : series.getPointsList()) {
            plotPoint(point);
        }
    }

    /**
     * Draws a dashed line of type - - - - between points.
     * At least 4 points must be defined in the series.
     * @param series object containing a list of PointPlot
     */
    public void plotSeriesDashedLine(SeriesXY series) {
        paint.setColor(series.getLineColor());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dpToPx(series.getLineWidth()));
        int size = series.getPointsList().size();

        if (size >= 4) {
            float[] points = new float[size * 2];
            int i = 0, j = 0;
            for (PointPlot point : series.getPointsList()) {
                points[i] = xPx(xAxis.scale(point.getX()));
                points[i + 1] = yPx(yAxis.scale(point.getY()));

                i = 2 * ++j;
            }
            canvas.drawLines(points, paint);
        } else {
            Log.e(TAG, "not enough points to define an interrupted line");
        }
    }

    /**
     * Plots a smooth line passing the points. If you use a non-linear scale on your axis,
     * this line may not pass trough the control points since the math to evaluate the line is linear.
     * This needs at least 3 points.
     * @param series object containing a list of PointPlot
     */
    public void plotSeriesBezierCubic(SeriesXY series) {
        Log.i(TAG, "........... plotSeriesBezierCubic .............");

        int size = series.getPointsList().size();

        if (size >= 3) {
            paint.setColor(series.getLineColor());
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dpToPx(series.getLineWidth()));
            Path path = new Path();
            path.moveTo( // 1st point
                    xPx(xAxis.scale(series.getPointsList().get(0).getX())),
                    yPx(yAxis.scale(series.getPointsList().get(0).getY()))
            );

            int remainingPoints = size%3;

            for (int i = 0; i < size - remainingPoints - 1; i+=3) {
                path.cubicTo(
                        xPx(xAxis.scale(series.getPointsList().get(i).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(i).getY())),
                        xPx(xAxis.scale(series.getPointsList().get(i+1).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(i+1).getY())),
                        xPx(xAxis.scale(series.getPointsList().get(i+2).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(i+2).getY()))
                );
            }

            if (remainingPoints > 0) {
                path.cubicTo(
                        xPx(xAxis.scale(series.getPointsList().get(size-3).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(size-3).getY())),
                        xPx(xAxis.scale(series.getPointsList().get(size-2).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(size-2).getY())),
                        xPx(xAxis.scale(series.getPointsList().get(size-1).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(size-1).getY()))
                );
            }
            canvas.drawPath(path, paint);
        }
    }

    /**
     * Plots a line passing the points. If you use a non-linear scale on your axis,
     * this line may not pass trough the control points since the math to evaluate the line is linear.
     * This needs at least 2 points.
     * @param series object containing a list of PointPlot
     */
    public void plotSeriesBezierQuadratic(SeriesXY series) {
        int size = series.getPointsList().size();

        if (size >= 2) {
            paint.setColor(series.getLineColor());
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dpToPx(series.getLineWidth()));
            Path path = new Path();
            path.moveTo( // 1st point
                    xPx(xAxis.scale(series.getPointsList().get(0).getX())),
                    yPx(yAxis.scale(series.getPointsList().get(0).getY()))
            );

            int remainingPoints = size%2;

            for (int i = 0; i < size - remainingPoints - 1; i+=2) {
                path.quadTo(
                        xPx(xAxis.scale(series.getPointsList().get(i).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(i).getY())),
                        xPx(xAxis.scale(series.getPointsList().get(i+1).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(i+1).getY()))
                );
            }

            if (remainingPoints > 0) {
                path.quadTo(
                        xPx(xAxis.scale(series.getPointsList().get(size-2).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(size-2).getY())),
                        xPx(xAxis.scale(series.getPointsList().get(size-1).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(size-1).getY()))
                );
            }
            canvas.drawPath(path, paint);
        }
    }

    /***
     * Plots straight line segments between points linking all points.
     * This needs at least 2 points.
     * @param series object containing a list of PointPlot
     */
    public void plotSeriesLine (SeriesXY series) {

        int size = series.getPointsList().size();

        if (size >= 2) {
            paint.setColor(series.getLineColor());
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dpToPx(series.getLineWidth()));

            for (int i = 0; i < size - 1; i++) {
                canvas.drawLine(
                        xPx(xAxis.scale(series.getPointsList().get(i).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(i).getY())),
                        xPx(xAxis.scale(series.getPointsList().get(i+1).getX())),
                        yPx(yAxis.scale(series.getPointsList().get(i+1).getY())),
                        paint
                );
            }
        }
    }

    /**
     * Coordinates transformation
     * @param xCoord x coordinate relative to origin of graphics in pixels (intersection of x with y axis)
     * @return absolute coordinates from canvas in pixels (top-left corner of canvas)
     */
    protected float xPx(float xCoord) {

        float rtn;
//        float leftMargin = getLeftBorderPx() + getLeftTextBorderPx() + getYAxis().getWidth();
        float leftMargin = getLeftBorder() + getLeftTextBorder() + getYAxis().getWidth()/2.0f;

        if (xCoord > 0f) {
            if (leftMargin + xCoord > canvas.getWidth()) {
                rtn = canvas.getWidth();
            } else {
                rtn = leftMargin + xCoord;
            }
        } else {
            if (leftMargin + xCoord > 0f) {
                rtn = leftMargin + xCoord;
            } else {
                rtn = 0f;
            }
        }
        return rtn;
    }

    /**
     * Coordinates transformation
     * @param yCoord y coordinate relative to origin of graphics in pixels (intersection of x with y axis)
     * @return absolute coordinates from canvas in pixels (top-left corner of canvas)
     */
    protected float yPx(float yCoord) {

        float rtn;

        float bottomMargin = getBottomBorder() + getBottomTextBorder();
        float xAxisWidth =  getXAxis().getWidth()/2;
//        float xAxisWidth =  0f;

        if (yCoord > 0f) {
            if (bottomMargin + yCoord > canvas.getHeight()) {
                Log.i(TAG, "y coordinate: " + yCoord);
                rtn = canvas.getHeight();
            } else {
                rtn = canvas.getHeight() - bottomMargin - yCoord;
            }
        } else {

            if (bottomMargin + yCoord > 0f) {
                rtn = canvas.getHeight() - bottomMargin - yCoord + xAxisWidth;
            } else {
                rtn = canvas.getHeight() - xAxisWidth;
            }
        }
        return rtn;
    }
}