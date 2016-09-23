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
public class AxisLog10 extends Axis{

    private final String TAG = "AxisLog10";

    private double firstMajorStepUnit;

    public AxisLog10() {
        Log.d(TAG, "creating AxisLog10");
        setLinear(false);
    }

    public double getFirstMajorStepUnit() {
        return firstMajorStepUnit;
    }

    /** @param firstMajorStepUnit the value you want for the first "bigger ruler" to be represented.
     * For instance, if you want to represent the response from an audio monitor, the value for the
     * first major ruler might be 20 (as 20Hz). Posterior minor rulers will be 40, 60, ... and
     * anterior and first minor rulers will be 4, 6, ...*/
    public void setFirstMajorStepUnit(double firstMajorStepUnit) {
        this.firstMajorStepUnit = firstMajorStepUnit;
        setMinAxisRange(firstMajorStepUnit/10);
    }

    @Override
    public float scale(double value) {
        float answer = super.scale(value);
        if (answer == -1) {
            return answer;
        }
        // px = m*log10(value) + b
        return (float) (getPxLength()/(Math.log10(getMaxAxisRange()) - Math.log10(getMinAxisRange())) *
                Math.log10(value) -
                getPxLength()/(Math.log10(getMaxAxisRange()) - Math.log10(getMinAxisRange())) * Math.log10(getMinAxisRange()));
    }

    @Override
    public void apply() {

//        Log.d(TAG, "....... apply() .......");

        if (getMinAxisRange() >= getMaxAxisRange()) {
            Log.e(TAG, "range axis limits badly defined; review minimum and maximum axis range");
            return;
        }

        if (!isLinear()) {

            final int MAX_INTERACTIONS = 1000;

//            float majorStepPx = scale(firstMajorStepUnit);
//            float rulerPxPosition = majorStepPx;
            double bigStep = firstMajorStepUnit;
            double littleStep = bigStep/10;
            float rulerPxPosition = 0;

            if (getAxisRange() <= littleStep) {
                Log.e(TAG, "the step chosen for the AxisLog10 is to large");
                return;
            }

            int fuse = 0;
            int k = 0;
            int j = 1;

            while (rulerPxPosition <= getPxLength()) {

                if (fuse++ == MAX_INTERACTIONS) { // cycle fuse
                    gridRulers.getRulers().clear();
                    Log.e(TAG, "cycle to assign rulers blew up; \n" +
                                    "maybe step to short for the axis;\n" +
                                    "rulers cleared out"
                    );
                    break;
                }

                Ruler ruler;

                if (j == 10) { // each tenth ruler the scale is changed
                    j = 1;
                    ruler = new MajorRuler();
                    ruler.setPxPosition(rulerPxPosition);

//                    ruler.label.setLabel(String.valueOf(littleStep * 10));
//                    Label label = new Label(String.valueOf(littleStep * 10));
//                    label.setColor(ChartColor.StandardColor.axisLabel);
//                    ruler.label.setColor(ChartColor.StandardColor.axisLabel);
//                    ruler.label.setText(String.valueOf(littleStep * 10));
//                    ruler.setLabel(label);

                    k++;
                    bigStep = firstMajorStepUnit * Math.pow(10, k);
                    littleStep = bigStep / 10;

                    if (littleStep > getMaxAxisRange()) {
                        Log.w(TAG, "ruler position stopped by outer of range value");
                        break;
                    }

                } else {
                    ruler = new MinorRuler();
                    ruler.setPxPosition(rulerPxPosition);
                }

                ruler.label.setText(String.valueOf(littleStep * j));
                gridRulers.addRuler(ruler);

                rulerPxPosition = scale(littleStep * ++j);
            }

            if (hasUserRulers()) { // user defined rulers
                for (Ruler ruler : userRulers.getRulers()) {
                    ruler.setPxPosition(scale(ruler.getPosition()));
                }
            }

            if (hasGridRulers()) {
                super.apply();
            }

//            if (hasGridRulers()) {
//
//                for (Ruler ruler : getGridRulers()) {
//                    ruler.setPxPosition(scale(ruler.getPosition()));
//                }
//
//                for (Ruler ruler : newRulers) {
//                    Log.d(TAG, "ruler text: " + ruler.label.getLabel());
//                    addGridRuler(ruler);
//                }
//            } else {
//                setGridRulers(newRulers);
//            }

        }
    }
}
