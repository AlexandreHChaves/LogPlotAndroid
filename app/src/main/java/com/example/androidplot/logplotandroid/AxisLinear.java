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

        Float answer = super.scale(value);

        if (answer == -1) {
            return answer;
        }

//        if (Double.isInfinite(getMaxAxisRange())) {
//            Log.e(TAG, "axis range not defined: infinity");
//            return 0f;
//        }
//
//        if (Double.isInfinite(getMaxAxisRange())) {
//            Log.e(TAG, "axis range not defined: infinity");
//            return 0f;
//        }
//
//        if (getMinAxisRange() > getMaxAxisRange()) {
//            Log.e(TAG, "minimum > maximum; limits for the axis range ill defined; review axis limits");
//            return 0f;
//        }
//
//        if (getPxLength() == 0f) {
//            Log.e(TAG, "invalid value for axis length: 0px");
//            return 0f;
//        }
//
//        Log.d(TAG, "value: " + value);
//        Log.d(TAG, "axisRange: " + getAxisRange());
//        Log.d(TAG, "pxLength: " + getPxLength());
//
//        Log.d(TAG, "value in pixels:" + ((value - getMinAxisRange())* getPxLength() / getAxisRange()));
//        Log.d(TAG, "minimum axis range: " + getMinAxisRange());

        // px = m*value + b; b = 0
        return (float) ((value - getMinAxisRange()) * getPxLength() / getAxisRange());
    }

    @Override
    public void apply() {
        Log.d(TAG, "............. apply() ...........");

        if (getMinAxisRange() >= getMaxAxisRange()) {
            Log.e(TAG, "range axis limits badly defined; review minimum and maximum axis range");
            return;
        }

        final int MAX_ITERATIONS = 1000;

        if (getIsLinear()){ // this only works if the axis scale is linear
            if (rulerStep.getValue() > 0d) {
                float pxStep = scale(getMinAxisRange() + rulerStep.getValue());
//                Log.i(TAG, "pxStep: " + pxStep);
                if (pxStep < getPxLength()) {

                    double firstStep = getMinAxisRange() + (rulerStep.getValue() - getMinAxisRange()% rulerStep.getValue());
                    Log.d(TAG, "first rulerStep ruler value position: " + firstStep);

                    double rulerPosition = firstStep;
                    float rulerPxPosition = scale(firstStep);

                    int i = 0; // fuse
                    while (true) {

                        if (rulerPxPosition > getPxLength() || rulerPxPosition < 0) {
                            Log.i(TAG, "fuse iteration: " + i);
                            break;
                        }

                        if (i == MAX_ITERATIONS) {
                            gridRulers.getRulers().clear();
                            Log.e(TAG,
                                    "cycle to assign rulers blew up; \n" +
                                            "maybe rulerStep to short for the axis;\n" +
                                            "rulerStep value: " + rulerStep.getValue() + ";\n" +
                                            "rulerStep pixel: " + pxStep + ";\n" +
                                            "rulers cleared out"
                            );
                            break;
                        }

                        Ruler ruler = new MinorRuler();

//                        ruler.setPxPosition(rulerPxPosition);
                        ruler.setPxPosition(rulerPxPosition);
//                        ruler.label.setText(String.valueOf(rulerPosition));
                        ruler.label.setText(rulerStep.getStringValue(rulerPosition));
//                        Log.i(TAG, "ruler position: " + rulerPosition);
                        gridRulers.addRuler(ruler);
//                        rulerPxPosition += firstPxStep;
//                        rulerPosition += firstStep;
                        rulerPosition += rulerStep.getValue();
                        rulerPxPosition = scale(rulerPosition);

                        i++;
                    }
                } else {
                    Log.w(TAG, "the rulerStep to assign rulers position is to big");
                }
            } else {
                Log.w(TAG, "rulerStep to assign rulers not defined or wrong");
            }

            if (userRulers.getRulers().size() > 0) {
                for (Ruler ruler : userRulers.getRulers()) {
                    ruler.setPxPosition(scale(ruler.getPosition()));
                }
            }

            if (this.hasGridRulers()) {
                super.apply();
            }
        } else {
            Log.w(TAG, "axis NOT defined as LINEAR");
        }
//        Log.d(TAG, "this axis has rulers: " + hasGridRulers());
//        Log.d(TAG, "# rulers: " + getGridRulers().size());
    }
}
