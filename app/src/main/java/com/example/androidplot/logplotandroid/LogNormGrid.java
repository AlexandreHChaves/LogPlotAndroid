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
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.widget.ImageView;

import static com.example.androidplot.logplotandroid.Utils.dpToPx;
import static com.example.androidplot.logplotandroid.Utils.spToPx;

/**
 * Created by achaves on 17-05-2016.
 *
 */
@Deprecated
public class LogNormGrid extends Grid {

    private final String TAG = "LogNormGrid";
    private final float ZERO_F = 0f;

    public LogNormGrid(Activity activity, ImageView imageView) {
        super(activity, imageView);
    }

    public LogNormGrid(Activity activity, Bitmap bitmap) {
        super(activity, bitmap);
    }

    @Override
    public void apply() {
        if (xAxis != null && yAxis != null) {

            paint.setColor(getBackGroundColor());

            canvas.drawRect(
                    getLeftBorder(),
                    getTopBorder(),
                    canvas.getWidth() - getRightBorder(),
                    canvas.getHeight() - getBottomBorder(),
                    paint
            );

            if (xAxis.label.hasText()) {
                paint.setTextSize(spToPx(xAxis.label.getSize()));
                if (xAxis.label.getVOffset() < ZERO_F) {
                    setBottomTextBorder(- dpToPx(xAxis.label.getVOffset()) + paint.getTextSize() + dpToPx(xAxis.getWidth()));
                } else {
                    setBottomTextBorder(paint.getTextSize() + dpToPx(xAxis.getWidth()));
                }
            }

            if (xAxis.gridRulers.label.getVOffset() < ZERO_F) {
                setBottomTextBorder(getBottomTextBorder() - dpToPx(xAxis.gridRulers.label.getVOffset()));
            }

            if (yAxis.label.hasText()) {
                paint.setTextSize(spToPx(yAxis.label.getSize()));

                if (yAxis.label.getHOffset() < ZERO_F) {
                    setLeftTextBorder(dpToPx(-yAxis.label.getHOffset()) + paint.getTextSize() + dpToPx(yAxis.getWidth()));
                } else {
                    setLeftTextBorder(paint.getTextSize() +  dpToPx(yAxis.getWidth()));
                }
            }

            if (yAxis.gridRulers.label.getHOffset() < ZERO_F) {
                setLeftTextBorder(getLeftTextBorder() - dpToPx(yAxis.gridRulers.label.getHOffset()));
            }

            xAxis.setPxLength(
                    canvas.getWidth() -
                            getLeftBorder() -
                            getLeftTextBorder() -
                            getRightBorder() -
                            getRightTextBorder()
            );
            yAxis.setPxLength(
                    canvas.getHeight() -
                            getTopBorder() -
                            getTopTextBorder() -
                            getBottomBorder() -
                            getBottomTextBorder()
            );

            xAxis.apply(); // evaluates rulers position in the axis in pixel units
            yAxis.apply();

            // drawing rulers of y axis, rulers parallel to x axis
            if (yAxis.hasGridRulers()) {
                for (Ruler ruler : yAxis.gridRulers.getRulers()) {
                    paint.setColor(ruler.getColor());
                    paint.setStrokeWidth(ruler.getWidth());
                    canvas.drawLine(
                            xPx(ZERO_F),
                            yPx(ruler.getPxPosition()),
                            xPx(xAxis.getPxLength()),
                            yPx(ruler.getPxPosition()),
                            paint
                    );
                    paint.setColor(ruler.label.getColor());
                    paint.setTextSize(spToPx(ruler.label.getSize()));

                    canvas.drawText(
                            roundStringValue(ruler.label.getText()),
                            xPx(dpToPx(ruler.label.getHOffset())),
                            yPx(ruler.getPxPosition() + dpToPx(ruler.label.getVOffset())),
                            paint
                    );
                }
            } else {
                Log.d(TAG, "yAxis has NO rulers");
            }

            // drawing rulers of x axis, rulers parallel to y axis
            if (xAxis.hasGridRulers()) {
                int i = 1;
                for (Ruler ruler : xAxis.gridRulers.getRulers()) {
                    paint.setColor(ruler.getColor());
                    paint.setStrokeWidth(dpToPx(ruler.getWidth()));
                    canvas.drawLine(
                            xPx(ruler.getPxPosition()),
                            yPx(ZERO_F),
                            xPx(ruler.getPxPosition()),
                            yPx(yAxis.getPxLength()),
                            paint
                    );
                    if (i%10 == 0) {
                        i = 1;
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
                Log.d(TAG, "xAxis has NO rulers");
            }

            // drawing personal rulers on y axis
            if (yAxis.hasUserRulers()) {
                for (Ruler ruler : yAxis.userRulers.getRulers()) {
                    paint.setColor(ruler.getColor());
                    paint.setStrokeWidth(ruler.getWidth());
                    canvas.drawLine(xPx(ZERO_F), yPx(ruler.getPxPosition()), xPx(xAxis.getPxLength()), yPx(ruler.getPxPosition()), paint);
                    paint.setColor(ruler.label.getColor());
                    paint.setTextSize(spToPx(ruler.label.getSize()));
                    canvas.drawText(
                            ruler.label.getText(),
                            xPx(dpToPx(ruler.label.getHOffset())),
                            yPx(ruler.getPxPosition() + ruler.label.getVOffset()),
                            paint
                    );
                }
            }

            // drawing personal rulers on x axis
            if (xAxis.hasUserRulers()) {
                for (Ruler ruler : xAxis.userRulers.getRulers()) {
                    paint.setColor(ruler.getColor());
                    paint.setStrokeWidth(dpToPx(ruler.getWidth()));
                    canvas.drawLine(
                            xPx(ruler.getPxPosition()),
                            yPx(ZERO_F),
                            xPx(ruler.getPxPosition()) ,
                            yPx(yAxis.getPxLength()),
                            paint
                    );
                    paint.setColor(ruler.label.getColor());
                    paint.setTextSize(spToPx(ruler.label.getSize()));
                    canvas.drawText(
                            ruler.label.getText(),
                            xPx(ruler.getPxPosition() + ruler.label.getHOffset()),
                            yPx(ruler.label.getVOffset()),
                            paint
                    );
                }
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

                float yOffset = getBottomTextBorder() - 0.4f*paint.getTextSize(); // 40% of the text size to stick to the bottom edge

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

                float xOffset = - getLeftTextBorder() + paint.getTextSize()*0.9f; // 90% of the text size to stick to the left border edge

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

    /**
     * @param value string value to round (ex: 0.30000003)
     * @return string value rounded (ex: 0.3)
     */
    private String roundStringValue(String value) {
        Double dValue = Double.valueOf(value) * 10d;
        long lValue = Math.round(dValue);
        dValue = lValue/10d;
        return String.valueOf(dValue);
    }
}
