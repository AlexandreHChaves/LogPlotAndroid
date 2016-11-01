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

import java.security.acl.LastOwnerException;
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

    /**
     * distance between rulers in data units (for linear axis)
     */
//    public Double getStep() {
//        return rulerStep;
//    }

//    /**
//     * @param rulerStep distance between rulers in data units (for linear axis)
//     */
//    public void setStep(Double rulerStep) {
//        this.rulerStep = rulerStep;
//    }


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
     * @return distance at which rulers will be placed
     */
    public static RulerStep findRulerStep(final double range) {

        RulerStep rulerStep = new RulerStep();

        if (range <= 0d) {
            Log.e(TAG, "range value must be a positive number bigger than 0 (zero)");
            new RulerStep(); // returning empty rulerStep
        }

        /** minimum number of rulers in the plot */
        final int N_RULERS = 5;
        final int MAX_CYCLES = 100;
        final double n_step = range/N_RULERS;

        double s = 1d; // singler
        double d = 2d; // doubler
        double f = 5d; // fiver
        StepType stepType;

        List<Double> steppingList = new ArrayList<>();
        steppingList.add(0, s);
        steppingList.add(1, d);
        steppingList.add(2, f);

        if (n_step == s) {
            rulerStep.setValue(s);
            return rulerStep;
        }

        if (n_step == d) {
            rulerStep.setValue(d);
            return rulerStep;
        }

        if (n_step == f) {
            rulerStep.setValue(f);
            return rulerStep;
        }

        if (n_step > s) {
            stepType = StepType.BIGGER_1;
        } else {
            stepType = StepType.MINOR_1;
        }

        int fuse = 0;

        switch (stepType) {
            case BIGGER_1:
                while (true) {
                    // returning the biggest stepping number that is smaller than rulerStep
                    if (n_step > Collections.max(steppingList)) {
                        s = s*10d;
                        d = d*10d;
                        f = f*10d;
                        steppingList.add(s);
                        steppingList.add(d);
                        steppingList.add(f);
                    } else {

                        if (n_step == steppingList.get(steppingList.size() - 1)) {
                            rulerStep.setValue(steppingList.get(steppingList.size() - 1));
                            return rulerStep;
                        }
                        if (n_step == steppingList.get(steppingList.size() - 2)) {
                            rulerStep.setValue(steppingList.get(steppingList.size() - 2));
                            return rulerStep;
                        }
                        if (n_step == steppingList.get(steppingList.size() - 3)) {
                            rulerStep.setValue(steppingList.get(steppingList.size() - 3));
                            return rulerStep;
                        }


                        if(n_step > steppingList.get(steppingList.size() - 1 - 2)){ // compare with singler
                            if (n_step > steppingList.get(steppingList.size() - 1 - 1)) { // compare with doubler
                                // return a doubler because is not bigger than maximum (the next fiver)
                                rulerStep.setValue(steppingList.get(steppingList.size() - 1 - 1));
                                return rulerStep;
                            } else {
                                rulerStep.setValue(steppingList.get(steppingList.size() - 1 - 2));
                                return rulerStep; // return singler
                            }
                        } else {
                            rulerStep.setValue(steppingList.get(steppingList.size() - 1 - 3));
                            return rulerStep; // return a fiver
                        }
                    }
                    fuse += 1;

                    if (fuse == MAX_CYCLES) {
                        Log.e(TAG, "number of cycles has been surpassed in BIGGER_1 cycle: returning ZERO");
                        return new RulerStep(); // returning empty rulerStep
                    }
                }

            case MINOR_1:

//                rulerStep.setPrecision(10);
                rulerStep.setPrecision(1);

                steppingList.clear();
                s = s/10d;
                d = d/10d;
                f = f/10d;
                steppingList.add(f);
                steppingList.add(d);
                steppingList.add(s);

                while (true) {
                    if (n_step < Collections.min(steppingList)) {
//                        rulerStep.setPrecision(rulerStep.getPrecision() * 10);
                        rulerStep.setPrecision(rulerStep.getPrecision() + 1);
                        s = s/10d;
                        d = d/10d;
                        f = f/10d;
                        steppingList.add(f);
                        steppingList.add(d);
                        steppingList.add(s);

                    } else {

                        if (n_step == steppingList.get(steppingList.size() - 1)) {
                            rulerStep.setValue(steppingList.get(steppingList.size() - 1));
                            return rulerStep;
                        }
                        if (n_step == steppingList.get(steppingList.size() - 2)) {
                            rulerStep.setValue(steppingList.get(steppingList.size() - 2));
                            return rulerStep;
                        }
                        if (n_step == steppingList.get(steppingList.size() - 3)) {
                            rulerStep.setValue(steppingList.get(steppingList.size() - 3));
                            return rulerStep;
                        }

                        if (n_step > steppingList.get(steppingList.size() - 1 - 1)) { // compare with doubler
                            if (n_step > steppingList.get(steppingList.size() - 1 - 2)) { // compare with fiver
                                rulerStep.setValue(steppingList.get(steppingList.size() - 1 - 2)); // return fiver
                                return rulerStep;
                            } else {
                                rulerStep.setValue(steppingList.get(steppingList.size() - 1 - 1)); // return doubler
                                return rulerStep;
                            }
                        } else {
                            rulerStep.setValue(steppingList.get(steppingList.size() - 1)); // return singler
                            return rulerStep;
                        }
                    }

                    fuse += 1;

                    if (fuse == MAX_CYCLES) {
                        Log.e(TAG, "number of cycles has been surpassed in MINOR_1 cycle: returning ZERO");
                        return new RulerStep();
                    }
                }
        }

        return new RulerStep();
    }

    public static class RulerStep {
        private double value = 0;
        /** ex: 100 means that number to be represented has 2 decimal places like 0.02  */
        private int precision = 1;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public int getPrecision() {
            if (precision < 1) {
                return 1;
            } else {
                return (int) Math.log10(precision);
            }
        }

        /**
         * @param precision number of decimal places that a number to be represented has <br>
         *                  ex:<br>
         *                  0.008 -> 3<br>
         *                  3 -> 0<br>
         *                  3.25 -> 2
         */
        public void setPrecision(int precision) {
            if (precision < 0) {
                Log.e(TAG, "WARNING: negative value for precision is invalid; assuming value 0 'zero'");
                this.precision = 1; // 10E0 = 1
            }
            this.precision = (int) Math.pow(10, precision);
        }

        public String getStringValue(double value) {
            Double dValue = value * precision;
            long lValue = Math.round(dValue);
            dValue = lValue/(double)precision;
            return String.valueOf(dValue);
        }
    }
}
