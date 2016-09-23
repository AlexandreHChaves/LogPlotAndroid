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

/**
 * Created by achaves on 12-05-2016.
 *
 */
public abstract class Axis extends Ruler implements Scalable, Applyable{

    private final String TAG = "Axis";

    private final int RULERS_PER_INCH = 7;
    /** length of the axis in pixels */
    private float pxLength = 0f;
    /** the maximum value to be represented in axis */
    private double maxAxisRange = Double.POSITIVE_INFINITY;
    /** the minimum value to be represented in axis */
    private double minAxisRange = Double.NEGATIVE_INFINITY;
    /** distance between rulers in linear representation of axis in data units*/
    private Double step = 0d;

    /** true if the representation of data for the axis is linear */
    private boolean isLinear = true;

    /** array of rulers to be applied in the grid */
    public Rulers gridRulers = new Rulers();
    /** array of ruler defined by the user */
    protected Rulers userRulers = new Rulers();

    public Axis(){
        Log.i(TAG, ".............. creating new Axis() ..............");
        setWidth(2f);
        setColor(ChartColor.StandardColor.axis);
        setPosition(0d);
        setPxPosition(0f);
        label.setColor(ChartColor.StandardColor.axisLegend);
        gridRulers.label.setSize(0);
    }

    /** @return the length of this axis in pixels */
    public float getPxLength() {
        return pxLength;
    }

    /** @param pxLength set the length for this axis in pixels */
    public void setPxLength(float pxLength) {
        this.pxLength = pxLength;
    }

    /** @return the maximum value to be represented in this axis */
    public double getMaxAxisRange() {
        return maxAxisRange;
    }

    /** @param maxAxisRange sets the the maximum value to be represented in this axis */
    public void setMaxAxisRange(double maxAxisRange) {
        this.maxAxisRange = maxAxisRange;
    }

    /** @return the minimum value to be represented in this axis */
    public double getMinAxisRange() {
        return minAxisRange;
    }

    /** @return difference between maximum and minimum range of the axis.
     * The axis length in data units */
    public double getAxisRange() {
        return getMaxAxisRange() - getMinAxisRange();
    }

    /** @param minAxisRange set the minimum value to be represented in this axis */
    public void setMinAxisRange(double minAxisRange) {
        this.minAxisRange = minAxisRange;
    }

    /**
     * replace by axis.gridRulers
     */
    @Deprecated
    public Rulers getGridRulers() {
        return gridRulers;
    }

    @Deprecated
    public void setGridRulers(ArrayList<Ruler> gridRulers) {
        if (gridRulers != null) {
            this.gridRulers.setRulers(gridRulers);
        }
    }

    @Deprecated
    public void addGridRuler(Ruler ruler) {
        if (ruler != null) {
            this.gridRulers.addRuler(ruler);
        }
    }

    @Deprecated
    public Ruler getGridRuler(int index) {
        return this.gridRulers.getRuler(index);
    }

    @Deprecated
    public ArrayList<Ruler> getUserRulers() {
        return userRulers.getRulers();
    }

    public void setUserRulers(ArrayList<Ruler> userRulers) {
        this.userRulers.setRulers(userRulers);
    }

    @Deprecated
    public void addUserRuler(Ruler ruler) {
        userRulers.addRuler(ruler);
    }

    /**
     * distance between rulers in data units (for linear axis)
     */
    public Double getStep() {
        return step;
    }

    /**
     * @param step distance between rulers in data units (for linear axis)
     */
    public void setStep(Double step) {
        this.step = step;
    }

    /** @return  true if the axis has linear representation of data */
    public boolean isLinear() {
        return isLinear;
    }

    /** set true if the representation of data for the axis is linear */
    protected void setLinear(boolean linear) {
        isLinear = linear;
    }

    protected boolean getIsLinear() {
        return isLinear;
    }

    public boolean hasGridRulers() {
        return gridRulers.getRulers().size() > 0;
    }

    public boolean hasUserRulers() {
        return userRulers.getRulers().size() > 0;
    }

    /**
     * This method must be called after a length for the axis has been defined with setPxLength()
     * @return Maximum number of rulers per axis excluding rulers defined by the user
     */
    public int getMaxRulers(){
        float inches = getPxLength()/SavedMetrics.Metrics.densityDpi;

        return (int) Math.floor(inches * RULERS_PER_INCH);
    }

    @Override
    public float scale(double value) {

        if (Double.isInfinite(getMaxAxisRange())) {
            Log.e(TAG, "maximum axis range not defined: infinity");
            return -1;
        }

        if (getMinAxisRange() > getMaxAxisRange()) {
            Log.e(TAG, "minimum > maximum; limits for the axis range ill defined; review axis limits");
            return -1;
        }

        if (getPxLength() == 0f) {
            Log.e(TAG, "invalid value for axis length: 0px");
            return -1;
        }

        if (Double.isInfinite(getMinAxisRange())) {
            Log.e(TAG, "minimum axis range not defined: infinity");
            return -1;
        }

        if (value < getMinAxisRange()) {
            Log.e(TAG, "value out of range: " + value );
            Log.e(TAG, "minimum admissible value: " + getMinAxisRange());
            return -1;
        }

        if (value > getMaxAxisRange()) {
            Log.e(TAG, "value out of range: " + value );
            Log.e(TAG, "maximum admissible value: " + getMaxAxisRange());
            return -1;
        }

        return (float) value;
    }

    @Override
    public void apply() {
        Log.d(TAG, "........ apply() .............");
        gridRulers.setAllRulersColor();
        gridRulers.setAllRulersWidth();
        gridRulers.setAllRulersLabelSize();
        gridRulers.setAllRulersLabelColor();
        gridRulers.setAllRulersHOffsetLabel();
        gridRulers.setAllRulersVOffsetLabel();
        gridRulers.setAllRulersLabelAngle();
    }
}
