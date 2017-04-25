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
import java.util.Collections;
import java.util.List;

/**
 * Created by achaves on 12-05-2016.
 *
 */
public abstract class Axis extends Ruler implements Scalable, Applyable{

    private static final String TAG = "Axis";

    private final int RULERS_PER_INCH = 7;
    /** length of the axis in pixels */
    private float pxLength = 0f;
    /** the maximum value to be represented in axis */
    private double maxAxisRange = Double.POSITIVE_INFINITY;
    /** the minimum value to be represented in axis */
    private double minAxisRange = Double.NEGATIVE_INFINITY;
    /** distance between rulers in linear representation of axis in data units*/
//    private Double rulerStep = 0d;
    public RulerStep rulerStep;

    /** true if the representation of data for the axis is linear */
    private boolean isLinear = true;

    /** array of rulers to be applied in the grid */
    public Rulers gridRulers = new Rulers();
    /** array of ruler defined by the user */
    public Rulers userRulers = new Rulers();

    public Axis(){
        rulerStep = new RulerStep();
        Log.i(TAG, ".............. creating new Axis() ..............");
        setWidth(2f);
        setColor(ChartColor.StandardColor.axis);
        setPosition(0d);
        setPxPosition(0f);
        label.setColor(ChartColor.StandardColor.axisLegend);
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

    public RulerStep getRulerStep() {
        return rulerStep;
    }

    public void setRulerStep(RulerStep rulerStep) {
        this.rulerStep = rulerStep;
    }

    public void setRulerStep(double value, int precision) {
        this.rulerStep.setValue(value);
        this.rulerStep.setPrecision(precision);
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
        gridRulers.setAllRulersDash();
        gridRulers.setAllRulersBlank();
    }

    private enum StepType{
        BIGGER_1,
        MINOR_1
    }

    /**
     * @param range distance between minimum and maximum values to be represented in the axis
     * @param minNumberRulers minimum number of rulers in the graphics
     * @return distance at which rulers will be placed
     */
    public static Axis.RulerStep findRulerStep(final double range, final int minNumberRulers) {

        Axis.RulerStep rulerStep = new Axis.RulerStep();

        if (range <= 0d) {
            Log.e(TAG, "range value must be a positive number bigger than 0 (zero)");
            new Axis.RulerStep(); // returning empty rulerStep
        }

        if (minNumberRulers <= 0) {
            Log.e(TAG, "the minimum number of rulers must be a positive number bigger than 0 (zero)");
            new Axis.RulerStep(); // returning empty rulerStep
        }

        final double stepValue = range/(double)minNumberRulers;

        double singler = 1d; // singler
        double doubler = 2d; // doubler
        double threer = 3d;
        double fourer = 4d;
        double fiver = 5d; // fiver
        double sixer = 6d;
        double sevener = 7d;
        double eighter = 8d;
        double niner = 9d;
        StepType stepType;

        if (stepValue >= singler) {
            stepType = StepType.BIGGER_1;
        } else {
            stepType = StepType.MINOR_1;
        }

        List<Double> stepListCandidate = new ArrayList<>();

        List<List<Double>> stepsList = new ArrayList<>();

        if (stepType == StepType.BIGGER_1) {

            stepListCandidate.add(0, singler);
            stepListCandidate.add(1, doubler);
            stepListCandidate.add(2, threer);
            stepListCandidate.add(3, fourer);
            stepListCandidate.add(4, fiver);
            stepListCandidate.add(5, sixer);
            stepListCandidate.add(6, sevener);
            stepListCandidate.add(7, eighter);
            stepListCandidate.add(8, niner);

            stepsList.add(stepListCandidate);

            while (stepValue > Collections.max(stepsList.get(stepsList.size()-1))) {
                List<Double> steps = new ArrayList<>();

                for (Double d : stepsList.get(stepsList.size()-1)) {
                    steps.add(d * 10);
                }
                stepsList.add(steps);
            }
        }

        if (stepType == StepType.MINOR_1) {
            rulerStep.setPrecision(1);

            singler = singler/10d;
            doubler = doubler/10d;
            threer = threer/10d;
            fourer = fourer/10d;
            fiver = fiver/10d;
            sixer = sixer/10d;
            sevener = sevener/10d;
            eighter = eighter/10d;
            niner = niner/10d;

            stepListCandidate.add(0, singler);
            stepListCandidate.add(1, doubler);
            stepListCandidate.add(2, threer);
            stepListCandidate.add(3, fourer);
            stepListCandidate.add(4, fiver);
            stepListCandidate.add(5, sixer);
            stepListCandidate.add(6, sevener);
            stepListCandidate.add(7, eighter);
            stepListCandidate.add(8, niner);

            stepsList.add(stepListCandidate);

            while (stepValue < Collections.min(stepsList.get(stepsList.size() - 1))) {
                List<Double> steps = new ArrayList<>();
                rulerStep.setPrecision(rulerStep.getPrecision() + 1);
                for (Double d : stepsList.get(stepsList.size()-1)) {
                    steps.add(d / 10);
                }
                stepsList.add(steps);
            }
        }

        int nLists = stepsList.size();

        for (int j = nLists-1; j >= 0; j--) {
            List<Double> lastStepList = stepsList.get(j);
            for (int i = lastStepList.size() - 1; i >= 0; i--) {
                if (lastStepList.get(i) <= stepValue) {
                    rulerStep.setValue(lastStepList.get(i));
                    return rulerStep;
                }
            }
        }

        return new Axis.RulerStep();
    }

    public static class RulerStep {
        /** numeric value of the data */
        private double value = 0;
        /** ex: 2 means that number has to be represented with 2 decimal places like 0.02  */
        private int precision = 1;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public int getPrecision() {
            if (precision > 0) {
                return (int) Math.log10(precision);
            }
            else {
                return precision;
            }
        }

        /**
         * @param precision number of decimal places that a number to be represented has <br>
         *                  ex:<br>
         *                  0.008 -> precision 3<br>
         *                  3 -> precision 0<br>
         *                  3.25 -> precision 2
         */
        public void setPrecision(int precision) {

            if (precision < 0) {
                Log.e(TAG, "WARNING: negative value for precision is invalid; assuming value 0 'zero'");
                this.precision = 0;
            }
            else if (precision == 0) {
                this.precision = 0;
            }
            else
                this.precision = (int) Math.pow(10, precision);
        }

        public String getStringValue(double value) {
            if (precision != 0) {
                Double dValue = value * precision;
                long lValue = Math.round(dValue);
                dValue = lValue / (double) precision;
                return String.valueOf(dValue);
            }
            else {
                long lValue = Math.round(value);
                Log.d(TAG, "lValue: " + lValue);
                int dotPosition = String.valueOf(lValue).indexOf(".");
                if (dotPosition != -1)
                    return String.valueOf(lValue).substring(0, dotPosition);
                else {
                    return String.valueOf(value);
                }
            }
        }
    }
}
