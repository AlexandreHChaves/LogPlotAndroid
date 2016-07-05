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

import android.support.annotation.NonNull;

/** Created by achaves on 10-05-2016. */
public class PointPlot implements Cloneable {

    private final String TAG = "PointPlot";

    private Double x;
    private Double y;
    private int color;
    private String text = "";
    private float testVOffset = 0f;
    private float testHOffset = 0f;
    private int textColor;
    private float textSize; // sp units
    private float size = 1; // point size dp
    private ChartPointType pointType = ChartPointType.BULLETS;

    public PointPlot(Double x, Double y) {
        this.x = x;
        this.y = y;
        color = ChartColor.StandardColor.point;
        textColor = ChartColor.StandardColor.pointTextColor;
        textSize = 10;
    }

    private PointPlot(PointPlot point) {
        setPoint(point.getX(), point.getY());
        setColor(point.getColor());
        setText(point.getText());
        setTextHOffset(point.getTextHOffset());
        setTextVOffset(point.getTextVOffset());
        setTextColor(point.getTextColor());
        setTextSize(point.getTextSize());
        setSize(point.getSize());
        setPointType(point.getPointType());
    }

    @Override
    public PointPlot clone() {

        PointPlot pointCopy;

        try {
            pointCopy = (PointPlot) super.clone();
            pointCopy.setPointType(this.getPointType());
            return pointCopy;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new PointPlot(this);
        }
    }

    public void setPoint(@NonNull Double x, @NonNull Double y) {
        this.x = x;
        this.y = y;
    }

    public void setPoint(
            @NonNull Double x,
            @NonNull Double y,
            ChartPointType pointType
    ) {
        this.x = x;
        this.y = y;
        this.pointType = pointType;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    /** @return  vertical text offset to point's center in dp units */
    public float getTextVOffset() {
        return testVOffset;
    }

    /** @param testVOffset vertical text offset to point's center in dp units */
    public void setTextVOffset(float testVOffset) {
        this.testVOffset = testVOffset;
    }

    /** @return horizontal text offset to to point's center in dp units */
    public float getTextHOffset() {
        return testHOffset;
    }

    /** @param testHOffset  horizontal text offset to to point's center in dp units */
    public void setTextHOffset(float testHOffset) {
        this.testHOffset = testHOffset;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /** @return the text size in sp */
    public float getTextSize() {
        return textSize;
    }

    /** @param textSize the text size in sp */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    @Override
    public String toString() {
        return "(" + x.toString() + ", " + y.toString() + ")";
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public ChartPointType getPointType() {
        return pointType;
    }

    public void setPointType(ChartPointType pointType) {
        this.pointType = pointType;
    }

}
