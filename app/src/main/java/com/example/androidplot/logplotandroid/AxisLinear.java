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

/**
 * Created by achaves on 12-05-2016.
 *
 */
public class AxisLinear extends Axis {

    private final String TAG = "AxisLinear";

    public AxisLinear() {
        Log.d(TAG, "Creating AxisLinear()");
        setPosition(0d);
        setLinear(true);
    }

    @Override
    public float scale(double value) {

        if (Double.isInfinite(getMaxAxisRange())) {
            Log.e(TAG, "axis range not defined: infinity");
            return 0f;
        }

        if (Double.isInfinite(getMaxAxisRange())) {
            Log.e(TAG, "axis range not defined: infinity");
            return 0f;
        }

        if (getMinAxisRange() > getMaxAxisRange()) {
            Log.e(TAG, "minimum > maximum; limits for the axis range ill defined; review axis limits");
            return 0f;
        }

        if (getPxLength() == 0f) {
            Log.e(TAG, "invalid value for axis length: 0px");
            return 0f;
        }
//
//        Log.d(TAG, "value: " + value);
//        Log.d(TAG, "axisRange: " + axisRange);
//        Log.d(TAG, "pxLength: " + pxLength);
//
//        Log.d(TAG, "(value * pxLength / axisRange) :" + (value * pxLength / axisRange));

        // px = m*value + b; b = 0
        return (float) (value * getPxLength() / (getAxisRange()));
    }

    @Override
    public void apply() {
        Log.d(TAG, "............. apply() ...........");

        if (getMinAxisRange() >= getMaxAxisRange()) {
            Log.e(TAG, "range axis limits badly defined; review minimum and maximum axis range");
            return;
        }

        final int MAX_ITERACTIONS = 1000;

        if (getIsLinear()){ // this only works if the axis scale is linear
            if (getStep() > 0d) {
                float pxStep = scale(getStep());
//                Log.i(TAG, "pxStep: " + pxStep);
                if (pxStep < getPxLength()) {

                    double firstStep = getMinAxisRange() + (getStep() - getMinAxisRange()%getStep());
                    double rulerPosition = firstStep;
                    float rulerPxPosition = scale(firstStep);

                    int i = 0; // fuse
                    while (rulerPxPosition <= getPxLength()) {

                        if (i == MAX_ITERACTIONS) {
                            getGridRulers().getRulers().clear();
                            Log.e(TAG,
                                    "cycle to assign rulers blew up; \n" +
                                            "maybe step to short for the axis;\n" +
                                            "step value: " + getStep() + ";\n" +
                                            "step pixel: " + pxStep + ";\n" +
                                            "rulers cleared out"
                            );
                            break;
                        }

                        Ruler ruler = new MinorRuler();

//                        ruler.setPxPosition(rulerPxPosition);
                        ruler.setPxPosition(rulerPxPosition);
                        ruler.setPosition(rulerPosition);
                        getGridRulers().addRuler(ruler);
//                        rulerPxPosition += firstPxStep;
                        rulerPosition += firstStep;
                        rulerPxPosition = scale(rulerPosition);

                        i++;
                    }
                } else {
                    Log.w(TAG, "the step to assign rulers position is to big");
                }
            } else {
                Log.w(TAG, "step to assign rulers not defined or wrong");
            }

            if (getUserRulers().size() > 0) {
                for (Ruler ruler : getUserRulers()) {
                    ruler.setPxPosition(scale(ruler.getPosition()));
                }
            }

            if (this.hasGridRulers()) {
                super.apply();
            }
        }
//        Log.d(TAG, "this axis has rulers: " + hasGridRulers());
//        Log.d(TAG, "# rulers: " + getGridRulers().size());
    }
}
