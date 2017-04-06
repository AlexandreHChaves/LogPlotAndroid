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
public class Grid implements Applyable{

    private final String TAG = "Grid";
//    private GridBorders gridLimits;

    private final float ZERO_F = 0f;

    private float leftBorder = 0f;
    private float rightBorder = 0f;
    private float topBorder = 0f;

    private float bottomBorder = 0f;
    private float leftTextBorder = 0f;
    private float topTextBorder = 0f;
    private float rightTextBorder = 0f;
    private float bottomTextBorder = 0f;

    private Label label;

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
        label = new Label();
    }

    public Grid(Activity activity, Bitmap bitmap) {
        initMetrics(activity);
        this.bitmap = bitmap;
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setAntiAlias(true); // smooths the drawing
        backGroundColor = ChartColor.StandardColor.background;
        label = new Label();
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
     * @param leftBorder position of the left border of the grid in dp units
     */
    public void setLeftBorder(float leftBorder) {
        this.leftBorder = leftBorder;
    }

    public float getLeftBorder() {
        return leftBorder;
    }

    /**
     * @param rightBorder position of the right border of the grid in dp units
     */
    public void setRightBorder(float rightBorder) {
        this.rightBorder = rightBorder;
    }

    /**
     * @return right margin of the grid in dp units
     */
    public float getRightBorder() {
        return rightBorder;
    }

    /**
     * @param topBorder position of the top border of the grid in dp
     */
    public void setTopBorder(float topBorder) {
        this.topBorder = topBorder;
    }

    public float getTopBorder() {
        return topBorder;
    }

    /**
     * @param bottomBorder position of the bottom border of the grid in dp units
     */
    public void setBottomBorder(float bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public float getBottomBorder() {
        return bottomBorder;
    }

    /** @return  The left label border of the graphic in dp units */
    public float getLeftTextBorder() {
        return leftTextBorder;
    }

    /** @param leftTextBorder  The left text border of the graphic in dp units */
    public void setLeftTextBorder(float leftTextBorder) {
        this.leftTextBorder = leftTextBorder;
    }

    /**
     * @return The top label border of the graphic in dp units
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

    /** set the bottom label margin border in dp units*/
    public void setBottomTextBorder(float bottomTextBorder) {
        this.bottomTextBorder = bottomTextBorder;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    /**
     * @param point write into the graphic the identification label point
     */
    private void writePointText(PointPlot point) {

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
    private void writePointCoordinate(PointPlot point) {

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

    private void plotPoint(PointPlot point) {
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
            path.moveTo( // 1st point position
                    xPx(xAxis.scale(series.getPointsList().get(0).getX())),
                    yPx(yAxis.scale(series.getPointsList().get(0).getY()))
            );

            int remainingPoints = size%3; // because of cubic interpolation

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
            path.moveTo( // 1st point position
                    xPx(xAxis.scale(series.getPointsList().get(0).getX())),
                    yPx(yAxis.scale(series.getPointsList().get(0).getY()))
            );

            int remainingPoints = size%2; // because of quadratic interpolation

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
        float leftMargin = dpToPx(getLeftBorder()) + dpToPx(getLeftTextBorder()) + getYAxis().getWidth()/2.0f;

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

        float bottomMargin = dpToPx(getBottomBorder()) + dpToPx(getBottomTextBorder());
        float xAxisWidth =  getXAxis().getWidth()/2;
//        float xAxisWidth =  0f;

        if (yCoord > 0f) {
            if (bottomMargin + yCoord > canvas.getHeight()) {
//                Log.i(TAG, "y coordinate: " + yCoord);
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

    @Override
    public void apply() {

        final String METHOD_NAME = "......... apply() ...........";

        if (xAxis != null && yAxis != null) {

            paint.setColor(getBackGroundColor());

            canvas.drawRect(
                    dpToPx(getLeftBorder()),
                    dpToPx(getTopBorder()),
                    canvas.getWidth() - dpToPx(getRightBorder()),
                    canvas.getHeight() - dpToPx(getBottomBorder()),
                    paint
            );

            if (xAxis.label.hasText()) {
                paint.setTextSize(spToPx(xAxis.label.getSize()));
                if (xAxis.label.getVOffset() < ZERO_F) {
                    setBottomTextBorder(getBottomTextBorder() - xAxis.label.getVOffset() + paint.getTextSize() + xAxis.getWidth());
                } else {
                    setBottomTextBorder(getBottomTextBorder() + paint.getTextSize() + xAxis.getWidth());
                }
            }

            if (xAxis.gridRulers.label.getVOffset() < ZERO_F) {
                setBottomTextBorder(getBottomTextBorder() - xAxis.gridRulers.label.getVOffset());
            }

            if (yAxis.label.hasText()) {
                paint.setTextSize(spToPx(yAxis.label.getSize()));

                if (yAxis.label.getHOffset() < ZERO_F) {
                    setLeftTextBorder(getLeftTextBorder() - yAxis.label.getHOffset() + paint.getTextSize() + yAxis.getWidth());
                } else {
                    setLeftTextBorder(getLeftTextBorder() + paint.getTextSize() +  yAxis.getWidth());
                }
            }

            if (yAxis.gridRulers.label.getHOffset() < ZERO_F) {
                setLeftTextBorder(getLeftTextBorder() - yAxis.gridRulers.label.getHOffset());
            }

            xAxis.setPxLength(
                    canvas.getWidth() -
                            dpToPx(getLeftBorder()) -
                            dpToPx(getLeftTextBorder()) -
                            dpToPx(getRightBorder()) -
                            dpToPx(getRightTextBorder())
            );
            yAxis.setPxLength(
                    canvas.getHeight() -
                            dpToPx(getTopBorder()) -
                            dpToPx(getTopTextBorder()) -
                            dpToPx(getBottomBorder()) -
                            dpToPx(getBottomTextBorder())
            );

            xAxis.apply(); // evaluates rulers position in the axis in pixel units
            yAxis.apply();

            // drawing rulers of y axis, rulers parallel to x axis
            if (yAxis.hasGridRulers()) {
                int i = 0;
                for (Ruler ruler : yAxis.gridRulers.getRulers()) {
                    paint.setColor(ruler.getColor());
                    paint.setStrokeWidth(ruler.getWidth());

                    drawHorizontalRuler(
                            canvas,
                            ruler,
                            xAxis,
                            paint
                    );

                    if (i%(yAxis.gridRulers.getLabelSpacing())== 0) { // print label for each nth ruler
//                        i = 1;
//                        i++;
                        paint.setColor(ruler.label.getColor());
                        paint.setTextSize(spToPx(ruler.label.getSize()));

                        canvas.drawText(
                                ruler.label.getText(),
                                xPx(dpToPx(ruler.label.getHOffset())),
                                yPx(ruler.getPxPosition() + dpToPx(ruler.label.getVOffset())),
                                paint
                        );
                    }
                    i++;
                }
            } else {
                Log.d(TAG, "yAxis has NO rulers");
            }

            // drawing rulers of x axis, rulers parallel to y axis
            if (xAxis.hasGridRulers()) {
                int i = 0;
                for (Ruler ruler : xAxis.gridRulers.getRulers()) {
                    paint.setColor(ruler.getColor());
                    paint.setStrokeWidth(dpToPx(ruler.getWidth()));

                    drawVerticalRuler(
                            canvas,
                            ruler,
                            yAxis,
                            paint
                    );

//                    Log.i(TAG, "label value: " + ruler.label.getText());
//                    Log.i(TAG, "i: " + i);
                    if (i%(xAxis.gridRulers.getLabelSpacing()) == 0) { // print label for each nth ruler
//                    if (i%10 == 0) { // print label for each nth ruler
//                        i = 1;
//                        Log.i(TAG, "i: " + i);
//                        Log.i(TAG, "i%xAxis.gridRulers.getLabelSpacing(): " + i%xAxis.gridRulers.getLabelSpacing());
//                        Log.i(TAG, "label value: " + ruler.label.getText());
                        paint.setColor(ruler.label.getColor());
                        paint.setTextSize(spToPx(ruler.label.getSize()));
                        canvas.drawText(
                                ruler.label.getText(),
                                xPx(ruler.getPxPosition() + dpToPx(ruler.label.getHOffset())),
                                yPx(dpToPx(ruler.label.getVOffset())),
                                paint
                        );
                    }
                    i++;
                }
            } else {
                Log.i(TAG, "xAxis has NO rulers");
            }

            // drawing personal rulers on y axis
            if (yAxis.hasUserRulers()) {
                for (Ruler ruler : yAxis.userRulers.getRulers()) {
                    paint.setColor(ruler.getColor());
                    paint.setStrokeWidth(ruler.getWidth());

                    drawHorizontalRuler(
                            canvas,
                            ruler,
                            xAxis,
                            paint
                    );

                    paint.setColor(ruler.label.getColor());
                    paint.setTextSize(spToPx(ruler.label.getSize()));
                    canvas.drawText(
                            ruler.label.getText(),
                            xPx(dpToPx(ruler.label.getHOffset())),
                            yPx(ruler.getPxPosition() + dpToPx(ruler.label.getVOffset())),
                            paint
                    );
                }
            } else {
                printLog(METHOD_NAME);
                printLog("y axis has no personal rulers");
            }

            // drawing personal rulers on x axis
            if (xAxis.hasUserRulers()) {
                for (Ruler ruler : xAxis.userRulers.getRulers()) {
                    paint.setColor(ruler.getColor());
                    paint.setStrokeWidth(dpToPx(ruler.getWidth()));

                    drawVerticalRuler(
                            canvas,
                            ruler,
                            yAxis,
                            paint
                    );
//                    canvas.drawLine(
//                            xPx(ruler.getPxPosition()),
//                            yPx(ZERO_F),
//                            xPx(ruler.getPxPosition()) ,
//                            yPx(yAxis.getPxLength()),
//                            paint
//                    );
                    paint.setColor(ruler.label.getColor());
                    paint.setTextSize(spToPx(ruler.label.getSize()));
                    canvas.drawText(
                            ruler.label.getText(),
                            xPx(ruler.getPxPosition() + dpToPx(ruler.label.getHOffset())),
                            yPx(dpToPx(ruler.label.getVOffset())),
                            paint
                    );
                }
            } else {
                printLog(METHOD_NAME);
                printLog("x axis has no personal rulers");
            }

            // drawing xAxis
            paint.setColor(getXAxis().getColor());
            paint.setStrokeWidth(dpToPx(getXAxis().getWidth()));
            canvas.drawLine(
                    xPx(getXAxis().getPxPosition()),
                    yPx(getYAxis().getPxPosition()),
                    xPx(getXAxis().getPxLength()),
                    yPx(getYAxis().getPxPosition()),
                    paint
            );

            if (xAxis.label.hasText()) {
                canvas.save();
                paint.setTextSize(spToPx(xAxis.label.getSize()));
                paint.setColor(xAxis.label.getColor());

                float yOffset = dpToPx(getBottomTextBorder()) - 0.4f*paint.getTextSize(); // 40% of the text size to stick to the bottom edge

                Path path = new Path();
                path.moveTo(xPx(ZERO_F), yPx(ZERO_F));
                path.lineTo(xPx(xAxis.getPxLength()), yPx(ZERO_F));
                paint.setStyle(Paint.Style.FILL);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawTextOnPath(xAxis.label.getText(), path, ZERO_F, yOffset, paint);
                canvas.restore();
            }

            // drawing yAxis
            paint.setColor(getYAxis().getColor());
            paint.setStrokeWidth(dpToPx(getYAxis().getWidth()));
            canvas.drawLine(
                    xPx(getXAxis().getPxPosition()),
                    yPx(getYAxis().getPxPosition()),
                    xPx(getXAxis().getPxPosition()),
                    yPx(getYAxis().getPxLength()),
                    paint
            );

            if (yAxis.label.hasText()) {
                canvas.save();
                paint.setTextSize(spToPx(yAxis.label.getSize()));
                paint.setColor(yAxis.label.getColor());

                float xOffset = - dpToPx(getLeftTextBorder()) + paint.getTextSize()*0.9f; // 90% of the text size to stick to the left border edge

                Path path = new Path();
                path.moveTo(xPx(ZERO_F), yPx(ZERO_F));
                path.lineTo(xPx(ZERO_F), yPx(yAxis.getPxLength()));
                paint.setStyle(Paint.Style.FILL);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawTextOnPath(yAxis.label.getText(), path, ZERO_F, xOffset, paint);
                canvas.restore();
            }
        }
    }

    private void drawVerticalRuler(
            Canvas canvas,
            Ruler ruler,
            Axis axis,
            Paint paint
    ) {
        float x1;
        float y1;
        float x2;
        float y2;
        float pxDash;
        float pxBlank;

        paint.setColor(ruler.getColor());
        paint.setStrokeWidth(ruler.getWidth());

        x1 = ruler.getPxPosition();
        x2 = ruler.getPxPosition();
        pxDash = dpToPx(ruler.getDash());
        pxBlank = dpToPx(ruler.getBlank());

        if (pxDash == ZERO_F) {
            canvas.drawLine(
                    xPx(ruler.getPxPosition()),
                    yPx(ZERO_F),
                    xPx(ruler.getPxPosition()),
                    yPx(axis.getPxLength()),
                    paint
            );
            return;
        }

        y1 = ZERO_F;
        y2 = pxDash;

        while (y1 <= axis.getPxLength()) {
            if (y2 > axis.getPxLength()) {
                canvas.drawLine(
                        xPx(x1),
                        yPx(y1),
                        xPx(x2),
                        yPx(axis.getPxLength()),
                        paint
                );
                return;
            }

            canvas.drawLine(
                    xPx(x1),
                    yPx(y1),
                    xPx(x2),
                    yPx(y2),
                    paint
            );

            y1 = y2 + pxBlank;
            y2 = y1 + pxDash;

        }
    }

    /**
     * This draw a horizontal ruler across the plot
     * @param canvas
     * @param ruler
     * @param axis
     * @param paint
     */
    private void drawHorizontalRuler(
            Canvas canvas,
            Ruler ruler,
            Axis axis,
            Paint paint
    ) {

        float x1;
        float y1;
        float x2;
        float y2;
        float pxDash;
        float pxBlank;


        paint.setColor(ruler.getColor());
        paint.setStrokeWidth(ruler.getWidth());

        y1 = ruler.getPxPosition();
        y2 = ruler.getPxPosition();
        pxDash = dpToPx(ruler.getDash());
        pxBlank = dpToPx(ruler.getBlank());

        if (pxDash == ZERO_F) {
            canvas.drawLine(
                    xPx(ZERO_F),
                    yPx(y1),
                    xPx(axis.getPxLength()),
                    yPx(y2),
                    paint
            );
            return;
        }

        x1 = ZERO_F;
        x2 = pxDash;

        while (x1 <= axis.getPxLength()) {

            if (x2 > axis.getPxLength()) {
                canvas.drawLine(
                        xPx(x1),
                        yPx(y1),
                        xPx(axis.getPxLength()),
                        yPx(y2),
                        paint
                );
                return;
            }

            canvas.drawLine(
                    xPx(x1),
                    yPx(y1),
                    xPx(x2),
                    yPx(y2),
                    paint
            );

            x1 = x2 + pxBlank;
            x2 = x1 + pxDash;
        }
    }

    private void printLog(String message) {
        Log.i(TAG, message);
    }

    private void printErrorLog(String methodName, String message){
        printLog(methodName);
        Log.e(TAG, message);
    }
}
